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

import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.domain.CloudEnvironment;

@RestController
@RequestMapping("/cloudasset/api")
public class CloudEnvironmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudEnvironmentController.class);
	
	@Autowired
	CloudEnvironmentService cloudEnvironmentService;
	
	@GetMapping("/cloud-environment/{id}")
	public ResponseEntity<CloudEnvironment> getCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to get cloud-environment. Id: "+id);
		Optional<CloudEnvironment> odp = cloudEnvironmentService.getCloudEnvironment(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/cloud-environment")
	public ResponseEntity<List<CloudEnvironment>> getAllCloudEnvironment() {
		logger.info("Request to get cloud-environment");
		List<CloudEnvironment> list = cloudEnvironmentService.getAllCloudEnvironment();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cloud-environment/{id}")
	public ResponseEntity<Optional<CloudEnvironment>> deleteCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to delete cloud-environment by id: {}", id);
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.deleteCloudEnvironment(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cloud-environment")
	public ResponseEntity<CloudEnvironment> createCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to create new cloud-environment");
		CloudEnvironment spa = cloudEnvironmentService.createCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/cloud-environment")
	public ResponseEntity<CloudEnvironment> updateCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to update cloud-environment");
		CloudEnvironment spa = cloudEnvironmentService.updateCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cloud-environment")
	public ResponseEntity<Optional<CloudEnvironment>> partialUpdateCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to partially update cloud-environment");
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.partialUpdateCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/cloud-environment/search")
	public ResponseEntity<List<CloudEnvironment>> searchAllCloudEnvironment(@RequestParam Map<String, String> obj){
		logger.info("Request to search cloud-environment");
		List<CloudEnvironment> list = cloudEnvironmentService.searchAllCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
