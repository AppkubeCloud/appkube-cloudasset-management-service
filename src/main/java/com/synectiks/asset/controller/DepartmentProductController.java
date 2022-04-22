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

import com.synectiks.asset.business.service.DepartmentProductService;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.Product;

@RestController
@RequestMapping("/api")
public class DepartmentProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentProductController.class);
	
	@Autowired
	DepartmentProductService departmentProductService;
	
	@PostMapping("/department/atach-product/department/{departmentid}/cloud-environment/{cloudEnvId}/product/{productId}")
	public ResponseEntity<Department> atachProduct(@PathVariable Long departmentid, @PathVariable Long cloudEnvId, @PathVariable Long productId){
		logger.info("Request to attach new product with department");
		Department dp = departmentProductService.atachProduct(departmentid, cloudEnvId, productId);
		return ResponseEntity.status(HttpStatus.OK).body(dp);
	}
	
	@PostMapping("/department/detach-product/department/{departmentid}/cloud-environment/{cloudEnvId}/product/{productId}")
	public ResponseEntity<Boolean> detachProduct(@PathVariable Long departmentid, @PathVariable Long cloudEnvId, @PathVariable Long productId){
		logger.info("Request to attach new product with department");
		boolean isRemoved = departmentProductService.detachProduct(departmentid, cloudEnvId, productId);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
	}
	
	@GetMapping("/department/{departmentid}/product")
	public ResponseEntity<List<Product>> getAllProductsOfDepartment(@PathVariable Long departmentid){
		logger.info("Request to get all products of a department");
		List<Product> productList = departmentProductService.getAllProductsOfDepartment(departmentid);
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}
	
	@GetMapping("/department/{departmentid}/cloudEnvironment/{cloudEnvId}/product")
	public ResponseEntity<List<Product>> getAllProducts(@PathVariable Long departmentid, @PathVariable Long cloudEnvId){
		logger.info("Request to get all products of a department and cloud environment");
		List<Product> productList = departmentProductService.getAllProducts(departmentid, cloudEnvId);
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}
}
