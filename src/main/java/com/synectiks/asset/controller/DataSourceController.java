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

import com.synectiks.asset.business.service.DataSourceService;
import com.synectiks.asset.domain.DataSource;

@RestController
@RequestMapping("/api")
public class DataSourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);
	
	@Autowired
	DataSourceService dataSourceService;
	
	@GetMapping("/data-source/{id}")
	public ResponseEntity<DataSource> getDataSource(@PathVariable Long id) {
		logger.info("Request to get data-source. Id: "+id);
		Optional<DataSource> odp = dataSourceService.getDataSource(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/data-source")
	public ResponseEntity<List<DataSource>> getAllDataSource() {
		logger.info("Request to get data-source");
		List<DataSource> list = dataSourceService.getAllDataSource();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/data-source/{id}")
	public ResponseEntity<Optional<DataSource>> deleteDataSource(@PathVariable Long id) {
		logger.info("Request to delete data-source by id: {}", id);
		Optional<DataSource> oSpa = dataSourceService.deleteDataSource(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/data-source")
	public ResponseEntity<DataSource> createDataSource(@RequestBody DataSource obj){
		logger.info("Request to create new data-source");
		DataSource spa = dataSourceService.createDataSource(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/data-source")
	public ResponseEntity<DataSource> updateDataSource(@RequestBody DataSource obj){
		logger.info("Request to update data-source");
		DataSource spa = dataSourceService.updateDataSource(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/data-source")
	public ResponseEntity<Optional<DataSource>> partialUpdateDataSource(@RequestBody DataSource obj){
		logger.info("Request to partially update data-source");
		Optional<DataSource> oSpa = dataSourceService.partialUpdateDataSource(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/data-source/search")
	public ResponseEntity<List<DataSource>> searchAllDataSource(@RequestParam Map<String, String> obj){
		logger.info("Request to search data-source");
		List<DataSource> list = dataSourceService.searchAllDataSource(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
