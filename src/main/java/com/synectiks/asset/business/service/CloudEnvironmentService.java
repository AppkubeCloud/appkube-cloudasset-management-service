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

import com.synectiks.asset.business.domain.CloudEnvironment;
import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.CloudEnvironmentRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class CloudEnvironmentService {
	
	private final Logger logger = LoggerFactory.getLogger(CloudEnvironmentService.class);

	@Autowired
	private CloudEnvironmentRepository cloudEnvironmentRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public CloudEnvironment save(CloudEnvironment cloudEnvironment) {
        logger.debug("Request to save CloudEnvironment : {}", cloudEnvironment);
//        cloudEnvironment.setAccountId(cloudEnvironment.getRoleArn().split(":")[4]);
        return cloudEnvironmentRepository.save(cloudEnvironment);
    }

    public Optional<CloudEnvironment> partialUpdate(CloudEnvironment cloudEnvironment) {
        logger.debug("Request to partially update CloudEnvironment : {}", cloudEnvironment);

        return cloudEnvironmentRepository
            .findById(cloudEnvironment.getId())
            .map(existingCloudEnvironment -> {
                if (cloudEnvironment.getName() != null) {
                    existingCloudEnvironment.setName(cloudEnvironment.getName());
                }
                if (cloudEnvironment.getDescription() != null) {
                    existingCloudEnvironment.setDescription(cloudEnvironment.getDescription());
                }
                if (cloudEnvironment.getAccountId() != null) {
                    existingCloudEnvironment.setAccountId(cloudEnvironment.getAccountId());
                }
//                if (cloudEnvironment.getVaultId() != null) {
//                    existingCloudEnvironment.setVaultId(cloudEnvironment.getVaultId());
//                }
//                if (cloudEnvironment.getDisplayName() != null) {
//                    existingCloudEnvironment.setDisplayName(cloudEnvironment.getDisplayName());
//                }
//                if (cloudEnvironment.getRoleArn() != null) {
//                    existingCloudEnvironment.setRoleArn(cloudEnvironment.getRoleArn());
//                }
//                if (cloudEnvironment.getExternalId() != null) {
//                    existingCloudEnvironment.setExternalId(cloudEnvironment.getExternalId());
//                }
                if (cloudEnvironment.getCloud() != null) {
                    existingCloudEnvironment.setCloud(cloudEnvironment.getCloud());
                }
                if (cloudEnvironment.getStatus() != null) {
                    existingCloudEnvironment.setStatus(cloudEnvironment.getStatus());
                }
                if (cloudEnvironment.getCreatedOn() != null) {
                    existingCloudEnvironment.setCreatedOn(cloudEnvironment.getCreatedOn());
                }
                if (cloudEnvironment.getUpdatedOn() != null) {
                    existingCloudEnvironment.setUpdatedOn(cloudEnvironment.getUpdatedOn());
                }
                if (cloudEnvironment.getUpdatedBy() != null) {
                    existingCloudEnvironment.setUpdatedBy(cloudEnvironment.getUpdatedBy());
                }
                if (cloudEnvironment.getCreatedBy() != null) {
                    existingCloudEnvironment.setCreatedBy(cloudEnvironment.getCreatedBy());
                }

				return existingCloudEnvironment;
            })
            .map(cloudEnvironmentRepository::save);
    }

    @Transactional(readOnly = true)
    public List<CloudEnvironment> findAll() {
        logger.debug("Request to get all CloudEnvironments");
        return cloudEnvironmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CloudEnvironment> findOne(Long id) {
        logger.debug("Request to get CloudEnvironment : {}", id);
        return cloudEnvironmentRepository.findById(id);
    }

    public void delete(Long id) {
        logger.debug("Request to delete CloudEnvironment : {}", id);
        cloudEnvironmentRepository.deleteById(id);
    }
	
    @Transactional(readOnly = true)
	public List<CloudEnvironment> search(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all cloudEnvironments on given filters");

		Department department = null;
		if (!StringUtils.isBlank(filter.get(Constants.DEPARTMENT_ID))) {
			department = new Department();
			department.setId(Long.parseLong(filter.get(Constants.DEPARTMENT_ID)));
			department.setCreatedOn(null);
			department.setUpdatedOn(null);
			filter.remove(Constants.DEPARTMENT_ID);
		}
		
		if (!StringUtils.isBlank(filter.get(Constants.DEPARTMENT_NAME))) {
			if(department != null) {
				department.setName(filter.get(Constants.DEPARTMENT_NAME));
			}else {
				department = new Department();
				department.setName(filter.get(Constants.DEPARTMENT_NAME));
			}
			department.setCreatedOn(null);
			department.setUpdatedOn(null);
			filter.remove(Constants.DEPARTMENT_NAME);
		}
		
		CloudEnvironment ce = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, CloudEnvironment.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			ce.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			ce.setUpdatedOn(null);
		}
		if (department != null) {
			ce.setDepartment(department);
		}
		List<CloudEnvironment> list = cloudEnvironmentRepository.findAll(Example.of(ce), Sort.by(Sort.Direction.DESC, "id"));
		return list;
	}
    
//	public Optional<CloudEnvironment> getCloudEnvironment(Long id) {
//		logger.info("Get cloud environment by id: {}", id);
//		return cloudEnvironmentRepository.findById(id);
//	}
//	
//	public List<CloudEnvironment> getAllCloudEnvironment() {
//		logger.info("Get all cloud environment");
//		return cloudEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
//	}
//	
//	public Optional<CloudEnvironment> deleteCloudEnvironment(Long id) {
//		logger.info("Delete cloud environment by id: {}", id);
//		Optional<CloudEnvironment> oObj = getCloudEnvironment(id);
//		if(!oObj.isPresent()) {
//			logger.warn("Id {} not found. cloud environment cannot be deleted", id);
//			return oObj;
//		}
//		cloudEnvironmentRepository.deleteById(id);
//		return oObj;
//	}
//	
//	public CloudEnvironment createAwsCloudEnvironment(CloudEnvironment obj){
//		logger.info("Create new aws cloud environment");
//		if(Objects.isNull(obj.getDepartment()) || (!Objects.isNull(obj.getDepartment()) && obj.getDepartment().getId() < 0)) {
//			throw new BadRequestAlertException("Invalid department id", "CloudEnvironment", "idnotfound");
//		}
//		obj.setCloud(Constants.AWS);
//		obj.setAccountId(obj.getRoleArn().split(":")[4]);
//		obj.setStatus(Constants.ACTIVE);
//		return cloudEnvironmentRepository.save(obj);
//	}
//	
//	public Optional<CloudEnvironment> partialUpdateAwsCloudEnvironment(CloudEnvironment obj){
//		logger.info("Update cloud environment partialy. Id: {}", obj.getId());
//		if(!cloudEnvironmentRepository.existsById(obj.getId())) {
//			throw new BadRequestAlertException("Entity not found", "CloudEnvironment", "idnotfound");
//		}
//		
//		Optional<CloudEnvironment> result = cloudEnvironmentRepository.findById(obj.getId())
//			.map(existingObj ->{
//				if(!StringUtils.isBlank(obj.getName())) {
//					existingObj.setName(obj.getName());
//				}
//				if(!StringUtils.isBlank(obj.getDescription())) {
//					existingObj.setDescription(obj.getDescription());
//				}
//				if(!StringUtils.isBlank(obj.getAccountId())) {
//					existingObj.setAccountId(obj.getAccountId());
//				}
//
//				if(!StringUtils.isBlank(obj.getStatus())) {
//					existingObj.setStatus(obj.getStatus().toUpperCase());
//				}
//				if(!StringUtils.isBlank(obj.getDisplayName())) {
//					existingObj.setDisplayName(obj.getDisplayName());
//				}
//				if(!StringUtils.isBlank(obj.getRoleArn())) {
//					existingObj.setRoleArn(obj.getRoleArn());
//				}
//				if(!StringUtils.isBlank(obj.getExternalId())) {
//					existingObj.setExternalId(obj.getExternalId());
//				}
//				
////				if(obj.getDepartment() != null && obj.getDepartment().getId() != null) {
////					Optional<Department> od = departmentService.getDepartment(obj.getDepartment().getId());
////					if(od.isPresent()) {
////						existingObj.setDepartment(od.get());
////					}else {
////						throw new BadRequestAlertException("Parent department entity not found", "CloudEnvironment", "parentidnotfound");
////					}
////				}
//				
//				return existingObj;
//			})
//			.map(cloudEnvironmentRepository::save);
//		return result;
//	}
//	
//	public List<CloudEnvironment> searchAllCloudEnvironment(Map<String, String> obj) {
//		logger.info("Search cloud environment");
//		CloudEnvironment cld = new CloudEnvironment();
//		boolean isFilter = false;
//		
////		if(!StringUtils.isBlank(obj.get("createdOn"))) {
////			cld.setCreatedOn(DateFormatUtil.convertStringToInstant(obj.get("createdOn"), Constants.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS)  );
////			isFilter = true;
////		}else {
////			cld.setCreatedOn(null);
////		}
////		if(!StringUtils.isBlank(obj.get("updatedOn"))) {
////			cld.setUpdatedOn(DateFormatUtil.convertStringToInstant(obj.get("updatedOn"), Constants.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS));
////			isFilter = true;
////		}else {
////			cld.setUpdatedOn(null);
////		}
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
//		if(!StringUtils.isBlank(obj.get("accountId"))) {
//			cld.setAccountId(obj.get("accountId"));
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("departmentId"))) {
//			isFilter = true;
//			Optional<Department> opd = departmentService.findOne(Long.parseLong(obj.get("departmentId")));
//			if(opd.isPresent()) {
//				cld.setDepartment(opd.get());
//			}
//		}
//
//		if(!StringUtils.isBlank(obj.get("status"))) {
//			cld.setStatus(obj.get("status").toUpperCase());
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("cloud"))) {
//			cld.setCloud(obj.get("cloud").toUpperCase());
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("displayName"))) {
//			cld.setDisplayName(obj.get("displayName"));
//			isFilter = true;
//		}
//		
//		if(!StringUtils.isBlank(obj.get("roleArn"))) {
//			cld.setRoleArn(obj.get("roleArn"));
//			isFilter = true;
//		}
//		if(!StringUtils.isBlank(obj.get("externalId"))) {
//			cld.setExternalId(obj.get("externalId"));
//			isFilter = true;
//		}
//		List<CloudEnvironment> list = null;
//		if(isFilter) {
//			list = cloudEnvironmentRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
//		}else {
//			list = cloudEnvironmentRepository.findAll(Sort.by(Direction.DESC, "id"));
//		}
//		return list;
//	}
//	

	
}
