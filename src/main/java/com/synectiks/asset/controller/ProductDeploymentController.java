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

import com.synectiks.asset.business.service.ProductDeploymentService;
import com.synectiks.asset.domain.ProductDeployment;

@RestController
@RequestMapping("/api")
public class ProductDeploymentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductDeploymentController.class);
	
	@Autowired
	ProductDeploymentService productDeploymentService;
	
	@PostMapping("/product-deployment/deploy/department/{departmentid}/product/{productId}/deployment-environment/{deploymentEnvironmentId}")
	public ResponseEntity<ProductDeployment> deploy(@PathVariable Long departmentid, @PathVariable Long productId, @PathVariable Long deploymentEnvironmentId){
		logger.info("Request to deploy a product");
		ProductDeployment pd = productDeploymentService.deploy(departmentid, productId, deploymentEnvironmentId);
		return ResponseEntity.status(HttpStatus.OK).body(pd);
	}
	
	@PostMapping("/product-deployment/undeploy/department/{departmentid}/product/{productId}/deployment-environment/{deploymentEnvironmentId}")
	public ResponseEntity<Boolean> undeploy(@PathVariable Long departmentid, @PathVariable Long productId, @PathVariable Long deploymentEnvironmentId){
		logger.info("Request to deploy a product");
		boolean isRemoved = productDeploymentService.unDeploy(departmentid, productId, deploymentEnvironmentId);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
	}
}
