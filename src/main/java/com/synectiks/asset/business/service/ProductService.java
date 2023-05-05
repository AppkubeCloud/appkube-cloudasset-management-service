package com.synectiks.asset.business.service;

import java.io.IOException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class ProductService {
	
	private final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentService departmentService;
	
	
//	@Autowired
//	private ServiceAllocationService serviceAllocationService;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public Product save(Product product) throws IOException {
        logger.debug("Request to save Product : {}", product);
        Product p = productRepository.save(product);
//        if(Objects.nonNull(p.getOrganizationId())){
//        	Optional<Organization> o = organizationService.findOne(p.getOrganizationId());
//        	if(o.isPresent()) {
//        		p.setOrganizationName(o.get().getName());
//        	}
//        }
//        if(Objects.nonNull(p.getDepartmentId())){
//        	Optional<Department> o = departmentService.findOne(p.getDepartmentId());
//        	if(o.isPresent()) {
//        		p.setDepartmentName(o.get().getName());
//        	}
//        }
        
//        if(p != null) {
//			Map<String, String> filter = new HashMap<>();
//			filter.put("productId", String.valueOf(p.getId()));
//			try {
//				List<DeploymentEnvironment> deList = serviceAllocationService.getDeploymentEnvironments(filter);
//				p.setDeploymentEnvironments(deList);
//			} catch (IOException e) {
//				logger.error("Error in getting deployment environments: ", e.getMessage());
//			}
//		}
        return p;
    }

    public Optional<Product> partialUpdate(Product product) {
        logger.debug("Request to partially update Product : {}", product);

        return productRepository
            .findById(product.getId())
            .map(existingProduct -> {
                if (product.getName() != null) {
                    existingProduct.setName(product.getName());
                }
                if (product.getDescription() != null) {
                    existingProduct.setDescription(product.getDescription());
                }
                
                if (product.getLandingZone() != null) {
                    existingProduct.setLandingZone(product.getLandingZone());
                }
                if (product.getOrganizationId() != null) {
                    existingProduct.setOrganizationId(product.getOrganizationId());
                }
                if (product.getDepartmentId() != null) {
                    existingProduct.setDepartmentId(product.getDepartmentId());
                }
                if (product.getDeploymentEnvironmentId() != null) {
                    existingProduct.setDeploymentEnvironmentId(product.getDeploymentEnvironmentId());
                }
                if (product.getModuleName() != null) {
                    existingProduct.setModuleName(product.getModuleName());
                }
                if (product.getServiceName() != null) {
                    existingProduct.setServiceName(product.getServiceName());
                }
                if (product.getServiceCategory() != null) {
                    existingProduct.setServiceCategory(product.getServiceCategory());
                }
                if (product.getServiceType() != null) {
                    existingProduct.setServiceType(product.getServiceType());
                }
                if (product.getServiceNature() != null) {
                    existingProduct.setServiceNature(product.getServiceNature());
                }
                if (product.getDiscoveredAssetId() != null) {
                    existingProduct.setDiscoveredAssetId(product.getDiscoveredAssetId());
                }
                if (product.getTag() != null) {
                    existingProduct.setTag(product.getTag());
                }
                if (product.getHostingType() != null) {
                    existingProduct.setHostingType(product.getHostingType());
                }
                if (product.getHostingUrl() != null) {
                    existingProduct.setHostingUrl(product.getHostingUrl());
                }
                if (product.getHostingNamespace() != null) {
                    existingProduct.setHostingNamespace(product.getHostingNamespace());
                }
                if (product.getServiceLocation() != null) {
                    existingProduct.setServiceLocation(product.getServiceLocation());
                }
                if (product.getProductEnclave() != null) {
                    existingProduct.setProductEnclave(product.getProductEnclave());
                }
                if (product.getProductEnclaveId() != null) {
                    existingProduct.setProductEnclaveId(product.getProductEnclaveId());
                }
                if (product.getProductEnclaveArn() != null) {
                    existingProduct.setProductEnclaveArn(product.getProductEnclaveArn());
                }
                if (product.getCloudElement() != null) {
                    existingProduct.setCloudElement(product.getCloudElement());
                }
                if (product.getCloudElementId() != null) {
                    existingProduct.setCloudElementId(product.getCloudElementId());
                }
                if (product.getCloudElementKey() != null) {
                    existingProduct.setCloudElementKey(product.getCloudElementKey());
                }
                if (product.getServiceDetailId() != null) {
                    existingProduct.setServiceDetailId(product.getServiceDetailId());
                }
                
                if (product.getStatus() != null) {
                    existingProduct.setStatus(product.getStatus());
                }
                
                if (product.getOrganizationName() != null) {
                    existingProduct.setOrganizationName(product.getOrganizationName());
                }
                if (product.getDepartmentName() != null) {
                    existingProduct.setDepartmentName(product.getDepartmentName());
                }
                if (product.getDeploymentEnvironmentName() != null) {
                    existingProduct.setDeploymentEnvironmentName(product.getDeploymentEnvironmentName());
                }
                
                if (product.getCreatedOn() != null) {
                    existingProduct.setCreatedOn(product.getCreatedOn());
                }
                if (product.getCreatedBy() != null) {
                    existingProduct.setCreatedBy(product.getCreatedBy());
                }
                if (product.getUpdatedOn() != null) {
                    existingProduct.setUpdatedOn(product.getUpdatedOn());
                }
                if (product.getUpdatedBy() != null) {
                    existingProduct.setUpdatedBy(product.getUpdatedBy());
                }

//                Map<String, String> filter = new HashMap<>();
//    			filter.put("productId", String.valueOf(existingProduct.getId()));
//    			try {
//    				List<DeploymentEnvironment> pList = serviceAllocationService.getDeploymentEnvironments(filter);
//    				existingProduct.setDeploymentEnvironments(pList);
//    			} catch (IOException e) {
//    				logger.error("Error in getting deployment environments ", e.getMessage());
//    			}
//    			try {
//    				if(Objects.nonNull(existingProduct.getOrganizationId())){
//                    	Optional<Organization> o = organizationService.findOne(existingProduct.getOrganizationId());
//                    	if(o.isPresent()) {
//                    		existingProduct.setOrganizationName(o.get().getName());
//                    	}
//                    }
//                    if(Objects.nonNull(existingProduct.getDepartmentId())){
//                    	Optional<Department> o = departmentService.findOne(existingProduct.getDepartmentId());
//                    	if(o.isPresent()) {
//                    		existingProduct.setDepartmentName(o.get().getName());
//                    	}
//                    }
//    			}catch(Exception e) {
//    				logger.warn("Exception in getting org and department name: ",e);
//    			}
                
                
                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        logger.debug("Request to get all Products");
        List<Product> list = productRepository.findAll();
//        for(Product p: list) {
//        	try {
//        		if(Objects.nonNull(p.getOrganizationId())){
//                	Optional<Organization> o = organizationService.findOne(p.getOrganizationId());
//                	if(o.isPresent()) {
//                		p.setOrganizationName(o.get().getName());
//                	}
//                }
//                if(Objects.nonNull(p.getDepartmentId())){
//                	Optional<Department> o = departmentService.findOne(p.getDepartmentId());
//                	if(o.isPresent()) {
//                		p.setDepartmentName(o.get().getName());
//                	}
//                }
//        	}catch(Exception e) {
//        		logger.warn("Exception in getting org and dept name in findAll: ",e);
//        	}
//        	
//        }
        
        
//        Map<String, String> filter = new HashMap<>();
//		for(Product product: list) {
//			filter.clear();
//			filter.put("productId", String.valueOf(product.getId()));
//			try {
//				List<DeploymentEnvironment> deList = serviceAllocationService.getDeploymentEnvironments(filter);
//				product.setDeploymentEnvironments(deList);
//			} catch (IOException e) {
//				logger.error("Error in getting deployment environments: ", e.getMessage());
//			}
//		}
		return list;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        logger.debug("Request to get Product : {}", id);
        Optional<Product> op = productRepository.findById(id);
        
//        if(op.isPresent()) {
//        	try {
//        		if(Objects.nonNull(op.get().getOrganizationId())){
//                	Optional<Organization> o = organizationService.findOne(op.get().getOrganizationId());
//                	if(o.isPresent()) {
//                		op.get().setOrganizationName(o.get().getName());
//                	}
//                }
//                if(Objects.nonNull(op.get().getDepartmentId())){
//                	Optional<Department> o = departmentService.findOne(op.get().getDepartmentId());
//                	if(o.isPresent()) {
//                		op.get().setDepartmentName(o.get().getName());
//                	}
//                }
//        	}catch(Exception e) {
//        		logger.warn("Exception in getting org and dept name in findAll: ",e);
//        	}	
//        }
    	
        	
        
        
//        if(op.isPresent()) {
//			Map<String, String> filter = new HashMap<>();
//			filter.put("productId", String.valueOf(op.get().getId()));
//			try {
//				List<DeploymentEnvironment> deList = serviceAllocationService.getDeploymentEnvironments(filter);
//				op.get().setDeploymentEnvironments(deList);
//			} catch (IOException e) {
//				logger.error("Error in getting deployment environments: ", e.getMessage());
//			}
//		}
        return op;
    }

    public void delete(Long id) {
        logger.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
	
    @Transactional(readOnly = true)
    public List<Product> search(Map<String, String> filter) throws IOException {
    	logger.debug("Request to get all products on given filters");

    	Product product = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, Product.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            product.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            product.setUpdatedOn(null);
        }
        List<Product> list = productRepository.findAll(Example.of(product), Sort.by(Sort.Direction.DESC, "name"));
//        for(Product p: list) {
//        	try {
//        		if(Objects.nonNull(p.getOrganizationId())){
//                	Optional<Organization> o = organizationService.findOne(p.getOrganizationId());
//                	if(o.isPresent()) {
//                		p.setOrganizationName(o.get().getName());
//                	}
//                }
//                if(Objects.nonNull(p.getDepartmentId())){
//                	Optional<Department> o = departmentService.findOne(p.getDepartmentId());
//                	if(o.isPresent()) {
//                		p.setDepartmentName(o.get().getName());
//                	}
//                }
//        	}catch(Exception e) {
//        		logger.warn("Exception in getting org and dept name in findAll: ",e);
//        	}
//        	
//        }
//        Map<String, String> defilter = new HashMap<>();
//		for(Product p: list) {
//			defilter.clear();
//			defilter.put("productId", String.valueOf(p.getId()));
//			try {
//				List<DeploymentEnvironment> deList = serviceAllocationService.getDeploymentEnvironments(defilter);
//				p.setDeploymentEnvironments(deList);
//			} catch (IOException e) {
//				logger.error("Error in getting deployment environments: ", e.getMessage());
//			}
//		}
		return list;
    }
}
