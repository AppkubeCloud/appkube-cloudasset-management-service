package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.DeploymentEnvironment;
import com.synectiks.asset.domain.Product;
import com.synectiks.asset.domain.ServiceBilling;
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
	
	@Autowired
	ServiceBillingService serviceBillingService;
	
	@Autowired
	DeploymentEnvironmentService deploymentEnvironmentService;
	
	public Optional<com.synectiks.asset.domain.ProductService> getProductService(Long id) {
		logger.info("Get roduct service by id: {}", id);
		return productServiceRepository.findById(id);
	}
	
	public Product atachService(Long productId, Long serviceId, Long depEnvId){
		logger.info("Ataching service {} to the product {}", serviceId, productId);
		Optional<Product> op = productService.getProduct(productId);
		Optional<Services> ose = servicesService.getServices(serviceId);
		Optional<DeploymentEnvironment> oDe = deploymentEnvironmentService.getDeploymentEnvironment(depEnvId);
		
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!ose.isPresent()) {
			throw new BadRequestAlertException("Service not found", "Service", "idnotfound");
		}
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(op.get());
		ps.setServices(ose.get());
		if(oDe.isPresent()) {
			ps.setDeploymentEnvironment(oDe.get());
		}
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
	
	public boolean detachService(Long productId, Long serviceId, Long depEnvId){
		logger.info("Detaching service {} to the product {}", serviceId, productId);
		Optional<Product> op = productService.getProduct(productId);
		Optional<Services> ose = servicesService.getServices(serviceId);
		Optional<DeploymentEnvironment> oDe = deploymentEnvironmentService.getDeploymentEnvironment(depEnvId);
		if(!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", "Product", "idnotfound");
		}
		if(!ose.isPresent()) {
			throw new BadRequestAlertException("Service not found", "Service", "idnotfound");
		}
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(op.get());
		ps.setServices(ose.get());
		if(oDe.isPresent()) {
			ps.setDeploymentEnvironment(oDe.get());
		}
		Optional<com.synectiks.asset.domain.ProductService> odp = productServiceRepository.findOne(Example.of(ps));
		if(odp.isPresent()) {
			productServiceRepository.deleteById(odp.get().getId());
			logger.info("Service deleted from product successfully");
			return true;
		}
		logger.warn("Service deletion from product failed");
		return false;
	}
	
	public List<Services> getAllServicesOfProduct(Product product){
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(product);
		List<com.synectiks.asset.domain.ProductService> list = productServiceRepository.findAll(Example.of(ps), Sort.by(Direction.DESC, "id"));
		List<Services> servicesList = new ArrayList<>();
		Map<String, String> searchMap = new HashMap<>();
		for(com.synectiks.asset.domain.ProductService obj: list) {
			searchMap.clear();
			Services services = obj.getServices();
			searchMap.put("productId", String.valueOf(product.getId()));
			searchMap.put("servicesId", String.valueOf(services.getId()));
			searchMap.put("status", Constants.STATUS_UNPAID);
			List<ServiceBilling> srvBillList = serviceBillingService.searchAllServiceBilling(searchMap);
			Double total = 0D;
			for(ServiceBilling sb: srvBillList) {
				total  = total + sb.getAmount();
			}
			services.setServiceBillingList(serviceBillingService.searchAllServiceBilling(searchMap));
			services.setTotalBillingAmount(total);
			servicesList.add(services);
		}
		return servicesList;
	}
	
	public List<com.synectiks.asset.domain.ProductService> getProductServicesList(Product product, DeploymentEnvironment depEnv){
		com.synectiks.asset.domain.ProductService ps = new com.synectiks.asset.domain.ProductService();
		ps.setProduct(product);
		ps.setDeploymentEnvironment(depEnv);
		return productServiceRepository.findAll(Example.of(ps), Sort.by(Direction.DESC, "id"));
	}
	
	public List<Services> getServicesList(Product product, DeploymentEnvironment depEnv){
		List<com.synectiks.asset.domain.ProductService> psList = getProductServicesList(product, depEnv);
		List<Services> servicesList = new ArrayList<>();
		for(com.synectiks.asset.domain.ProductService obj: psList) {
			servicesList.add(obj.getServices());
		}
		return servicesList;
	}
	
	public List<Services> getAllServices(Long productId, Long depEnvId) {
		Optional<Product> opd = productService.getProduct(productId);
		if(!opd.isPresent()) {
			return Collections.emptyList();
		}
		Optional<DeploymentEnvironment> ode = deploymentEnvironmentService.getDeploymentEnvironment(depEnvId);
		if(!ode.isPresent()) {
			return Collections.emptyList();
		}
		return getServicesList(opd.get(), ode.get());
	}
}
