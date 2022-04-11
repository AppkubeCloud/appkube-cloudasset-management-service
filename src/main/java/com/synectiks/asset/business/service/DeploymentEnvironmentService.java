package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class DeploymentEnvironmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentEnvironmentService.class);
		
	@Autowired
	DeploymentEnvironmentRepository deploymentEnvironmentRepository;
	
	public Optional<DeploymentEnvironment> getDeploymentEnvironment(Long id) {
		logger.info("Get deployment environment by id: {}", id);
		return deploymentEnvironmentRepository.findById(id);
	}
	
	public List<DeploymentEnvironment> getAllDeploymentEnvironment() {
		logger.info("Get all deployment environment");
		return deploymentEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<DeploymentEnvironment> deleteDeploymentEnvironment(Long id) {
		logger.info("Delete deployment environment by id: {}", id);
		Optional<DeploymentEnvironment> oObj = getDeploymentEnvironment(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. deployment environment cannot be deleted", id);
			return oObj;
		}
		deploymentEnvironmentRepository.deleteById(id);
		return oObj;
	}
	
	public DeploymentEnvironment createDeploymentEnvironment(DeploymentEnvironment obj){
		logger.info("Create new deployment environment");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return deploymentEnvironmentRepository.save(obj);
	}
	
	public DeploymentEnvironment updateDeploymentEnvironment(DeploymentEnvironment obj){
		logger.info("Update deployment environment. Id: {}", obj.getId());
		if(!deploymentEnvironmentRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "DeploymentEnvironment", "idnotfound");
		}
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return deploymentEnvironmentRepository.save(obj);
	}
	
	public Optional<DeploymentEnvironment> partialUpdateDeploymentEnvironment(DeploymentEnvironment obj){
		logger.info("Update deployment environment partialy. Id: {}", obj.getId());
		if(!deploymentEnvironmentRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "DeploymentEnvironment", "idnotfound");
		}
		
		Optional<DeploymentEnvironment> result = deploymentEnvironmentRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(deploymentEnvironmentRepository::save);
		return result;
	}
	
	public List<DeploymentEnvironment> searchAllDeploymentEnvironment(Map<String, String> obj) {
		logger.info("Search deployment environment");
		DeploymentEnvironment cld = new DeploymentEnvironment();
		boolean isFilter = false;
		
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
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		List<DeploymentEnvironment> list = null;
		if(isFilter) {
			list = deploymentEnvironmentRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = deploymentEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	
}
