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

import com.synectiks.asset.business.service.ServiceBillingService;
import com.synectiks.asset.domain.ServiceBilling;

@RestController
@RequestMapping("/api")
public class ServiceBillingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceBillingController.class);
	
	@Autowired
	ServiceBillingService serviceBillingService;
	
	@GetMapping("/service-billing/{id}")
	public ResponseEntity<ServiceBilling> getServiceBilling(@PathVariable Long id) {
		logger.info("Request to get service-billing. Id: "+id);
		Optional<ServiceBilling> odp = serviceBillingService.getServiceBilling(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/service-billing")
	public ResponseEntity<List<ServiceBilling>> getAllServiceBilling() {
		logger.info("Request to get service-billing");
		List<ServiceBilling> list = serviceBillingService.getAllServiceBilling();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-billing/{id}")
	public ResponseEntity<Optional<ServiceBilling>> deleteServiceBilling(@PathVariable Long id) {
		logger.info("Request to delete service-billing by id: {}", id);
		Optional<ServiceBilling> oSpa = serviceBillingService.deleteServiceBilling(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-billing")
	public ResponseEntity<ServiceBilling> createServiceBilling(@RequestBody ServiceBilling obj){
		logger.info("Request to create new service-billing");
		ServiceBilling spa = serviceBillingService.createServiceBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-billing")
	public ResponseEntity<ServiceBilling> updateServiceBilling(@RequestBody ServiceBilling obj){
		logger.info("Request to update service-billing");
		ServiceBilling spa = serviceBillingService.updateServiceBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-billing")
	public ResponseEntity<Optional<ServiceBilling>> partialUpdateServiceBilling(@RequestBody ServiceBilling obj){
		logger.info("Request to partially update service-billing");
		Optional<ServiceBilling> oSpa = serviceBillingService.partialUpdateServiceBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-billing/search")
	public ResponseEntity<List<ServiceBilling>> searchAllServiceBilling(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-billing");
		List<ServiceBilling> list = serviceBillingService.searchAllServiceBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
