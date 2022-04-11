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

import com.synectiks.asset.business.service.ProductBillingService;
import com.synectiks.asset.domain.ProductBilling;

@RestController
@RequestMapping("/api")
public class ProductBillingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductBillingController.class);
	
	@Autowired
	ProductBillingService productBillingService;
	
	@GetMapping("/product-billing/{id}")
	public ResponseEntity<ProductBilling> getProductBilling(@PathVariable Long id) {
		logger.info("Request to get product-billing. Id: "+id);
		Optional<ProductBilling> odp = productBillingService.getProductBilling(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/product-billing")
	public ResponseEntity<List<ProductBilling>> getAllProductBilling() {
		logger.info("Request to get product-billing");
		List<ProductBilling> list = productBillingService.getAllProductBilling();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/product-billing/{id}")
	public ResponseEntity<Optional<ProductBilling>> deleteProductBilling(@PathVariable Long id) {
		logger.info("Request to delete product-billing by id: {}", id);
		Optional<ProductBilling> oSpa = productBillingService.deleteProductBilling(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/product-billing")
	public ResponseEntity<ProductBilling> createProductBilling(@RequestBody ProductBilling obj){
		logger.info("Request to create new product-billing");
		ProductBilling spa = productBillingService.createProductBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/product-billing")
	public ResponseEntity<ProductBilling> updateProductBilling(@RequestBody ProductBilling obj){
		logger.info("Request to update product-billing");
		ProductBilling spa = productBillingService.updateProductBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/product-billing")
	public ResponseEntity<Optional<ProductBilling>> partialUpdateProductBilling(@RequestBody ProductBilling obj){
		logger.info("Request to partially update product-billing");
		Optional<ProductBilling> oSpa = productBillingService.partialUpdateProductBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/product-billing/search")
	public ResponseEntity<List<ProductBilling>> searchAllProductBilling(@RequestParam Map<String, String> obj){
		logger.info("Request to search product-billing");
		List<ProductBilling> list = productBillingService.searchAllProductBilling(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
