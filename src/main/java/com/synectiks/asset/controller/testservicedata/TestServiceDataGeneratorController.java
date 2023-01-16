package com.synectiks.asset.controller.testservicedata;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceDetailRepository;

@RestController
@RequestMapping("/api")
public class TestServiceDataGeneratorController {

	
	@Autowired
	private ServiceDetailRepository serviceDetailRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceDataGeneratorController.class);
	
		public List<ServiceDetail> searchServiceDetailWithFilter(String Env) {
	
			List<ServiceDetail> list = serviceDetailRepository.findAll();
			List<ServiceDetail> list2 = list.stream().filter((sd) -> sd.getMetadata_json().get("associatedEnv").equals(Env)).collect(Collectors.toList());
			return list2;

			}
			
			
		

	
	@GetMapping("/create-test-sd-data")
	public ResponseEntity<ServiceDto> createTestSdData(@RequestParam Map<String, String> obj) throws JsonParseException, JsonMappingException, IOException{
		logger.info("Request to create service detail test data");
		String jsonFilePath = obj.get("jsonFilePath");
		ServiceDto dto = ServiceDto.instantiate(ServiceDto.getHostingType(jsonFilePath));
		dto.readJson(jsonFilePath);
		dto.toString();
		dto.save();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	

	@GetMapping("/create-bulk-test-data")
	public ResponseEntity<ServiceDto> createTestSdDatas(@RequestParam Map<String, String> obj) throws JsonParseException, JsonMappingException, IOException{
		logger.info("Request to create service detail test data");
		String jsonFilePath = obj.get("jsonFilePath");
		List<File> fil = ServiceDto.listFilesUsingFileWalkAndVisitor(jsonFilePath);
		for(File file : fil){
			ServiceDto dto = ServiceDto.instantiate(ServiceDto.getHostingType(file.toString()));
			dto.readJson(file.toString());
			dto.toString();
			dto.save();
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("getResult/{Env}")
	public List<ServiceDetail> getallservice(@PathVariable String Env){
		List<ServiceDetail> sd = searchServiceDetailWithFilter(Env);
		System.out.println(sd);
		return sd;

	}
	

	
}
