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

import com.synectiks.asset.business.service.CloudService;
import com.synectiks.asset.domain.Cloud;

@RestController
@RequestMapping("/api")
public class CloudController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudController.class);
	
	@Autowired
	CloudService cloudService;
	
	@GetMapping("/cloud/{id}")
	public ResponseEntity<Cloud> getCloud(@PathVariable Long id) {
		logger.info("Request to get cloud. Id: "+id);
		Optional<Cloud> odp = cloudService.getCloud(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/cloud")
	public ResponseEntity<List<Cloud>> getAllCloud() {
		logger.info("Request to get cloud");
		List<Cloud> list = cloudService.getAllCloud();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cloud/{id}")
	public ResponseEntity<Optional<Cloud>> deleteCloud(@PathVariable Long id) {
		logger.info("Request to delete cloud by id: {}", id);
		Optional<Cloud> oSpa = cloudService.deleteCloud(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cloud")
	public ResponseEntity<Cloud> createCloud(@RequestBody Cloud obj){
		logger.info("Request to create new cloud");
		Cloud spa = cloudService.createCloud(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/cloud")
	public ResponseEntity<Cloud> updateCloud(@RequestBody Cloud obj){
		logger.info("Request to update cloud");
		Cloud spa = cloudService.updateCloud(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cloud")
	public ResponseEntity<Optional<Cloud>> partialUpdateCloud(@RequestBody Cloud obj){
		logger.info("Request to partially update cloud");
		Optional<Cloud> oSpa = cloudService.partialUpdateCloud(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/cloud/search")
	public ResponseEntity<List<Cloud>> searchAllCloud(@RequestParam Map<String, String> obj){
		logger.info("Request to search cloud");
		List<Cloud> list = cloudService.searchAllCloud(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
