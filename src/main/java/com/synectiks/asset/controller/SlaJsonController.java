package com.synectiks.asset.controller;

import java.util.HashMap;
import java.util.Map;
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
import com.google.gson.Gson;
import com.synectiks.asset.business.service.AwsService;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class SlaJsonController {
	
	private static final Logger logger = LoggerFactory.getLogger(SlaJsonController.class);
	
	@Autowired
	AwsService awsService;
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
	@PostMapping("/service-detail/sla-json")
	public ResponseEntity<Map<String, Object>> slaJson(@RequestBody ObjectNode objectNode) throws JsonProcessingException {
		if(StringUtils.isBlank(objectNode.get("serviceId").asText())) {
			throw new BadRequestAlertException("Service id not provided", "SlaJson", "idnotprovided");
		}
		logger.info("Updating sla_json");
		Long serviceId = Long.parseLong(objectNode.get("serviceId").asText());
		
		JsonNode jsonNode = objectNode.get("slaJson");
		
		if(jsonNode != null) {
			Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(serviceId);
			if(osd.isPresent()) {
				Gson gson = new Gson();
				Map<String,Object> attributes = gson.fromJson(jsonNode.toPrettyString(),Map.class);
				ServiceDetail sd = osd.get();
				sd.setSla_json(attributes);
				serviceDetailService.updateServiceDetailSlaJson(sd);
				return ResponseEntity.status(HttpStatus.OK).body(sd.getSla_json());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/service-detail/sla-json")
	public ResponseEntity<Map<String, Object>> getSlaJson(@RequestParam String serviceId) throws JsonProcessingException {
		logger.info("Getting sla_json. Service id {}: ", serviceId);
		if(StringUtils.isBlank(serviceId)) {
			throw new BadRequestAlertException("Service id not provided", "SlaJson", "idnotprovided");
		}
		Long id = Long.parseLong(serviceId);
		Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(id);
		if(osd.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(osd.get().getSla_json());
		}
		return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>());
	}
	
}
