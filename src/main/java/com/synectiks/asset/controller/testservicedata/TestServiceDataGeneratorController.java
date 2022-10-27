package com.synectiks.asset.controller.testservicedata;


import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api")
public class TestServiceDataGeneratorController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceDataGeneratorController.class);
	
	
	@GetMapping("/create-test-sd-data")
	public ResponseEntity<ServiceDto> createTestSdData(@RequestParam Map<String, String> obj) throws JsonParseException, JsonMappingException, IOException{
		logger.info("Request to create service detail test data");
		String jsonFilePath = obj.get("jsonFilePath");
		ServiceDto dto = ServiceDto.instantiate(ServiceDto.getHostingType(jsonFilePath));
		dto.readJson(jsonFilePath);
		dto.save();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	

	
}
