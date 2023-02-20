package com.synectiks.asset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportResponse;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class CloudEnvironmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudEnvironmentController.class);
	private static final String ENTITY_NAME = "CloudEnvironment";
	
	@Value("jhipster.clientApp.name")
	private String applicationName;
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	private ServiceDetailService serviceDetailService;
	
	@GetMapping("/cloud-environment/{id}")
	public ResponseEntity<CloudEnvironment> getCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to get cloud-environment by Id: "+id);
		Optional<CloudEnvironment> odp = cloudEnvironmentService.getCloudEnvironment(id);
		return ResponseUtil.wrapOrNotFound(odp);
	}
	
	@GetMapping("/cloud-environment")
	public ResponseEntity<List<CloudEnvironment>> getAllCloudEnvironment() {
		logger.info("Request to get all cloud-environments");
		List<CloudEnvironment> list = cloudEnvironmentService.getAllCloudEnvironment();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cloud-environment/{id}")
	public ResponseEntity<Optional<CloudEnvironment>> deleteCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to delete cloud-environment by id: {}", id);
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.deleteCloudEnvironment(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cloud-environment/aws")
	public ResponseEntity<CloudEnvironment> createAwsCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to create new cloud-environment");
		CloudEnvironment spa = cloudEnvironmentService.createAwsCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cloud-environment/aws")
	public ResponseEntity<Optional<CloudEnvironment>> partialUpdateAwsCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to partially update cloud-environment");
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.partialUpdateAwsCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/cloud-environment/search")
	public ResponseEntity<List<CloudEnvironment>> searchAllCloudEnvironmentNew(@RequestParam Map<String, String> obj){
		logger.info("Request to search cloud-environment");
		
		List<CloudEnvironment> list = cloudEnvironmentService.searchAllCloudEnvironment(obj);
		for(CloudEnvironment cloudEnvironment: list) {
			Map<String, String> sdSearchMap = new HashMap<>();
			sdSearchMap.put("associatedLandingZone", cloudEnvironment.getAccountId());
			ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(sdSearchMap);
			int totalApp = 0 ;
			int totalData = 0;
			Map<String, String> productMap = new HashMap<String, String>();
			Map<String, String> productEnclaveMap = new HashMap<String, String>();
			Integer totalBilling = 0;
			
			for(ServiceDetail sdDetail: sdr.getServices()) {
				if(Constants.SERVICE_BUSINESS_APP_SERVICE.equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) && !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
					totalApp = totalApp + 1;
				}else if(Constants.SERVICE_BUSINESS_DATA_SERVICE.equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) 
						&& !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
					totalData = totalData + 1;
				}
				productMap.put((String)sdDetail.getMetadata_json().get("associatedProduct"), (String)sdDetail.getMetadata_json().get("associatedProduct"));
				productEnclaveMap.put((String)sdDetail.getMetadata_json().get("associatedProductEnclave"), (String)sdDetail.getMetadata_json().get("associatedProductEnclave"));
				
				totalBilling = totalBilling + Integer.parseInt((String)((Map)sdDetail.getMetadata_json().get("stats")).get("totalCostSoFar"));
			}
			cloudEnvironment.setTotalAppServices(totalApp);
			cloudEnvironment.setTotalDataServices(totalData);
			cloudEnvironment.setTotalProducts(productMap.size());
			cloudEnvironment.setTotalProductEnclave(productEnclaveMap.size());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
