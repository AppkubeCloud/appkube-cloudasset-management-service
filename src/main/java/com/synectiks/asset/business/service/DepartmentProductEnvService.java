package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.DepartmentProductEnv;
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Module;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DepartmentProductEnvRepository;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
import com.synectiks.asset.repository.ModuleRepository;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.repository.ServicesRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class DepartmentProductEnvService {
	
	private final Logger logger = LoggerFactory.getLogger(DepartmentProductEnvService.class);
	
	@Autowired
	private DepartmentProductEnvRepository departmentProductEnvRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private DeploymentEnvironmentRepository deploymentEnvironmentRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public DepartmentProductEnv save(DepartmentProductEnv departmentProductEnv) {
        logger.debug("Request to save departmentProductEnv : {}", departmentProductEnv);
        return departmentProductEnvRepository.save(departmentProductEnv);
    }

    public Optional<DepartmentProductEnv> partialUpdate(DepartmentProductEnv departmentProductEnv) {
        logger.debug("Request to partially update departmentProductEnv : {}", departmentProductEnv);

        return departmentProductEnvRepository
            .findById(departmentProductEnv.getId())
            .map(existingDepartmentProduct -> {
            	if (departmentProductEnv.getLandingZone() != null) {
                    existingDepartmentProduct.setLandingZone(departmentProductEnv.getLandingZone());
                }
                if (departmentProductEnv.getDepartmentId() != null) {
                    existingDepartmentProduct.setDepartmentId(departmentProductEnv.getDepartmentId());
                }
                if (departmentProductEnv.getProductId() != null) {
                    existingDepartmentProduct.setProductId(departmentProductEnv.getProductId());
                }
                if (departmentProductEnv.getDeploymentEnvironmentId() != null) {
                    existingDepartmentProduct.setDeploymentEnvironmentId(departmentProductEnv.getDeploymentEnvironmentId());
                }
                if (departmentProductEnv.getModuleId() != null) {
                    existingDepartmentProduct.setModuleId(departmentProductEnv.getModuleId());
                }
                if (departmentProductEnv.getServicesId() != null) {
                    existingDepartmentProduct.setServicesId(departmentProductEnv.getServicesId());
                }
                if (departmentProductEnv.getServiceType() != null) {
                    existingDepartmentProduct.setServiceType(departmentProductEnv.getServiceType());
                }
                if (departmentProductEnv.getServiceNature() != null) {
                    existingDepartmentProduct.setServiceNature(departmentProductEnv.getServiceNature());
                }
                if (departmentProductEnv.getCreatedOn() != null) {
                    existingDepartmentProduct.setCreatedOn(departmentProductEnv.getCreatedOn());
                }
                if (departmentProductEnv.getUpdatedOn() != null) {
                    existingDepartmentProduct.setUpdatedOn(departmentProductEnv.getUpdatedOn());
                }
                if (departmentProductEnv.getUpdatedBy() != null) {
                    existingDepartmentProduct.setUpdatedBy(departmentProductEnv.getUpdatedBy());
                }
                if (departmentProductEnv.getCreatedBy() != null) {
                    existingDepartmentProduct.setCreatedBy(departmentProductEnv.getCreatedBy());
                }
                return existingDepartmentProduct;
            })
            .map(departmentProductEnvRepository::save);
    }

    @Transactional(readOnly = true)
    public List<DepartmentProductEnv> findAll() {
        logger.debug("Request to get all departmentProductEnv");
        return departmentProductEnvRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentProductEnv> findOne(Long id) {
        logger.debug("Request to get departmentProductEnv : {}", id);
        return departmentProductEnvRepository.findById(id);
    }

    public void delete(Long id) {
        logger.debug("Request to delete departmentProductEnv : {}", id);
        departmentProductEnvRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public List<DepartmentProductEnv> search(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all departmentProductEnv on given filters");
		
		DepartmentProductEnv departmentProductEnv = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, DepartmentProductEnv.class);

		// unset default value if createdOn is not coming in filter
		if (filter.containsKey(Constants.CREATED_ON) && StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			departmentProductEnv.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (filter.containsKey(Constants.UPDATED_ON) && StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			departmentProductEnv.setUpdatedOn(null);
		}
		
		return departmentProductEnvRepository.findAll(Example.of(departmentProductEnv), Sort.by(Sort.Direction.DESC, "id"));
	}
    
	public List<Product> getProducts(Map<String, String> filter) throws IOException {
		logger.debug("Getting products of given department");
		List<DepartmentProductEnv> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(DepartmentProductEnv dpe: list) {
			idList.add(dpe.getProductId());
		}
		return findAllProductsById(idList);
	}
	
	public List<Product> findAllProductsById(List<Long> idList){
		List<Product> list = productRepository.findAllById(idList);
		Map<String, String> filter = new HashMap<>();
		for(Product product: list) {
			filter.clear();
			filter.put(Constants.PRODUCT_ID, String.valueOf(product.getId()));
			try {
				List<DeploymentEnvironment> deList = getDeploymentEnvironments(filter);
				product.setDeploymentEnvironments(deList);
			} catch (IOException e) {
				logger.error("Error in getting deployment environments: ", e.getMessage());
			}
		}
		return list;
	}
	
	
	public List<DeploymentEnvironment> getDeploymentEnvironments(Map<String, String> filter) throws IOException {
		logger.debug("Getting deployment environments");
		List<DepartmentProductEnv> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(DepartmentProductEnv dpe: list) {
			idList.add(dpe.getDeploymentEnvironmentId());
		}
		return findAllDeploymentEnvironmentsById(idList);
	}
	
	public List<DeploymentEnvironment> findAllDeploymentEnvironmentsById(List<Long> idList){
		List<DeploymentEnvironment> list = deploymentEnvironmentRepository.findAllById(idList);
		Map<String, String> filter = new HashMap<>();
		for(DeploymentEnvironment de: list) {
			filter.clear();
			filter.put(Constants.DEPLOYMENT_ENVIRONMENT_ID, String.valueOf(de.getId()));
			try {
				List<Module> deList = getModules(filter);
				de.setModules(deList);
			} catch (IOException e) {
				logger.error("Error in getting modules: ", e.getMessage());
			}
		}
		return list;
	}
	
	public List<Module> getModules(Map<String, String> filter) throws IOException {
		logger.debug("Getting modules");
		List<DepartmentProductEnv> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(DepartmentProductEnv dpe: list) {
			idList.add(dpe.getModuleId());
		}
		return findAllModulesById(idList);
	}
	
	public List<Module> findAllModulesById(List<Long> idList){
		List<Module> list = moduleRepository.findAllById(idList);
		Map<String, String> filter = new HashMap<>();
		for(Module md: list) {
			filter.clear();
			filter.put(Constants.MODULE_ID, String.valueOf(md.getId()));
			try {
				List<Services> deList = getServices(filter);
				md.setAppServices(deList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.APP_SERVICES)).collect(Collectors.toList()));
				md.setDataServices(deList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.DATA_SERVICES)).collect(Collectors.toList()));
			} catch (IOException e) {
				logger.error("Error in getting app services: ", e.getMessage());
			}
		}
		return list;
	}

	public List<Services> getServices(Map<String, String> filter) throws IOException {
		logger.debug("Getting services");
		List<DepartmentProductEnv> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(DepartmentProductEnv dpe: list) {
			idList.add(dpe.getServicesId());
		}
		return findAllServicesById(idList);
	}
	
	public List<Services> findAllServicesById(List<Long> idList){
		return servicesRepository.findAllById(idList);
	}
}
