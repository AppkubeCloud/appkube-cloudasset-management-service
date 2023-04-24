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

import com.synectiks.asset.business.domain.ServiceAllocation;
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Module;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ServiceAllocationRepository;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
import com.synectiks.asset.repository.ModuleRepository;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.repository.ServicesRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class ServiceAllocationService {
	
	private final Logger logger = LoggerFactory.getLogger(ServiceAllocationService.class);
	
	@Autowired
	private ServiceAllocationRepository serviceAllocationRepository;
	
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
	
    public ServiceAllocation save(ServiceAllocation serviceAllocation) {
        logger.debug("Request to save serviceAllocation : {}", serviceAllocation);
        return serviceAllocationRepository.save(serviceAllocation);
    }

    public Optional<ServiceAllocation> partialUpdate(ServiceAllocation serviceAllocation) {
        logger.debug("Request to partially update serviceAllocation : {}", serviceAllocation);

        return serviceAllocationRepository
            .findById(serviceAllocation.getId())
            .map(existingServiceAllocation -> {
            	if (serviceAllocation.getLandingZone() != null) {
                    existingServiceAllocation.setLandingZone(serviceAllocation.getLandingZone());
                }
                if (serviceAllocation.getDepartmentId() != null) {
                    existingServiceAllocation.setDepartmentId(serviceAllocation.getDepartmentId());
                }
                if (serviceAllocation.getProductId() != null) {
                    existingServiceAllocation.setProductId(serviceAllocation.getProductId());
                }
                if (serviceAllocation.getDeploymentEnvironmentId() != null) {
                    existingServiceAllocation.setDeploymentEnvironmentId(serviceAllocation.getDeploymentEnvironmentId());
                }
                if (serviceAllocation.getModuleId() != null) {
                    existingServiceAllocation.setModuleId(serviceAllocation.getModuleId());
                }
                if (serviceAllocation.getServicesId() != null) {
                    existingServiceAllocation.setServicesId(serviceAllocation.getServicesId());
                }
                if (serviceAllocation.getServiceType() != null) {
                    existingServiceAllocation.setServiceType(serviceAllocation.getServiceType());
                }
                if (serviceAllocation.getServiceNature() != null) {
                    existingServiceAllocation.setServiceNature(serviceAllocation.getServiceNature());
                }
                if (serviceAllocation.getCreatedOn() != null) {
                    existingServiceAllocation.setCreatedOn(serviceAllocation.getCreatedOn());
                }
                if (serviceAllocation.getUpdatedOn() != null) {
                    existingServiceAllocation.setUpdatedOn(serviceAllocation.getUpdatedOn());
                }
                if (serviceAllocation.getUpdatedBy() != null) {
                    existingServiceAllocation.setUpdatedBy(serviceAllocation.getUpdatedBy());
                }
                if (serviceAllocation.getCreatedBy() != null) {
                    existingServiceAllocation.setCreatedBy(serviceAllocation.getCreatedBy());
                }
                return existingServiceAllocation;
            })
            .map(serviceAllocationRepository::save);
    }

    @Transactional(readOnly = true)
    public List<ServiceAllocation> findAll() {
        logger.debug("Request to get all serviceAllocation");
        return serviceAllocationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ServiceAllocation> findOne(Long id) {
        logger.debug("Request to get serviceAllocation : {}", id);
        return serviceAllocationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ServiceAllocation> findOne(ServiceAllocation serviceAllocation) {
        logger.debug("Request to find serviceAllocation");
        return serviceAllocationRepository.findOne(Example.of(serviceAllocation));
    }
    
    public void delete(Long id) {
        logger.debug("Request to delete serviceAllocation : {}", id);
        serviceAllocationRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public List<ServiceAllocation> search(Map<String, String> filter) throws IOException {
		logger.debug("Request to get all serviceAllocation on given filters");
		
		ServiceAllocation serviceAllocation = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, ServiceAllocation.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			serviceAllocation.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			serviceAllocation.setUpdatedOn(null);
		}
		
		return serviceAllocationRepository.findAll(Example.of(serviceAllocation), Sort.by(Sort.Direction.DESC, "id"));
	}
    
	public List<Product> getProducts(Map<String, String> filter) throws IOException {
		logger.debug("Getting products of given department");
		List<ServiceAllocation> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(ServiceAllocation dpe: list) {
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
		List<ServiceAllocation> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(ServiceAllocation dpe: list) {
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
		List<ServiceAllocation> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(ServiceAllocation dpe: list) {
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
		List<ServiceAllocation> list = search(filter);
		List<Long> idList = new ArrayList<>();
		for(ServiceAllocation dpe: list) {
			idList.add(dpe.getServicesId());
		}
		return findAllServicesById(idList);
	}
	
	public List<Services> findAllServicesById(List<Long> idList){
		return servicesRepository.findAllById(idList);
	}
}
