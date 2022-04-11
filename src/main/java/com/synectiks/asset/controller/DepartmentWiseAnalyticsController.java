package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.DepartmentWiseAnalyticsService;
import com.synectiks.asset.domain.Department;

@RestController
@RequestMapping("/api")
public class DepartmentWiseAnalyticsController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentWiseAnalyticsController.class);
	
	@Autowired
	DepartmentWiseAnalyticsService departmentWiseAnalyticsService;
	
	@GetMapping("/department-wise-analytics/search")
	public ResponseEntity<List<Department>> searchAllDepartment(@RequestParam Map<String, String> obj){
		logger.info("Request to search department-wise-analytics");
		List<Department> list = departmentWiseAnalyticsService.searchDepartmentWiseStats(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
