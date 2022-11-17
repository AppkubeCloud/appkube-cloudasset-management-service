package com.synectiks.asset.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.CfgCacheConfig;
import com.synectiks.asset.util.UniqueProductUtil;

@RestController
@RequestMapping("/api")
public class CacheController {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
	
	@Autowired
	private UniqueProductUtil uniqueProductUtil;
	
	@GetMapping("/cache/update-cache/name/{name}")
	public ResponseEntity<CfgCacheConfig> updateCache(@PathVariable String name) {
		logger.info("Request to get update cache for: "+name);
		if("product".equalsIgnoreCase(name)) {
			List<String> productList = uniqueProductUtil.getProducts();
			Constants.cache.put(Constants.PRODUCT_CACHE_KEY, productList);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	// maintain cache of sla-central data for each product
}
