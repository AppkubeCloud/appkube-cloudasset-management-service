package com.synectiks.asset.controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.ProductServiceDetailService;
import com.synectiks.asset.domain.ProductServiceDetail;

@RestController
@RequestMapping("/api")
public class ProductServiceDetailController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceDetailController.class);
	
	@Autowired
	ProductServiceDetailService productServiceDetailService;
	
	@GetMapping("/product-service-detail/{id}")
	public ResponseEntity<ProductServiceDetail> getProductServiceDetail(@PathVariable Long id) {
		logger.info("Request to get product service-detail. Id: "+id);
		Optional<ProductServiceDetail> odp = productServiceDetailService.getProductServiceDetail(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/product-service-detail")
	public ResponseEntity<List<ProductServiceDetail>> getAllProductServiceDetail() {
		logger.info("Request to get product service-detail");
		List<ProductServiceDetail> list = productServiceDetailService.getAllProductServiceDetail();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/product-service-detail/{id}")
	public ResponseEntity<Optional<ProductServiceDetail>> deleteProductServiceDetail(@PathVariable Long id) {
		logger.info("Request to delete product-service-detail by id: {}", id);
		Optional<ProductServiceDetail> oSpa = productServiceDetailService.deleteProductServiceDetail(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/product-service-detail")
	public ResponseEntity<ProductServiceDetail> createProductServiceDetail(@RequestBody ProductServiceDetail obj){
		logger.info("Request to create new product-service-detail");
		ProductServiceDetail spa = productServiceDetailService.createProductServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/product-service-detail")
	public ResponseEntity<ProductServiceDetail> updateProductServiceDetail(@RequestBody ProductServiceDetail obj){
		logger.info("Request to update product-service-detail");
		ProductServiceDetail spa = productServiceDetailService.updateProductServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/product-service-detail")
	public ResponseEntity<Optional<ProductServiceDetail>> partialUpdateProductServiceDetail(@RequestBody ProductServiceDetail obj){
		logger.info("Request to partially update product-service-detail");
		Optional<ProductServiceDetail> oSpa = productServiceDetailService.partialUpdateProductServiceDetail(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
//	@GetMapping("/product-service-detail/search")
//	public ResponseEntity<ProductServiceDetailReportResponse> searchAllProductServiceDetail(@RequestParam Map<String, String> obj){
//		logger.info("Request to search product-service-detail");
//		ProductServiceDetailReportResponse sdr = productServiceDetailService.searchAllProductServiceDetail(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(sdr);
//	}
	
//	@PostMapping("/product-service-detail/create-bulk-data")
//	public ResponseEntity<Void> createBulkData(@RequestBody ObjectNode objNode) throws IOException {
//		JsonNode objArray = objNode.get("masterServices");
//		for(JsonNode node: objArray) {
//			productServiceDetailService.createBulkData(node);
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(null);
//	}
	
//	@GetMapping("/service-detail/search-with-filter")
//	public ResponseEntity<ServiceDetailReportResponse> searchServiceDetailWithFilter(@RequestParam Map<String, String> obj){
//		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(sdr);
//	}
	
//	public Map<String, String> convertKeyValuePairToMap(String keyValuePairs) {
//		String dt[] = keyValuePairs.split("&");
//		Map<String, String> obj = new HashMap<>();
//		for(String pair: dt) {
//			String tmp [] = pair.split("=");
//			obj.put(tmp[0], tmp[1]);
//		}
//		return obj;
//	}
}
