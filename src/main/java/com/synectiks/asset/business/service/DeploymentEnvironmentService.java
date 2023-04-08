package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class DeploymentEnvironmentService {
	
	private final Logger log = LoggerFactory.getLogger(DeploymentEnvironmentService.class);

	@Autowired
    private DeploymentEnvironmentRepository deploymentEnvironmentRepository;

	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;

    public DeploymentEnvironment save(DeploymentEnvironment deploymentEnvironment) {
        log.debug("Request to save DeploymentEnvironment : {}", deploymentEnvironment);
        return deploymentEnvironmentRepository.save(deploymentEnvironment);
    }

    public Optional<DeploymentEnvironment> partialUpdate(DeploymentEnvironment deploymentEnvironment) {
        log.debug("Request to partially update DeploymentEnvironment : {}", deploymentEnvironment);

        return deploymentEnvironmentRepository
            .findById(deploymentEnvironment.getId())
            .map(existingDeploymentEnvironment -> {
                if (deploymentEnvironment.getName() != null) {
                    existingDeploymentEnvironment.setName(deploymentEnvironment.getName());
                }
                if (deploymentEnvironment.getDescription() != null) {
                    existingDeploymentEnvironment.setDescription(deploymentEnvironment.getDescription());
                }
                if (deploymentEnvironment.getStatus() != null) {
                    existingDeploymentEnvironment.setStatus(deploymentEnvironment.getStatus());
                }

                return existingDeploymentEnvironment;
            })
            .map(deploymentEnvironmentRepository::save);
    }

    @Transactional(readOnly = true)
    public List<DeploymentEnvironment> findAll() {
        log.debug("Request to get all DeploymentEnvironments");
        return deploymentEnvironmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DeploymentEnvironment> findOne(Long id) {
        log.debug("Request to get DeploymentEnvironment : {}", id);
        return deploymentEnvironmentRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete DeploymentEnvironment : {}", id);
        deploymentEnvironmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DeploymentEnvironment> search(Map<String, String> filter) throws IOException {
    	log.debug("Request to get all DeploymentEnvironment on given filters");

    	DeploymentEnvironment deploymentEnvironment = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, DeploymentEnvironment.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            deploymentEnvironment.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            deploymentEnvironment.setUpdatedOn(null);
        }
        return deploymentEnvironmentRepository.findAll(Example.of(deploymentEnvironment), Sort.by(Sort.Direction.DESC, "name"));
    }

    
//	private static final Logger logger = LoggerFactory.getLogger(DeploymentEnvironmentService.class);
//		
//	@Autowired
//	DeploymentEnvironmentRepository deploymentEnvironmentRepository;
//	
//	public Optional<DeploymentEnvironment> getDeploymentEnvironment(Long id) {
//		logger.info("Get deployment environment by id: {}", id);
//		return deploymentEnvironmentRepository.findById(id);
//	}
//	
//	public List<DeploymentEnvironment> getAllDeploymentEnvironment() {
//		logger.info("Get all deployment environment");
//		return deploymentEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
//	}
//	
//	public Optional<DeploymentEnvironment> deleteDeploymentEnvironment(Long id) {
//		logger.info("Delete deployment environment by id: {}", id);
//		Optional<DeploymentEnvironment> oObj = getDeploymentEnvironment(id);
//		if(!oObj.isPresent()) {
//			logger.warn("Id {} not found. deployment environment cannot be deleted", id);
//			return oObj;
//		}
//		deploymentEnvironmentRepository.deleteById(id);
//		return oObj;
//	}
//	
//	public DeploymentEnvironment createDeploymentEnvironment(DeploymentEnvironment obj){
//		logger.info("Create new deployment environment");
//		if(!StringUtils.isBlank(obj.getStatus())) {
//			obj.setStatus(obj.getStatus().toUpperCase());
//		}
//		Instant instant = Instant.now();
//		obj.setCreatedOn(instant);
//		obj.setUpdatedOn(instant);
//		return deploymentEnvironmentRepository.save(obj);
//	}
//	
//	public DeploymentEnvironment updateDeploymentEnvironment(DeploymentEnvironment obj){
//		logger.info("Update deployment environment. Id: {}", obj.getId());
//		if(!deploymentEnvironmentRepository.existsById(obj.getId())) {
//			throw new BadRequestAlertException("Entity not found", "DeploymentEnvironment", "idnotfound");
//		}
//		if(!StringUtils.isBlank(obj.getStatus())) {
//			obj.setStatus(obj.getStatus().toUpperCase());
//		}
//		obj.setUpdatedOn(Instant.now());
//		return deploymentEnvironmentRepository.save(obj);
//	}
//	
//	public Optional<DeploymentEnvironment> partialUpdateDeploymentEnvironment(DeploymentEnvironment obj){
//		logger.info("Update deployment environment partialy. Id: {}", obj.getId());
//		if(!deploymentEnvironmentRepository.existsById(obj.getId())) {
//			throw new BadRequestAlertException("Entity not found", "DeploymentEnvironment", "idnotfound");
//		}
//		
//		Optional<DeploymentEnvironment> result = deploymentEnvironmentRepository.findById(obj.getId())
//			.map(existingObj ->{
//				if(!StringUtils.isBlank(obj.getName())) {
//					existingObj.setName(obj.getName());
//				}
//				if(!StringUtils.isBlank(obj.getDescription())) {
//					existingObj.setDescription(obj.getDescription());
//				}
//				if(!StringUtils.isBlank(obj.getStatus())) {
//					existingObj.setStatus(obj.getStatus().toUpperCase());
//				}
//				existingObj.updatedOn(Instant.now());
//				return existingObj;
//			})
//			.map(deploymentEnvironmentRepository::save);
//		return result;
//	}
//	
//	public List<DeploymentEnvironment> searchAllDeploymentEnvironment(Map<String, String> obj) {
//		logger.info("Search deployment environment");
//		DeploymentEnvironment cld = new DeploymentEnvironment();
//		boolean isFilter = false;
//		
//		if(!StringUtils.isBlank(obj.get("id"))) {
//			cld.setId(Long.parseLong(obj.get("id")));
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("name"))) {
//			cld.setName(obj.get("name"));
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("description"))) {
//			cld.setDescription(obj.get("description"));
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("status"))) {
//			cld.setStatus(obj.get("status").toUpperCase());
//			isFilter = true;
//		}
//		
//		List<DeploymentEnvironment> list = null;
//		if(isFilter) {
//			list = deploymentEnvironmentRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
//		}else {
//			list = deploymentEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
//		}
//		return list;
//	}
	
}
