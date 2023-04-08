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
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public Product save(Product product) {
        logger.debug("Request to save Product : {}", product);
        return productRepository.save(product);
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

                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        logger.debug("Request to get all Products");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        logger.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
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
        return productRepository.findAll(Example.of(product), Sort.by(Sort.Direction.DESC, "name"));
    }
}
