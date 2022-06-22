package com.synectiks.asset.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.ViewJsonResponse;
import com.synectiks.asset.util.CryptoUtil;

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
	public ResponseEntity<ServiceDetailReportResponse> searchAllServiceDetail(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-detail");
		ServiceDetailReportResponse sdr = serviceDetailService.searchAllServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(sdr);
	}
	
	@PostMapping("/service-detail/create-bulk-data")
	public ResponseEntity<ViewJsonResponse> createBulkData(@RequestBody ObjectNode objNode) throws IOException {
		JsonNode objArray = objNode.get("services");
		for(JsonNode node: objArray) {
			serviceDetailService.createBulkData(node);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/service-detail/search-with-filter")
	public ResponseEntity<ServiceDetailReportResponse> searchServiceDetailWithFilter(@RequestParam Map<String, String> obj){
//		logger.info("Request to search service-detail with filter parameters");
//		Map<String, String> obj = null;
//		if(!StringUtils.isBlank(params)) {
//			String keyValuePairs = CryptoUtil.decrypt(params);
//			if(!StringUtils.isBlank(keyValuePairs)) {
//				obj = convertKeyValuePairToMap(keyValuePairs);
//			}else {
//				logger.error("Decryption failed. Returning error FAILED_DEPENDENCY. Error code: "+HttpStatus.FAILED_DEPENDENCY);
//				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
//			}
//		}else {
//			obj = new HashMap<>();
//		}
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
		return ResponseEntity.status(HttpStatus.OK).body(sdr);
	}
	
	public Map<String, String> convertKeyValuePairToMap(String keyValuePairs) {
		String dt[] = keyValuePairs.split("&");
		Map<String, String> obj = new HashMap<>();
		for(String pair: dt) {
			String tmp [] = pair.split("=");
			obj.put(tmp[0], tmp[1]);
		}
		return obj;
	}
}
