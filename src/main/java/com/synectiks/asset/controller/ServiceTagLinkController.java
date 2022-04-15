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

import com.synectiks.asset.business.service.ServiceTagLinkService;
import com.synectiks.asset.domain.ServiceTagLink;

@RestController
@RequestMapping("/api")
public class ServiceTagLinkController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTagLinkController.class);
	
	@Autowired
	ServiceTagLinkService serviceTagLinkService;
	
	@GetMapping("/service-tag-link/{id}")
	public ResponseEntity<ServiceTagLink> getServiceTagLink(@PathVariable Long id) {
		logger.info("Request to get service-tag-link. Id: "+id);
		Optional<ServiceTagLink> odp = serviceTagLinkService.getServiceTagLink(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/service-tag-link")
	public ResponseEntity<List<ServiceTagLink>> getAllServiceTagLink() {
		logger.info("Request to get service-tag-link");
		List<ServiceTagLink> list = serviceTagLinkService.getAllServiceTagLink();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-tag-link/{id}")
	public ResponseEntity<Optional<ServiceTagLink>> deleteServiceTagLink(@PathVariable Long id) {
		logger.info("Request to delete service-tag-link by id: {}", id);
		Optional<ServiceTagLink> oSpa = serviceTagLinkService.deleteServiceTagLink(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-tag-link")
	public ResponseEntity<ServiceTagLink> createServiceTagLink(@RequestBody ServiceTagLink obj){
		logger.info("Request to create new service-tag-link");
		ServiceTagLink spa = serviceTagLinkService.createServiceTagLink(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-tag-link")
	public ResponseEntity<ServiceTagLink> updateServiceTagLink(@RequestBody ServiceTagLink obj){
		logger.info("Request to update service-tag-link");
		ServiceTagLink spa = serviceTagLinkService.updateServiceTagLink(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-tag-link")
	public ResponseEntity<Optional<ServiceTagLink>> partialUpdateServiceTagLink(@RequestBody ServiceTagLink obj){
		logger.info("Request to partially update service-tag-link");
		Optional<ServiceTagLink> oSpa = serviceTagLinkService.partialUpdateServiceTagLink(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-tag-link/search")
	public ResponseEntity<List<ServiceTagLink>> searchAllServiceTagLink(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-tag-link");
		List<ServiceTagLink> list = serviceTagLinkService.searchAllServiceTagLink(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
}
