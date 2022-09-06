package com.synectiks.asset.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.AwsService;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ViewJsonResponse;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class ViewJsonController {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewJsonController.class);
	
	@Autowired
	AwsService awsService;
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
	@PostMapping("/dashboard/view-json")
	public ResponseEntity<ViewJsonResponse> viewJson(@RequestBody ObjectNode objectNode) throws JsonProcessingException {
		if(StringUtils.isBlank(objectNode.get("serviceId").asText())) {
			throw new BadRequestAlertException("Service id not provided", "ViewJson", "idnotprovided");
		}
		serviceDetailService.updateViewJson(objectNode);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/dashboard/view-json")
	public ResponseEntity<ViewJsonResponse> getViewJson(@RequestParam String serviceId) throws JsonProcessingException {
		logger.info("Getting view json. Service id {}: ", serviceId);
		if(StringUtils.isBlank(serviceId)) {
			throw new BadRequestAlertException("Service id not provided", "ViewJson", "idnotprovided");
		}
		Long id = Long.parseLong(serviceId);
		Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(id);
		if(osd.isPresent() && osd.get().getView_json() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(osd.get().getView_json());
		}
		return ResponseEntity.status(HttpStatus.OK).body(ViewJsonResponse.builder().build());
	}
	
	@DeleteMapping("/dashboard/view-json")
	public ResponseEntity<ViewJsonResponse> updateViewJson(@RequestBody ObjectNode[] objArray) throws JsonProcessingException {
		for(JsonNode node: objArray) {
			serviceDetailService.updateViewJson(node);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
}
