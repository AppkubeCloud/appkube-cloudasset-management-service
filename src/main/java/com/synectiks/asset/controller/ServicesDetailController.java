package com.synectiks.asset.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.CfgCacheConfigService;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.config.Converter;
import com.synectiks.asset.domain.CfgCacheConfig;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.AvailabilityResponse;
import com.synectiks.asset.response.DataProtectionResponse;
import com.synectiks.asset.response.PerformanceResponse;
import com.synectiks.asset.response.SecurityResponse;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.UserExperianceResponse;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class ServicesDetailController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServicesDetailController.class);
	
	@Autowired
	private ServiceDetailService serviceDetailService;
	
	@Autowired
	private CfgCacheConfigService cfgCacheConfigService;
	
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
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/service-detail")
	public ResponseEntity<ServiceDetail> createServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to create new service-detail");
		ServiceDetail spa = serviceDetailService.createServiceDetail(obj);
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/service-detail")
	public ResponseEntity<ServiceDetail> updateServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to update service-detail");
		ServiceDetail spa = serviceDetailService.updateServiceDetail(obj);
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/service-detail")
	public ResponseEntity<Optional<ServiceDetail>> partialUpdateServiceDetail(@RequestBody ServiceDetail obj){
		logger.info("Request to partially update service-detail");
		Optional<ServiceDetail> oSpa = serviceDetailService.partialUpdateServiceDetail(obj);
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/service-detail/search")
	public ResponseEntity<ServiceDetailReportResponse> search(@RequestParam Map<String, String> obj){
		logger.info("Request to search service-detail");
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
		List<ServiceDetail> list = new ArrayList<>();
		
		if(sdr.getServices() != null && sdr.getServices().size() > 0) {
			for(ServiceDetail sdObj: sdr.getServices()) {
				sdObj.getMetadata_json().put("performance", PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
				sdObj.getMetadata_json().put("availability", AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
				sdObj.getMetadata_json().put("security", SecurityResponse.builder().score(RandomUtil.getRandom()).build());
				sdObj.getMetadata_json().put("dataProtection", DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
				sdObj.getMetadata_json().put("userExperiance", UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
				list.add(sdObj);
			}
			sdr.setServices(list);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(sdr);
	}
	
	@PostMapping("/service-detail/create-bulk-data")
	public ResponseEntity<List<ServiceDetail>> createBulkData(@RequestBody ObjectNode objNode) throws IOException {
		logger.info("Request to create bulk service-detail data");
		serviceDetailService.createBulkData(objNode);
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		return getAllServiceDetail();
	}
	
	@GetMapping("/service-detail/search-with-filter")
	public ResponseEntity<ServiceDetailReportResponse> searchServiceDetailWithFilter(@RequestParam Map<String, String> obj){
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
	
	@GetMapping("/service-detail/transform")
	public ResponseEntity<Object> change() throws IOException {
		logger.info("Request to transform service-detail data");
		Object m =serviceDetailService.transformServiceDetailsListToTree();
		return ResponseEntity.status(HttpStatus.OK).body(m);
	}
	
	@GetMapping("/service-detail/enable-monitoring")
	public ResponseEntity<Object> enableMonitoring(@RequestParam Map<String, String> obj) throws Exception {
		logger.info("Request to deploy all the dashboards for each service");
		serviceDetailService.enableMonitoring(obj);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@PostMapping("/service-detail/view-json")
	public ResponseEntity<Map<String, Object>> viewJson(@RequestBody ObjectNode objectNode) throws IOException {
		if(StringUtils.isBlank(objectNode.get("serviceId").asText())) {
			throw new BadRequestAlertException("Service id not provided", "ViewJson", "idnotprovided");
		}
		serviceDetailService.updateViewJson(objectNode);
		return getViewJson(objectNode.get("serviceId").asText());
	}
	
	@GetMapping("/service-detail/view-json")
	public ResponseEntity<Map<String, Object>> getViewJson(@RequestParam String serviceId) throws JsonProcessingException {
		logger.info("Getting view json. Service id {}: ", serviceId);
		if(StringUtils.isBlank(serviceId)) {
			throw new BadRequestAlertException("Service id not provided", "ViewJson", "idnotprovided");
		}
		Long id = Long.parseLong(serviceId);
		Optional<ServiceDetail> osd = serviceDetailService.getServiceDetail(id);
		if(osd.isPresent() && osd.get().getView_json() != null) {
			return ResponseEntity.status(HttpStatus.OK).body(osd.get().getView_json());
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	

	@PostMapping("/service-detail/add-new-field")
	public void createNewFieldInServiceDetail(@RequestBody ObjectNode obj) throws IOException{
		logger.info("Request to create new filed in service-detail {}",obj);
		String filePath = obj.get("filePath").asText();
		String newKey = obj.get("newKey").asText();
		File f = new File(filePath);
		if(f.isDirectory()) {
			for(File file: f.listFiles()) {
				
				System.out.println(file.getName());
				
				//read contents of a file into string
				String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
				
				//convert that string into json
				ObjectNode objNode = Converter.fromJsonString(content, ObjectNode.class);
				ArrayNode arrayNode = Constants.instantiateMapper().createArrayNode();
				
				//iterate each json and add new field
				JsonNode objArray = objNode.get("services");
				for (JsonNode node : objArray) {
					ObjectNode objectNode = (ObjectNode)node;
					if("data".equalsIgnoreCase(objectNode.get("serviceType").asText())) {
						String description = objectNode.get("description").asText();
						if(description.toLowerCase().contains("Postgresql".toLowerCase()) || description.toLowerCase().contains("Postgres".toLowerCase())) {
							objectNode.put(newKey, "Postgresql");
						}else if(description.toLowerCase().contains("Radis".toLowerCase()) || description.toLowerCase().contains("Redis".toLowerCase())) {
							objectNode.put(newKey, "Redis");
						}
						else if(isContain(description, "ES") || isContain(description, "ElasticSearch")) {
							objectNode.put(newKey, "ElasticSearch");
						}
//						else if(description.toLowerCase().contentEquals("ES".toLowerCase()) || description.toLowerCase().contentEquals("ElasticSearch".toLowerCase())) {
//							objectNode.put(newKey, "ElasticSearch");
//						}
						else if(description.toLowerCase().contains("GIT".toLowerCase()) || description.toLowerCase().contains("Github".toLowerCase())) {
							objectNode.put(newKey, "GIT");
						}
						else if(description.toLowerCase().contains("Mongodb".toLowerCase()) || description.toLowerCase().contains("Mongo".toLowerCase())) {
							objectNode.put(newKey, "Mongodb");
						}else if(description.toLowerCase().contains("DynamoDB".toLowerCase())) {
							objectNode.put(newKey, "DynamoDB");
						}else if(description.toLowerCase().contains("s3".toLowerCase())) {
							objectNode.put(newKey, "S3 ObjectStore");
						}else if(description.toLowerCase().contains("CDN".toLowerCase())) {
							objectNode.put(newKey, "CDN data service");
						}else if(description.toLowerCase().contains("Druid s3 database".toLowerCase())) {
							objectNode.put(newKey, "Druid s3");
						}else {
							objectNode.put(newKey, "");
						}
						
					}
					arrayNode.add(objectNode);
				}
				objNode.put("services",arrayNode);
				
				//convert the json into string
				String updatedContents = Converter.toPrettyJsonString(objNode, String.class);
				
				//write the string back to file
				Files.write(Paths.get(file.getAbsolutePath()), updatedContents.getBytes());
				createBulkData(objNode);
			}
		}
	}
	
	@PostMapping("/service-detail/update-field")
	public void updateFieldInServiceDetail(@RequestBody ObjectNode obj) throws IOException{
		logger.info("Request to update a filed in service-detail {}",obj);
		String filePath = obj.get("filePath").asText();
		String key = obj.get("key").asText();
		String value = obj.get("value").asText();
		File f = new File(filePath);
		if(f.isDirectory()) {
			for(File file: f.listFiles()) {
				
				System.out.println(file.getName());
				
				//read contents of a file into string
				String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
				
				//convert that string into json
				ObjectNode objNode = Converter.fromJsonString(content, ObjectNode.class);
				ArrayNode arrayNode = Constants.instantiateMapper().createArrayNode();
				
				//iterate each json and add new field
				JsonNode objArray = objNode.get("services");
				for (JsonNode node : objArray) {
					ObjectNode objectNode = (ObjectNode)node;
					objectNode.put(key, value);
					arrayNode.add(objectNode);
				}
				objNode.put("services",arrayNode);
				
				//convert the json into string
				String updatedContents = Converter.toPrettyJsonString(objNode, String.class);
				//write the string back to file
				Files.write(Paths.get(file.getAbsolutePath()), updatedContents.getBytes());
				
			}
		}
	}
	
	private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
   }
}
