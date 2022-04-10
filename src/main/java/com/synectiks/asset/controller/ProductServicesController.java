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
	
	@PostMapping("/product/attach-service/product/{productId}/service/{serviceId}")
	public ResponseEntity<Product> attachService(@PathVariable Long productId, @PathVariable Long serviceId){
		logger.info("Request to attach new service with product");
		Product spa = productServicesService.attachService(productId, serviceId);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
}
