package com.synectiks.asset.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.ServiceAnalyticsService;
import com.synectiks.asset.domain.ServiceAnalytics;

@RestController
@RequestMapping("/api")
public class ServiceAnalyticsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceAnalyticsController.class);
	
	@Autowired
	ServiceAnalyticsService serviceAnalyticsService;
	
	@GetMapping("/service-analytics/{id}")
	public ResponseEntity<ServiceAnalytics> getServiceAnalytics(@PathVariable Long id) {
		logger.info("Request to get service-analytics. Id: "+id);
		Optional<ServiceAnalytics> odp = serviceAnalyticsService.getServiceAnalytics(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/service-analytics")
	public ResponseEntity<List<ServiceAnalytics>> getAllServiceAnalytics() {
		logger.info("Request to get service-analytics");
		List<ServiceAnalytics> list = serviceAnalyticsService.getAllServiceAnalytics();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/service-analytics/{id}")
	public ResponseEntity<Optional<ServiceAnalytics>> deleteServiceAnalytics(@PathVariable Long id) {
		logger.info("Request to delete service-analytics by id: {}", id);
		Optional<ServiceAnalytics> oSpa = serviceAnalyticsService.deleteServiceAnalytics(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-analytics")
	public ResponseEntity<ServiceAnalytics> createServiceAnalytics(@RequestBody ServiceAnalytics obj){
		logger.info("Request to create new service-analytics");
		ServiceAnalytics spa = serviceAnalyticsService.createServiceAnalytics(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@GetMapping("/service-analytics/create-bulk-data")
	public ResponseEntity<String> createBulkData(@RequestParam Map<String, String> obj) throws ParseException, InterruptedException{
//			@RequestParam String startDateTime, @RequestParam String endDateTime, @RequestParam String type ) throws ParseException, InterruptedException{
		logger.info("Request to create new service-analytics");
		serviceAnalyticsService.createBulkAnalyticData(obj.get("startDateTime"), obj.get("endDateTime"), obj.get("type"));
		return ResponseEntity.status(HttpStatus.OK).body("Bulk data in progress");
	}
//	
//	@GetMapping("/service-analytics/search")
//	public ResponseEntity<List<ServiceAnalytics>> searchAllServiceAnalytics(@RequestParam Map<String, String> obj){
//		logger.info("Request to search service-analytics");
//		List<ServiceAnalytics> list = serviceAnalyticsService.searchAllServiceAnalytics(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
	
}
