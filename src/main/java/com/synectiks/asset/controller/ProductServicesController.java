package com.synectiks.asset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.ProductServicesService;
import com.synectiks.asset.domain.Product;

@RestController
@RequestMapping("/api")
public class ProductServicesController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServicesController.class);
	
	@Autowired
	ProductServicesService productServicesService;
	
	@PostMapping("/product/atach-service/product/{productId}/service/{serviceId}")
	public ResponseEntity<Product> atachService(@PathVariable Long productId, @PathVariable Long serviceId){
		logger.info("Request to attach new service with product");
		Product spa = productServicesService.atachService(productId, serviceId);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PostMapping("/product/detach-service/product/{productId}/service/{serviceId}")
	public ResponseEntity<Boolean> detachService(@PathVariable Long productId, @PathVariable Long serviceId){
		logger.info("Request to detach service from product");
		boolean isRemoved = productServicesService.detachService(productId, serviceId);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
	}
}
