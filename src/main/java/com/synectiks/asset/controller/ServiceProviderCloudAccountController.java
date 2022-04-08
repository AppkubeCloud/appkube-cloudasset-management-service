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

import com.synectiks.asset.business.service.ServiceProviderCloudAccountService;
import com.synectiks.asset.domain.ServiceProviderCloudAccount;

@RestController
@RequestMapping("/api")
public class ServiceProviderCloudAccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceProviderCloudAccountController.class);
	
	@Autowired
	ServiceProviderCloudAccountService serviceProviderCloudAccountService;
	
	@GetMapping("/provider/account/{id}")
	public ResponseEntity<ServiceProviderCloudAccount> getServiceProviderCloudAccount(@PathVariable Long id) {
		logger.info("Request to get service provider's cloud account. Id: "+id);
		Optional<ServiceProviderCloudAccount> oa = serviceProviderCloudAccountService.getServiceProviderCloudAccount(id);
		if(oa.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(oa.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/provider/account")
	public ResponseEntity<List<ServiceProviderCloudAccount>> getAllServiceProviderCloudAccount() {
		logger.info("Request to get service provider's cloud account");
		List<ServiceProviderCloudAccount> list = serviceProviderCloudAccountService.getAllServiceProviderCloudAccount();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/provider/account/{id}")
	public ResponseEntity<Optional<ServiceProviderCloudAccount>> deleteServiceProviderCloudAccount(@PathVariable Long id) {
		logger.info("Request to delete service provider's cloud account by id: {}", id);
		Optional<ServiceProviderCloudAccount> oSpa = serviceProviderCloudAccountService.deleteServiceProviderCloudAccount(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/provider/account")
	public ResponseEntity<ServiceProviderCloudAccount> createServiceProviderCloudAccount(@RequestBody ServiceProviderCloudAccount obj){
		logger.info("Request to create new service provider's cloud account");
		ServiceProviderCloudAccount spa = serviceProviderCloudAccountService.createServiceProviderCloudAccount(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/provider/account")
	public ResponseEntity<ServiceProviderCloudAccount> updateServiceProviderCloudAccount(@RequestBody ServiceProviderCloudAccount obj){
		logger.info("Request to update service provider's cloud account");
		ServiceProviderCloudAccount spa = serviceProviderCloudAccountService.updateServiceProviderCloudAccount(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/provider/account")
	public ResponseEntity<Optional<ServiceProviderCloudAccount>> partialUpdateServiceProviderCloudAccount(@RequestBody ServiceProviderCloudAccount obj){
		logger.info("Request to partially update service provider's cloud account");
		Optional<ServiceProviderCloudAccount> oSpa = serviceProviderCloudAccountService.partialUpdateServiceProviderCloudAccount(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/provider/account/search")
	public ResponseEntity<List<ServiceProviderCloudAccount>> searchAllServiceProviderCloudAccount(@RequestParam Map<String, String> obj){
		logger.info("Request to search service provider's cloud account");
		List<ServiceProviderCloudAccount> list = serviceProviderCloudAccountService.searchAllServiceProviderCloudAccount(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
