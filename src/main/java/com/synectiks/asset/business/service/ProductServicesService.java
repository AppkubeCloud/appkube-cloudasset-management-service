package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.Services;
import com.synectiks.asset.repository.ProductServiceRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ProductServicesService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServicesService.class);
		
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductServiceRepository productServiceRepository;
	
	@Autowired
	ServicesService servicesService;
	
	public Product attachService(Long productId, Long serviceId){
		logger.info("Attaching service {} to the product {}", serviceId, productId);
		Optional<Product> op = productService.getProduct(productId);
		Optional<Services> ose = servicesService.getServices(serviceId);
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!ose.isPresent()) {
			throw new BadRequestAlertException("Service not found", "Service", "idnotfound");
		}
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(op.get());
		ps.setServices(ose.get());
		ps.setStatus(Constants.ACTIVE);
		Instant instant = Instant.now();
		ps.setDescription(ose.get().getName() + " service is associated with product "+op.get().getName());
		ps.setCreatedOn(instant);
		ps.setUpdatedOn(instant);
		ps = productServiceRepository.save(ps);
		Product product = ps.getProduct();
		product.setServiceList(getAllServicesOfProduct(product));
		return product;
	}
	
	public List<Services> getAllServicesOfProduct(Product product){
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(product);
		List<com.synectiks.asset.domain.ProductService> list = productServiceRepository.findAll(Example.of(ps), Sort.by(Direction.DESC, "id"));
		List<Services> servicesList = new ArrayList<>();
		for(com.synectiks.asset.domain.ProductService obj: list) {
			servicesList.add(obj.getServices());
		}
		return servicesList;
	}
}
