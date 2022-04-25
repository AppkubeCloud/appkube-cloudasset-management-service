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

import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportingResponse;

@RestController
@RequestMapping("/api")
public class ServicesDetailController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServicesDetailController.class);
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
	@GetMapping("/service-detail/{id}")
	public ResponseEntity<ServiceDetail> getServiceDetail(@PathVariable Long id) {
		logger.info("Request to get service-detail. Id: "+id);
		Optional<ServiceDetail> odp = serviceDetailService.getServiceDetail(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/service-detail")
	public ResponseEntity<List<ServiceDetail>> getAllServiceDetail() {
		logger.info("Request to get service-detail");
		List<ServiceDetail> list = serviceDetailService.getAllServiceDetail();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-detail/{id}")
	public ResponseEntity<Optional<ServiceDetail>> deleteServiceDetail(@PathVariable Long id) {
		logger.info("Request to delete service-detail by id: {}", id);
		Optional<ServiceDetail> oSpa = serviceDetailService.deleteServiceDetail(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-detail")
	public ResponseEntity<ServiceDetail> createServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to create new service-detail");
		ServiceDetail spa = serviceDetailService.createServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-detail")
	public ResponseEntity<ServiceDetail> updateServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to update service-detail");
		ServiceDetail spa = serviceDetailService.updateServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-detail")
	public ResponseEntity<Optional<ServiceDetail>> partialUpdateServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to partially update service-detail");
		Optional<ServiceDetail> oSpa = serviceDetailService.partialUpdateServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-detail/search")
	public ResponseEntity<ServiceDetailReportingResponse> searchAllServiceDetail(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-detail");
		ServiceDetailReportingResponse serviceDetailReportingResponse = serviceDetailService.searchServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(serviceDetailReportingResponse);
	}
	
	
}
