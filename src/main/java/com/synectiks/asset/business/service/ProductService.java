package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
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

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.Services;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.repository.ProductServiceRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
		
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductServiceRepository productServiceRepository;
	
	@Autowired
	ServicesService servicesService;
	
	public Optional<Product> getProduct(Long id) {
		logger.info("Get product by id: {}", id);
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProduct() {
		logger.info("Get all product");
		return productRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Product> deleteProduct(Long id) {
		logger.info("Delete product by id: {}", id);
		Optional<Product> oObj = getProduct(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. product cannot be deleted", id);
			return oObj;
		}
		productRepository.deleteById(id);
		return oObj;
	}
	
	public Product createProduct(Product obj){
		logger.info("Create new product");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return productRepository.save(obj);
	}
	
	public Product updateProduct(Product obj){
		logger.info("Update product. Id: {}", obj.getId());
		if(!productRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Product", "idnotfound");
		}
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return productRepository.save(obj);
	}
	
	public Optional<Product> partialUpdateProduct(Product obj){
		logger.info("Update product partialy. Id: {}", obj.getId());
		if(!productRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Product", "idnotfound");
		}
		
		Optional<Product> result = productRepository.findById(obj.getId())
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
			.map(productRepository::save);
		return result;
	}
	
	public List<Product> searchAllProduct(Map<String, String> obj) {
		logger.info("Search product");
		Product cld = new Product();
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
		
		List<Product> list = null;
		if(isFilter) {
			list = productRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = productRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	
}
