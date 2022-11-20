package com.synectiks.asset.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.CloudService;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.Cloud;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportResponse;

@RestController
@RequestMapping("/api")
public class CloudEnvironmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudEnvironmentController.class);
	
	@Autowired
	CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
	@Autowired
	CloudService cloudService;
	
	@GetMapping("/cloud-environment/{id}")
	public ResponseEntity<CloudEnvironment> getCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to get cloud-environment. Id: "+id);
		Optional<CloudEnvironment> odp = cloudEnvironmentService.getCloudEnvironment(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/cloud-environment")
	public ResponseEntity<List<CloudEnvironment>> getAllCloudEnvironment() {
		logger.info("Request to get cloud-environment");
		List<CloudEnvironment> list = cloudEnvironmentService.getAllCloudEnvironment();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cloud-environment/{id}")
	public ResponseEntity<Optional<CloudEnvironment>> deleteCloudEnvironment(@PathVariable Long id) {
		logger.info("Request to delete cloud-environment by id: {}", id);
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.deleteCloudEnvironment(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cloud-environment")
	public ResponseEntity<CloudEnvironment> createCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to create new cloud-environment");
		CloudEnvironment spa = cloudEnvironmentService.createCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/cloud-environment")
	public ResponseEntity<CloudEnvironment> updateCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to update cloud-environment");
		CloudEnvironment spa = cloudEnvironmentService.updateCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cloud-environment")
	public ResponseEntity<Optional<CloudEnvironment>> partialUpdateCloudEnvironment(@RequestBody CloudEnvironment obj){
		logger.info("Request to partially update cloud-environment");
		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.partialUpdateCloudEnvironment(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
//	@GetMapping("/cloud-environment/search")
//	public ResponseEntity<List<CloudEnvironment>> searchAllCloudEnvironment(@RequestParam Map<String, String> obj){
//		logger.info("Request to search cloud-environment");
//		List<CloudEnvironment> list = cloudEnvironmentService.searchAllCloudEnvironment(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
	
	@GetMapping("/cloud-environment/search")
	public ResponseEntity<List<CloudEnvironment>> searchAllCloudEnvironmentNew(@RequestParam Map<String, String> obj){
		logger.info("Request to search cloud-environment");
		List<CloudEnvironment> cloudEnvironmentList = new ArrayList<>();
		
		Map<String, String> sdSearchMap = new HashMap<>();
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(sdSearchMap);
		
		Map<String, String> cloudMap = new HashMap<>();
		for(ServiceDetail sd: sdr.getServices()) {
			cloudMap.put((String)sd.getMetadata_json().get("cloudType"), (String)sd.getMetadata_json().get("cloudType"));
		}
		
		for(String cloud: cloudMap.keySet()) {
			//filter service detail list for the given cloud
			List<ServiceDetail>cloudList = sdr.getServices().stream().filter(sd -> ((String) sd.getMetadata_json().get("cloudType"))
						.equalsIgnoreCase(cloud)).collect(Collectors.toList());
			
			//filter unique landing zone from cloudList
			Map<String, String> landingZone = new HashMap<>();
			for(ServiceDetail sd: cloudList) {
				landingZone.put((String)sd.getMetadata_json().get("associatedLandingZone"), (String)sd.getMetadata_json().get("associatedLandingZone"));
			}
			
			Map<String, String> searchMap = new HashMap<>();
			for (String key: landingZone.keySet()) {
				searchMap.clear();
				searchMap.put("associatedLandingZone", key);
				
				//filter service detail list for the given landing zone
				List<ServiceDetail>landingZoneList = cloudList.stream().filter(sd -> ((String) sd.getMetadata_json().get("associatedLandingZone"))
							.equalsIgnoreCase(key)).collect(Collectors.toList());
				
				int totalApp = 0 ;
				int totalData = 0;
				Map<String, String> productMap = new HashMap<String, String>();
				Map<String, String> productEnclaveMap = new HashMap<String, String>();
				Integer totalBilling = 0;
				for(ServiceDetail sdDetail: landingZoneList) {
					if("APP".equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) && !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
						totalApp = totalApp + 1;
					}else if("DATA".equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) 
							&& !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
						totalData = totalData + 1;
					}
					productMap.put((String)sdDetail.getMetadata_json().get("associatedProduct"), (String)sdDetail.getMetadata_json().get("associatedProduct"));
					productEnclaveMap.put((String)sdDetail.getMetadata_json().get("associatedProductEnclave"), (String)sdDetail.getMetadata_json().get("associatedProductEnclave"));
					
					totalBilling = totalBilling + Integer.parseInt((String)((Map)sdDetail.getMetadata_json().get("stats")).get("totalCostSoFar"));
				}
				
				CloudEnvironment ce = new CloudEnvironment();
				ce.setTotalAppServices(totalApp);
				ce.setTotalDataServices(totalData);
				ce.setTotalProducts(productMap.size());
				ce.setTotalProductEnclave(productEnclaveMap.size());
				ce.setName(key);
				ce.setDescription(key);
				ce.setAccountId(key);
				ce.setTotalBilling(totalBilling);
//				ce.setAccessKey(cloudEnvObjList.getAccessKey());
//				ce.setSecretKey(cloudEnvObjList.getSecretKey());
//				ce.setRegion(cloudEnvObjList.getRegion());
//				ce.setOrgId(cloudEnvObjList.getOrgId());
//				ce.setStatus(cloudEnvObjList.getStatus());
				Cloud cl = new Cloud();
				cl.setName(cloud);
				ce.setCloud(cl);
				cloudEnvironmentList.add(ce);
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(cloudEnvironmentList);
	}
	
}
