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

import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.domain.Product;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		logger.info("Request to get product. Id: "+id);
		Optional<Product> odp = productService.getProduct(id);
		if(odp.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(odp.get());
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProduct() {
		logger.info("Request to get product");
		List<Product> list = productService.getAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Optional<Product>> deleteProduct(@PathVariable Long id) {
		logger.info("Request to delete product by id: {}", id);
		Optional<Product> oSpa = productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product obj){
		logger.info("Request to create new product");
		Product spa = productService.createProduct(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PutMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product obj){
		logger.info("Request to update product");
		Product spa = productService.updateProduct(obj);
		return ResponseEntity.status(HttpStatus.OK).body(spa);
	}
	
	@PatchMapping("/product")
	public ResponseEntity<Optional<Product>> partialUpdateProduct(@RequestBody Product obj){
		logger.info("Request to partially update product");
		Optional<Product> oSpa = productService.partialUpdateProduct(obj);
		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
	}
	
	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> searchAllProduct(@RequestParam Map<String, String> obj){
		logger.info("Request to search product");
		List<Product> list = productService.searchAllProduct(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
