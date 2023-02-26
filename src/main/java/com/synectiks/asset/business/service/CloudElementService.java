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
import com.synectiks.asset.domain.CloudElement;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.repository.CloudElementRepository;
import com.synectiks.asset.util.DateFormatUtil;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class CloudElementService {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudElementService.class);
		
	@Autowired
	private CloudElementRepository cloudElementRepository;
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	public Optional<CloudElement> getCloudElement(Long id) {
		logger.info("Get cloud element by id: {}", id);
		return cloudElementRepository.findById(id);
	}
	
	public List<CloudElement> getAllCloudElement() {
		logger.info("Get all cloud element");
		return cloudElementRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<CloudElement> deleteCloudElement(Long id) {
		logger.info("Delete cloud element by id: {}", id);
		Optional<CloudElement> oObj = getCloudElement(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. cloud element cannot be deleted", id);
			return oObj;
		}
		cloudElementRepository.deleteById(id);
		return oObj;
	}
	
	public CloudElement createCloudElement(CloudElement obj){
		logger.info("Create new cloud element");
//		if(Objects.isNull(obj.getCloudEnvironment()) || (!Objects.isNull(obj.getCloudEnvironment()) && obj.getCloudEnvironment().getId() < 0)) {
//			throw new BadRequestAlertException("Cloud environment id not provided", "CloudElement", "idnotfound");
//		}
		return cloudElementRepository.save(obj);
	}
		
	public Optional<CloudElement> partialUpdateCloudElement(CloudElement obj){
		logger.info("Update cloud element partialy. Id: {}", obj.getId());
		if(!cloudElementRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "CloudElement", "idnotfound");
		}
		
		Optional<CloudElement> result = cloudElementRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(obj.getViewJson() != null) {
					existingObj.setViewJson(obj.getViewJson());
				}
				if(obj.getAccountId() != null) {
					existingObj.setAccountId(obj.getAccountId());
				}				
				if(obj.getCloudEnvironment() != null && obj.getCloudEnvironment().getId() != null) {
					Optional<CloudEnvironment> od = cloudEnvironmentService.getCloudEnvironment(obj.getCloudEnvironment().getId());
					if(od.isPresent()) {
						existingObj.setCloudEnvironment(od.get());
					}else {
						throw new BadRequestAlertException("CloudEnvironment entity not found", "CloudElement", "parentidnotfound");
					}
				}
				
				return existingObj;
			})
			.map(cloudElementRepository::save);
		return result;
	}
	
	public List<CloudElement> searchAllCloudElement(Map<String, String> obj) {
		logger.info("Search cloud element");
		CloudElement cld = new CloudElement();
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
		if(!StringUtils.isBlank(obj.get("accountId"))) {
			cld.setAccountId(obj.get("accountId"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("cloudEnvironmentId"))) {
			Optional<CloudEnvironment> opd = cloudEnvironmentService.getCloudEnvironment(Long.parseLong(obj.get("cloudEnvironmentId")));
			if(opd.isPresent()) {
				cld.setCloudEnvironment(opd.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}

		
		List<CloudElement> list = null;
		if(isFilter) {
			list = cloudElementRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = cloudElementRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
