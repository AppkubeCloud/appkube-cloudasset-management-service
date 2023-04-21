package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.HashMap;
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
	private ServiceAllocationService departmentProductEnvService;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public Product save(Product product) {
        logger.debug("Request to save Product : {}", product);
        Product p = productRepository.save(product);
        if(p != null) {
			Map<String, String> filter = new HashMap<>();
			filter.put("productId", String.valueOf(p.getId()));
			try {
				List<DeploymentEnvironment> deList = departmentProductEnvService.getDeploymentEnvironments(filter);
				p.setDeploymentEnvironments(deList);
			} catch (IOException e) {
				logger.error("Error in getting deployment environments: ", e.getMessage());
			}
		}
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
                if (product.getStatus() != null) {
                    existingProduct.setStatus(product.getStatus());
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

                Map<String, String> filter = new HashMap<>();
    			filter.put("productId", String.valueOf(existingProduct.getId()));
    			try {
    				List<DeploymentEnvironment> pList = departmentProductEnvService.getDeploymentEnvironments(filter);
    				existingProduct.setDeploymentEnvironments(pList);
    			} catch (IOException e) {
    				logger.error("Error in getting deployment environments ", e.getMessage());
    			}
    			
                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        logger.debug("Request to get all Products");
        List<Product> list = productRepository.findAll();
        Map<String, String> filter = new HashMap<>();
		for(Product product: list) {
			filter.clear();
			filter.put("productId", String.valueOf(product.getId()));
			try {
				List<DeploymentEnvironment> deList = departmentProductEnvService.getDeploymentEnvironments(filter);
				product.setDeploymentEnvironments(deList);
			} catch (IOException e) {
				logger.error("Error in getting deployment environments: ", e.getMessage());
			}
		}
		return list;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        logger.debug("Request to get Product : {}", id);
        Optional<Product> op = productRepository.findById(id);
        if(op.isPresent()) {
			Map<String, String> filter = new HashMap<>();
			filter.put("productId", String.valueOf(op.get().getId()));
			try {
				List<DeploymentEnvironment> deList = departmentProductEnvService.getDeploymentEnvironments(filter);
				op.get().setDeploymentEnvironments(deList);
			} catch (IOException e) {
				logger.error("Error in getting deployment environments: ", e.getMessage());
			}
		}
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
        
        Map<String, String> defilter = new HashMap<>();
		for(Product p: list) {
			defilter.clear();
			defilter.put("productId", String.valueOf(p.getId()));
			try {
				List<DeploymentEnvironment> deList = departmentProductEnvService.getDeploymentEnvironments(defilter);
				p.setDeploymentEnvironments(deList);
			} catch (IOException e) {
				logger.error("Error in getting deployment environments: ", e.getMessage());
			}
		}
		return list;
    }
}
