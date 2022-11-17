package com.synectiks.asset.controller;

import java.util.HashMap;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.util.UniqueProductUtil;

@RestController
@RequestMapping("/api")
public class AnalyticController {
	
	private static final Logger logger = LoggerFactory.getLogger(AnalyticController.class);
	
	@Autowired
	private UniqueProductUtil uniqueProductUtil;
	
	@GetMapping("/anlytics/cloud-wise-spend")
	public ResponseEntity<Map<String, ObjectNode>> getCloudWiseSpend(@RequestParam Map<String, String> obj){
		logger.info("Request to get cloud wise spend");
		ObjectMapper mapper = Constants.instantiateMapper();
		Map<String, ObjectNode> map = new HashMap<>();
		for(String cloud: Constants.AVAILABLE_CLOUDS) {
			ObjectNode node = mapper.createObjectNode();
			Integer current = RandomUtil.getRandom(50000, 50500);
			Integer prev = RandomUtil.getRandom(40000, 50000);
			Integer variance = (current-prev)*100/current;
			node.put("currentTotal", current);
			node.put("previousTotal", prev);
			node.put("variance", variance);
			map.put(cloud, node);
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@GetMapping("/anlytics/sla-central")
	public ResponseEntity<Map<String, ObjectNode>> getSlaCentralData(@RequestParam Map<String, String> obj){
		logger.info("Request to get sla central anlytics");
		Map<String, ObjectNode> map = new HashMap<>();
		ObjectMapper mapper = Constants.instantiateMapper();
		List<String> productList = null;
		if(Constants.cache.containsKey(Constants.PRODUCT_CACHE_KEY)) {
			productList = (List<String>)Constants.cache.get(Constants.PRODUCT_CACHE_KEY);
		}else {
			productList = uniqueProductUtil.getProducts();
		}
		for(String product: productList) {
			ObjectNode node = mapper.createObjectNode();
			node.put("Performance", RandomUtil.getRandom(90, 100));
			node.put("Availibility", RandomUtil.getRandom(90, 100));
			node.put("Reliability", RandomUtil.getRandom(90, 100));
			node.put("Security", RandomUtil.getRandom(90, 100));
			node.put("End Usage", RandomUtil.getRandom(90, 100));
			map.put(product, node);
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
}
