package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.ServiceProviderCloudAccount;
import com.synectiks.asset.repository.ServiceProviderCloudAccountRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceProviderCloudAccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceProviderCloudAccountService.class);
		
	@Autowired
	ServiceProviderCloudAccountRepository serviceProviderCloudAccountRepository;
	
	public Optional<ServiceProviderCloudAccount> getServiceProviderCloudAccount(Long id) {
		logger.info("Get service provider's cloud account by id: {}", id);
		return serviceProviderCloudAccountRepository.findById(id);
	}
	
	public List<ServiceProviderCloudAccount> getAllServiceProviderCloudAccount() {
		logger.info("Get all service provider's cloud accounts");
		return serviceProviderCloudAccountRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceProviderCloudAccount> deleteServiceProviderCloudAccount(Long id) {
		logger.info("Delete service provider's cloud account by id: {}", id);
		Optional<ServiceProviderCloudAccount> oSpa = getServiceProviderCloudAccount(id);
		if(!oSpa.isPresent()) {
			logger.warn("Id {} not found. Service provider's cloud account cannot be deleted", id);
			return oSpa;
		}
		serviceProviderCloudAccountRepository.deleteById(id);
		return oSpa;
	}
	
	public ServiceProviderCloudAccount createServiceProviderCloudAccount(ServiceProviderCloudAccount obj){
		logger.info("Create new service provider's cloud account");
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceProviderCloudAccountRepository.save(obj);
	}
	
	public ServiceProviderCloudAccount updateServiceProviderCloudAccount(ServiceProviderCloudAccount obj){
		logger.info("Update service provider's cloud account. Id: {}", obj.getId());
		if(!serviceProviderCloudAccountRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceProviderCloudAccount", "idnotfound");
		}
		obj.setUpdatedOn(Instant.now());
		return serviceProviderCloudAccountRepository.save(obj);
	}
	
	public Optional<ServiceProviderCloudAccount> partialUpdateServiceProviderCloudAccount(ServiceProviderCloudAccount obj){
		logger.info("Update service provider's cloud account partialy. Id: {}", obj.getId());
		if(!serviceProviderCloudAccountRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceProviderCloudAccount", "idnotfound");
		}
		
		Optional<ServiceProviderCloudAccount> result = serviceProviderCloudAccountRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getCloudType())) {
					existingObj.setCloudType(obj.getCloudType());
				}
				if(!StringUtils.isBlank(obj.getAccountId())) {
					existingObj.setAccountId(obj.getAccountId());
				}
				if(!StringUtils.isBlank(obj.getAccessKey())) {
					existingObj.setAccessKey(obj.getAccessKey());
				}
				if(!StringUtils.isBlank(obj.getSecretKey())) {
					existingObj.setSecretKey(obj.getSecretKey());
				}
				if(!StringUtils.isBlank(obj.getRegion())) {
					existingObj.setRegion(obj.getRegion());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(serviceProviderCloudAccountRepository::save);
		return result;
	}
	
	public List<ServiceProviderCloudAccount> searchAllServiceProviderCloudAccount(Map<String, String> obj) {
		logger.info("Search service provider's cloud accounts");
		ServiceProviderCloudAccount spa = new ServiceProviderCloudAccount();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			spa.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("cloudType"))) {
			spa.setCloudType(obj.get("cloudType"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("accountId"))) {
			spa.setAccountId(obj.get("accountId"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("accessKey"))) {
			spa.setAccessKey(obj.get("accessKey"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("secretKey"))) {
			spa.setSecretKey(obj.get("secretKey"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("region"))) {
			spa.setRegion(obj.get("region"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			spa.setStatus(obj.get("status"));
			isFilter = true;
		}
		
		List<ServiceProviderCloudAccount> list = null;
		if(isFilter) {
			list = serviceProviderCloudAccountRepository.findAll(Example.of(spa), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceProviderCloudAccountRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
