package com.synectiks.asset.business.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.repository.CloudEnvironmentRepository;
import com.synectiks.asset.util.DateFormatUtil;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class CloudEnvironmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudEnvironmentService.class);
		
	@Autowired
	private CloudEnvironmentRepository cloudEnvironmentRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	public Optional<CloudEnvironment> getCloudEnvironment(Long id) {
		logger.info("Get cloud environment by id: {}", id);
		return cloudEnvironmentRepository.findById(id);
	}
	
	public List<CloudEnvironment> getAllCloudEnvironment() {
		logger.info("Get all cloud environment");
		return cloudEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<CloudEnvironment> deleteCloudEnvironment(Long id) {
		logger.info("Delete cloud environment by id: {}", id);
		Optional<CloudEnvironment> oObj = getCloudEnvironment(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. cloud environment cannot be deleted", id);
			return oObj;
		}
		cloudEnvironmentRepository.deleteById(id);
		return oObj;
	}
	
	public CloudEnvironment createAwsCloudEnvironment(CloudEnvironment obj){
		logger.info("Create new aws cloud environment");
		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid department id", "CloudEnvironment", "idnotfound");
		}
		obj.setCloud(Constants.AWS);
		obj.setAccountId(obj.getRoleArn().split(":")[4]);
		obj.setStatus(Constants.ACTIVE);
		return cloudEnvironmentRepository.save(obj);
	}
	
//	public CloudEnvironment updateCloudEnvironment(CloudEnvironment obj){
//		logger.info("Update cloud environment. Id: {}", obj.getId());
//		if(!cloudEnvironmentRepository.existsById(obj.getId())) {
//			throw new BadRequestAlertException("Entity not found", "CloudEnvironment", "idnotfound");
//		}
//		
//		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
//			throw new BadRequestAlertException("Invalid department id", "CloudEnvironment", "idnotfound");
//		}
//		Optional<Department> pOdp = departmentService.getDepartment(obj.getDepartment().getId());
//		if(!pOdp.isPresent()) {
//			throw new BadRequestAlertException("Parent department not found", "CloudEnvironment", "parentidnotfound");
//		}
//		
//		if(!StringUtils.isBlank(obj.getStatus())) {
//			obj.setStatus(obj.getStatus().toUpperCase());
//		}
////		obj.setUpdatedOn(Instant.now());
//		return cloudEnvironmentRepository.save(obj);
//	}
	
	public Optional<CloudEnvironment> partialUpdateAwsCloudEnvironment(CloudEnvironment obj){
		logger.info("Update cloud environment partialy. Id: {}", obj.getId());
		if(!cloudEnvironmentRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "CloudEnvironment", "idnotfound");
		}
		
		Optional<CloudEnvironment> result = cloudEnvironmentRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getAccountId())) {
					existingObj.setAccountId(obj.getAccountId());
				}

				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				if(!StringUtils.isBlank(obj.getDisplayName())) {
					existingObj.setDisplayName(obj.getDisplayName());
				}
				if(!StringUtils.isBlank(obj.getRoleArn())) {
					existingObj.setRoleArn(obj.getRoleArn());
				}
				if(!StringUtils.isBlank(obj.getExternalId())) {
					existingObj.setExternalId(obj.getExternalId());
				}
				
				if(obj.getDepartment() != null && obj.getDepartment().getId() != null) {
					Optional<Department> od = departmentService.getDepartment(obj.getDepartment().getId());
					if(od.isPresent()) {
						existingObj.setDepartment(od.get());
					}else {
						throw new BadRequestAlertException("Parent department entity not found", "CloudEnvironment", "parentidnotfound");
					}
				}
				
				return existingObj;
			})
			.map(cloudEnvironmentRepository::save);
		return result;
	}
	
	public List<CloudEnvironment> searchAllCloudEnvironment(Map<String, String> obj) {
		logger.info("Search cloud environment");
		CloudEnvironment cld = new CloudEnvironment();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("createdOn"))) {
			cld.setCreatedOn(DateFormatUtil.convertStringToInstant(obj.get("createdOn"), Constants.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS)  );
			isFilter = true;
		}else {
			cld.setCreatedOn(null);
		}
		if(!StringUtils.isBlank(obj.get("updatedOn"))) {
			cld.setUpdatedOn(DateFormatUtil.convertStringToInstant(obj.get("updatedOn"), Constants.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS));
			isFilter = true;
		}else {
			cld.setUpdatedOn(null);
		}
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			cld.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			cld.setDescription(obj.get("description"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("accountId"))) {
			cld.setAccountId(obj.get("accountId"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("departmentId"))) {
			Optional<Department> opd = departmentService.getDepartment(Long.parseLong(obj.get("departmentId")));
			if(opd.isPresent()) {
				cld.setDepartment(opd.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}

		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("cloud"))) {
			cld.setCloud(obj.get("cloud").toUpperCase());
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("displayName"))) {
			cld.setDisplayName(obj.get("displayName"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("roleArn"))) {
			cld.setRoleArn(obj.get("roleArn"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("externalId"))) {
			cld.setExternalId(obj.get("externalId"));
			isFilter = true;
		}
		List<CloudEnvironment> list = null;
		if(isFilter) {
			list = cloudEnvironmentRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = cloudEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
