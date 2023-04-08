package com.synectiks.asset.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.CfgCacheConfigService;
import com.synectiks.asset.business.service.DepartmentWiseAnalyticsService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.CfgCacheConfig;
import com.synectiks.asset.response.DepartmentWiseAnaliticResponse;

@RestController
@RequestMapping("/api")
public class DepartmentWiseAnalyticsController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentWiseAnalyticsController.class);
	
	@Autowired
	private DepartmentWiseAnalyticsService departmentWiseAnalyticsService;
	
	@Autowired
	private CfgCacheConfigService cfgCacheConfigService;
	
//	@Deprecated
//	@GetMapping("/department-wise-analytics/search")
//	public ResponseEntity<List<Department>> searchAllDepartment(@RequestParam Map<String, String> obj){
//		logger.info("Request to search department-wise-analytics");
//		List<Department> list = departmentWiseAnalyticsService.searchDepartmentWiseStats(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
//	
//	@Deprecated
//	@GetMapping("/department-wise-analytics/get")
//	public ResponseEntity<DepartmentWiseAnaliticResponse> getAllDepartment(@RequestParam Map<String, String> obj){
//		logger.info("Request to get department-wise-analytics");
//		DepartmentWiseAnaliticResponse resp = departmentWiseAnalyticsService.getDepartmentWiseStats(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(resp);
//	}
	
	@GetMapping("/department-wise-analytics/get-data")
	public ResponseEntity<DepartmentWiseAnaliticResponse> getAnalyticalData(@RequestParam Map<String, String> obj){
		logger.info("Request to get department-wise-analytics from json");
		DepartmentWiseAnaliticResponse resp = departmentWiseAnalyticsService.getAnalyticalDataFromJson(obj);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	
	@GetMapping("/department-wise-analytics/update-cache")
	public ResponseEntity<CfgCacheConfig> updateCache(){
		logger.info("Request to update department-wise-analytics cache");
		Optional<CfgCacheConfig> oCache= cfgCacheConfigService.getCfgCacheConfigByName(Constants.DEPARTMENT_WISE_ANALYTICS_CACHE_KEY);
		CfgCacheConfig ccf = oCache.get();
		ccf.setDirtyFlag(Boolean.TRUE);
		cfgCacheConfigService.updateCfgCacheConfig(ccf);
		Map<String, String> obj = new HashMap<>();
		departmentWiseAnalyticsService.getAnalyticalDataFromJson(obj);
		return ResponseEntity.status(HttpStatus.OK).body(ccf);
	}
}
