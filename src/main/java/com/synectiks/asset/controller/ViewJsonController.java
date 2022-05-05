package com.synectiks.asset.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		Long serviceId = Long.parseLong(objectNode.get("serviceId").asText());
		JsonNode jsonNode = objectNode.get("performance");
		
		if(jsonNode != null) {
			Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(serviceId);
			if(osd.isPresent()) {
				ServiceDetail sd = osd.get();
				if(osd.get().getView_json() == null) {
					logger.info("Adding new view json");
					ViewJsonResponse vjr = ViewJsonResponse.from(objectNode.get("serviceId").asText(), jsonNode, "performance");
					sd.setView_json(vjr);
				}else {
					logger.info("Updating view json");
					ViewJsonResponse vjr = ViewJsonResponse.updateFrom(jsonNode, objectNode.get("serviceId").asText(), osd.get().getView_json(), "performance");
					sd.setView_json(vjr);
				}
				serviceDetailService.updateServiceDetail(sd);
				return ResponseEntity.status(HttpStatus.OK).body(sd.getView_json());
			}
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/dashboard/view-json")
	public ResponseEntity<ViewJsonResponse> getViewJson(@RequestParam String serviceId) throws JsonProcessingException {
		logger.info("Getting view json. Service id {}: ", serviceId);
		if(StringUtils.isBlank(serviceId)) {
			throw new BadRequestAlertException("Service id not provided", "ViewJson", "idnotprovided");
		}
		Long id = Long.parseLong(serviceId);
		Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(id);
		if(osd.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(osd.get().getView_json());
		}
		return ResponseEntity.status(HttpStatus.OK).body(ViewJsonResponse.builder().build());
	}
}
