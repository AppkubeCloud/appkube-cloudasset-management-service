package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.CloudElementService;
import com.synectiks.asset.domain.CloudElement;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class CloudElementController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudElementController.class);
	private static final String ENTITY_NAME = "CloudElement";
	
	@Value("jhipster.clientApp.name")
	private String applicationName;
	
	@Autowired
	private CloudElementService cloudElementService;
	
	@GetMapping("/cloud-element/{id}")
	public ResponseEntity<CloudElement> getCloudElement(@PathVariable Long id) {
		logger.info("Request to get cloud-element by Id: "+id);
		Optional<CloudElement> odp = cloudElementService.getCloudElement(id);
		return ResponseUtil.wrapOrNotFound(odp);
	}
	
	@GetMapping("/cloud-element")
	public ResponseEntity<List<CloudElement>> getAllCloudElement() {
		logger.info("Request to get all cloud-elements");
		List<CloudElement> list = cloudElementService.getAllCloudElement();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cloud-element/{id}")
	public ResponseEntity<Optional<CloudElement>> deleteCloudElement(@PathVariable Long id) {
		logger.info("Request to delete cloud-element by id: {}", id);
		Optional<CloudElement> oSpa = cloudElementService.deleteCloudElement(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cloud-element")
	public ResponseEntity<CloudElement> createCloudElement(@RequestBody CloudElement obj){
		logger.info("Request to create new cloud-element");
		CloudElement spa = cloudElementService.createCloudElement(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cloud-element")
	public ResponseEntity<Optional<CloudElement>> partialUpdateCloudElement(@RequestBody CloudElement obj){
		logger.info("Request to partially update cloud-element");
		Optional<CloudElement> oSpa = cloudElementService.partialUpdateCloudElement(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/cloud-element/search")
	public ResponseEntity<List<CloudElement>> searchAllCloudElement(@RequestParam Map<String, String> obj){
		logger.info("Request to search cloud-element");
		List<CloudElement> list = cloudElementService.searchAllCloudElement(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
