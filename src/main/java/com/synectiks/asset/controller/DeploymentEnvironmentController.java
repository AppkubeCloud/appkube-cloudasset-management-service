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

import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.domain.DeploymentEnvironment;

@RestController
@RequestMapping("/api")
public class DeploymentEnvironmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentEnvironmentController.class);
	
	@Autowired
	DeploymentEnvironmentService deploymentEnvironmentService;
	
	@GetMapping("/deployment-environment/{id}")
	public ResponseEntity<DeploymentEnvironment> getDeploymentEnvironment(@PathVariable Long id) {
		logger.info("Request to get deployment-environment. Id: "+id);
		Optional<DeploymentEnvironment> odp = deploymentEnvironmentService.getDeploymentEnvironment(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/deployment-environment")
	public ResponseEntity<List<DeploymentEnvironment>> getAllDeploymentEnvironment() {
		logger.info("Request to get deployment-environment");
		List<DeploymentEnvironment> list = deploymentEnvironmentService.getAllDeploymentEnvironment();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/deployment-environment/{id}")
	public ResponseEntity<Optional<DeploymentEnvironment>> deleteDeploymentEnvironment(@PathVariable Long id) {
		logger.info("Request to delete deployment-environment by id: {}", id);
		Optional<DeploymentEnvironment> oSpa = deploymentEnvironmentService.deleteDeploymentEnvironment(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/deployment-environment")
	public ResponseEntity<DeploymentEnvironment> createDeploymentEnvironment(@RequestBody DeploymentEnvironment obj){
		logger.info("Request to create new deployment-environment");
		DeploymentEnvironment spa = deploymentEnvironmentService.createDeploymentEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/deployment-environment")
	public ResponseEntity<DeploymentEnvironment> updateDeploymentEnvironment(@RequestBody DeploymentEnvironment obj){
		logger.info("Request to update deployment-environment");
		DeploymentEnvironment spa = deploymentEnvironmentService.updateDeploymentEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/deployment-environment")
	public ResponseEntity<Optional<DeploymentEnvironment>> partialUpdateDeploymentEnvironment(@RequestBody DeploymentEnvironment obj){
		logger.info("Request to partially update deployment-environment");
		Optional<DeploymentEnvironment> oSpa = deploymentEnvironmentService.partialUpdateDeploymentEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/deployment-environment/search")
	public ResponseEntity<List<DeploymentEnvironment>> searchAllDeploymentEnvironment(@RequestParam Map<String, String> obj){
		logger.info("Request to search deployment-environment");
		List<DeploymentEnvironment> list = deploymentEnvironmentService.searchAllDeploymentEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
