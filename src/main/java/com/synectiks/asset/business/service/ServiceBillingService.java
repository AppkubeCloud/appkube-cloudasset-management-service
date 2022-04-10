package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.ServiceBilling;
import com.synectiks.asset.repository.ServiceBillingRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceBillingService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceBillingService.class);
		
	@Autowired
	ServiceBillingRepository serviceBillingRepository;
	
	@Autowired
	ProductServicesService productServicesService;
	
//	@Autowired
//	ServicesService servicesService;
	
	public Optional<ServiceBilling> getServiceBilling(Long id) {
		logger.info("Get service billing by id: {}", id);
		return serviceBillingRepository.findById(id);
	}
	
	public List<ServiceBilling> getAllServiceBilling() {
		logger.info("Get all service billing");
		return serviceBillingRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceBilling> deleteServiceBilling(Long id) {
		logger.info("Delete service billing by id: {}", id);
		Optional<ServiceBilling> oObj = getServiceBilling(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. service billing cannot be deleted", id);
			return oObj;
		}
		serviceBillingRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceBilling createServiceBilling(ServiceBilling obj){
		logger.info("Create new service billing");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(Objects.isNull(obj.getProductService()) || (!Objects.isNull(obj.getProductService()) && obj.getProductService().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product-service id", "ProductService", "idnotfound");
		}
		
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceBillingRepository.save(obj);
	}
	
	public ServiceBilling updateServiceBilling(ServiceBilling obj){
		logger.info("Update service billing. Id: {}", obj.getId());
		if(!serviceBillingRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getProductService()) || (!Objects.isNull(obj.getProductService()) && obj.getProductService().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product-service id", "ProductService", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return serviceBillingRepository.save(obj);
	}
	
	public Optional<ServiceBilling> partialUpdateServiceBilling(ServiceBilling obj){
		logger.info("Update service billing partialy. Id: {}", obj.getId());
		if(!serviceBillingRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceBilling", "idnotfound");
		}
		if(Objects.isNull(obj.getProductService()) || (!Objects.isNull(obj.getProductService()) && obj.getProductService().getId() < 0)) {
			throw new BadRequestAlertException("Invalid product-service id", "ProductService", "idnotfound");
		}
		
		Optional<ServiceBilling> result = serviceBillingRepository.findById(obj.getId())
			.map(existingObj ->{
				if(obj.getAmount() != null) {
					existingObj.setAmount(obj.getAmount());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(serviceBillingRepository::save);
		return result;
	}
	
	public List<ServiceBilling> searchAllServiceBilling(Map<String, String> obj) {
		logger.info("Search service billing");
		ServiceBilling cld = new ServiceBilling();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			cld.setDescription(obj.get("description"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("productServiceId"))) {
			Optional<com.synectiks.asset.domain.ProductService> opd = productServicesService.getProductService(Long.parseLong(obj.get("productServiceId")));
			if(opd.isPresent()) {
				cld.setProductService(opd.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		List<ServiceBilling> list = null;
		if(isFilter) {
			list = serviceBillingRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceBillingRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	
}
