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

import com.synectiks.asset.business.service.ServiceTagService;
import com.synectiks.asset.domain.ServiceTag;

@RestController
@RequestMapping("/api")
public class ServiceTagController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTagController.class);
	
	@Autowired
	ServiceTagService serviceTagService;
	
	@GetMapping("/service-tag/{id}")
	public ResponseEntity<ServiceTag> getServiceTag(@PathVariable Long id) {
		logger.info("Request to get service-tag. Id: "+id);
		Optional<ServiceTag> odp = serviceTagService.getServiceTag(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/service-tag")
	public ResponseEntity<List<ServiceTag>> getAllServiceTag() {
		logger.info("Request to get service-tag");
		List<ServiceTag> list = serviceTagService.getAllServiceTag();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-tag/{id}")
	public ResponseEntity<Optional<ServiceTag>> deleteServiceTag(@PathVariable Long id) {
		logger.info("Request to delete service-tag by id: {}", id);
		Optional<ServiceTag> oSpa = serviceTagService.deleteServiceTag(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-tag")
	public ResponseEntity<ServiceTag> createServiceTag(@RequestBody ServiceTag obj){
		logger.info("Request to create new service-tag");
		ServiceTag spa = serviceTagService.createServiceTag(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-tag")
	public ResponseEntity<ServiceTag> updateServiceTag(@RequestBody ServiceTag obj){
		logger.info("Request to update service-tag");
		ServiceTag spa = serviceTagService.updateServiceTag(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-tag")
	public ResponseEntity<Optional<ServiceTag>> partialUpdateServiceTag(@RequestBody ServiceTag obj){
		logger.info("Request to partially update service-tag");
		Optional<ServiceTag> oSpa = serviceTagService.partialUpdateServiceTag(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-tag/search")
	public ResponseEntity<List<ServiceTag>> searchAllServiceTag(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-tag");
		List<ServiceTag> list = serviceTagService.searchAllServiceTag(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
}
