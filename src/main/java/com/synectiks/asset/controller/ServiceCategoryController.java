package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.ServiceCategoryService;
import com.synectiks.asset.domain.ServiceCategory;

@RestController
@RequestMapping("/api")
public class ServiceCategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);
	
	@Autowired
	ServiceCategoryService serviceCategoryService;
	
	@GetMapping("/service-category/{id}")
	public ResponseEntity<ServiceCategory> getServiceCategory(@PathVariable Long id) {
		logger.info("Request to get service-category. Id: "+id);
		Optional<ServiceCategory> odp = serviceCategoryService.getServiceCategory(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/service-category")
	public ResponseEntity<List<ServiceCategory>> getAllServiceCategory() {
		logger.info("Request to get service-category");
		List<ServiceCategory> list = serviceCategoryService.getAllServiceCategory();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-category/{id}")
	public ResponseEntity<Optional<ServiceCategory>> deleteServiceCategory(@PathVariable Long id) {
		logger.info("Request to delete service-category by id: {}", id);
		Optional<ServiceCategory> oSpa = serviceCategoryService.deleteServiceCategory(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-category")
	public ResponseEntity<ServiceCategory> createServiceCategory(@RequestBody ServiceCategory obj){
		logger.info("Request to create new service-category");
		ServiceCategory spa = serviceCategoryService.createServiceCategory(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-category")
	public ResponseEntity<ServiceCategory> updateServiceCategory(@RequestBody ServiceCategory obj){
		logger.info("Request to update service-category");
		ServiceCategory spa = serviceCategoryService.updateServiceCategory(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-category")
	public ResponseEntity<Optional<ServiceCategory>> partialUpdateServiceCategory(@RequestBody ServiceCategory obj){
		logger.info("Request to partially update service-category");
		Optional<ServiceCategory> oSpa = serviceCategoryService.partialUpdateServiceCategory(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-category/search")
	public ResponseEntity<List<ServiceCategory>> searchAllServiceCategory(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-category");
		List<ServiceCategory> list = serviceCategoryService.searchAllServiceCategory(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
}
