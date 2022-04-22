package com.synectiks.asset.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.ProductServicesService;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.Services;

@RestController
@RequestMapping("/api")
public class ProductServicesController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServicesController.class);
	
	@Autowired
	ProductServicesService productServicesService;
	
	@PostMapping("/product/atach-service/product/{productId}/service/{serviceId}")
	public ResponseEntity<Product> atachService(@PathVariable Long productId, @PathVariable Long serviceId){
		logger.info("Request to attach new service with product");
		Product spa = productServicesService.atachService(productId, serviceId, null);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PostMapping("/product/atach-service/product/{productId}/service/{serviceId}/depEnv/{depEnvId}")
	public ResponseEntity<Product> atachWithDepEvnService(@PathVariable Long productId, @PathVariable Long serviceId, @PathVariable Long depEnvId){
		logger.info("Request to attach new service with product");
		Product spa = productServicesService.atachService(productId, serviceId, depEnvId);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PostMapping("/product/detach-service/product/{productId}/service/{serviceId}")
	public ResponseEntity<Boolean> detachService(@PathVariable Long productId, @PathVariable Long serviceId){
		logger.info("Request to detach service from product");
		boolean isRemoved = productServicesService.detachService(productId, serviceId, null);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
	}
	
	@PostMapping("/product/detach-service/product/{productId}/service/{serviceId}/depEnv/{depEnvId}")
	public ResponseEntity<Boolean> detachWithDepEvnService(@PathVariable Long productId, @PathVariable Long serviceId, @PathVariable Long depEnvId){
		logger.info("Request to detach service from product");
		boolean isRemoved = productServicesService.detachService(productId, serviceId, depEnvId);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
	}
	
	@GetMapping("/product/{productId}/depEnv/{depEnvId}")
	public ResponseEntity<List<Services>> detachWithDepEvnService(@PathVariable Long productId, @PathVariable Long depEnvId){
		logger.info("Request to get all service of a product and deployment environment");
		List<Services> servicesList = productServicesService.getAllServices(productId, depEnvId);
		return ResponseEntity.status(HttpStatus.OK).body(servicesList);
	}
}
