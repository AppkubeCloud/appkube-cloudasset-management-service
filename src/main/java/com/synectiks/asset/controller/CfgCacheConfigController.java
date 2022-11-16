package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.synectiks.asset.business.service.CfgCacheConfigService;
import com.synectiks.asset.domain.CfgCacheConfig;

@RestController
@RequestMapping("/api")
public class CfgCacheConfigController {
	
	private static final Logger logger = LoggerFactory.getLogger(CfgCacheConfigController.class);
	
	@Autowired
	private CfgCacheConfigService cfgCacheConfigService;
	
	@GetMapping("/cfg-cache-config/{id}")
	public ResponseEntity<CfgCacheConfig> getCfgCacheConfig(@PathVariable Long id) {
		logger.info("Request to get CfgCacheConfig. Id: "+id);
		Optional<CfgCacheConfig> odp = cfgCacheConfigService.getCfgCacheConfig(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/cfg-cache-config/name/{name}")
	public ResponseEntity<CfgCacheConfig> getCfgCacheConfigByName(@PathVariable String name) {
		logger.info("Request to get CfgCacheConfig. name: "+name);
		Optional<CfgCacheConfig> odp = cfgCacheConfigService.getCfgCacheConfigByName(name);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/cfg-cache-config")
	public ResponseEntity<List<CfgCacheConfig>> getAllCfgCacheConfig() {
		logger.info("Request to get CfgCacheConfig");
		List<CfgCacheConfig> list = cfgCacheConfigService.getAllCfgCacheConfig();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/cfg-cache-config/{id}")
	public ResponseEntity<Optional<CfgCacheConfig>> deleteCfgCacheConfig(@PathVariable Long id) {
		logger.info("Request to delete CfgCacheConfig by id: {}", id);
		Optional<CfgCacheConfig> oSpa = cfgCacheConfigService.deleteCfgCacheConfig(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/cfg-cache-config")
	public ResponseEntity<CfgCacheConfig> createCfgCacheConfig(@RequestBody CfgCacheConfig obj){
		logger.info("Request to create new CfgCacheConfig");
		CfgCacheConfig spa = cfgCacheConfigService.createCfgCacheConfig(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/cfg-cache-config")
	public ResponseEntity<CfgCacheConfig> updateCfgCacheConfig(@RequestBody CfgCacheConfig obj){
		logger.info("Request to update CfgCacheConfig");
		CfgCacheConfig spa = cfgCacheConfigService.updateCfgCacheConfig(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/cfg-cache-config")
	public ResponseEntity<Optional<CfgCacheConfig>> partialUpdateCfgCacheConfig(@RequestBody CfgCacheConfig obj){
		logger.info("Request to partially update CfgCacheConfig");
		Optional<CfgCacheConfig> oSpa = cfgCacheConfigService.partialUpdateCfgCacheConfig(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/cfg-cache-config/search")
	public ResponseEntity<List<CfgCacheConfig>> searchAllCfgCacheConfig(@RequestParam Map<String, String> obj){
		logger.info("Request to search CfgCacheConfig");
		List<CfgCacheConfig> list = cfgCacheConfigService.searchAllCfgCacheConfig(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
