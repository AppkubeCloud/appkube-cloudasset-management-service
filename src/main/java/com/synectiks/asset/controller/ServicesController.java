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

import com.synectiks.asset.business.service.ServicesService;
import com.synectiks.asset.domain.Services;

@RestController
@RequestMapping("/api")
public class ServicesController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServicesController.class);
	
	@Autowired
	ServicesService servicesService;
	
	@GetMapping("/services/{id}")
	public ResponseEntity<Services> getServices(@PathVariable Long id) {
		logger.info("Request to get services. Id: "+id);
		Optional<Services> odp = servicesService.getServices(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/services")
	public ResponseEntity<List<Services>> getAllServices() {
		logger.info("Request to get services");
		List<Services> list = servicesService.getAllServices();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/services/{id}")
	public ResponseEntity<Optional<Services>> deleteServices(@PathVariable Long id) {
		logger.info("Request to delete services by id: {}", id);
		Optional<Services> oSpa = servicesService.deleteServices(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/services")
	public ResponseEntity<Services> createServices(@RequestBody Services obj){
		logger.info("Request to create new services");
		Services spa = servicesService.createServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/services")
	public ResponseEntity<Services> updateServices(@RequestBody Services obj){
		logger.info("Request to update services");
		Services spa = servicesService.updateServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/services")
	public ResponseEntity<Optional<Services>> partialUpdateServices(@RequestBody Services obj){
		logger.info("Request to partially update services");
		Optional<Services> oSpa = servicesService.partialUpdateServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/services/search")
	public ResponseEntity<List<Services>> searchAllServices(@RequestParam Map<String, String> obj){
		logger.info("Request to search services");
		List<Services> list = servicesService.searchAllServices(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
}
