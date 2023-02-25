package com.synectiks.asset.business.service;

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

import com.synectiks.asset.domain.Vault;
import com.synectiks.asset.repository.VaultRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class VaultService {
	
	private static final Logger logger = LoggerFactory.getLogger(VaultService.class);
		
	@Autowired
	private VaultRepository valultRepository;
	
	public Optional<Vault> getVault(Long id) {
		logger.info("Get vault by id: {}", id);
		return valultRepository.findById(id);
	}
	
	public List<Vault> getAllVault() {
		logger.info("Get all vaults");
		return valultRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Vault> deleteVault(Long id) {
		logger.info("Delete vault by id: {}", id);
		Optional<Vault> oObj = getVault(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Vault cannot be deleted", id);
			return Optional.empty();
		}
		valultRepository.deleteById(id);
		return oObj;
	}
	
	public Vault createVault(Vault obj){
		logger.info("Create new vault");
		return valultRepository.save(obj);
	}
	
	public Vault updateVault(Vault obj){
		logger.info("Update Vault. Id: {}", obj.getId());
		if(!valultRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Vault", "idnotfound");
		}
		return valultRepository.save(obj);
	}
	
	public Optional<Vault> partialUpdateVault(Vault obj){
		logger.info("Update Vault partialy. Id: {}", obj.getId());
		if(!valultRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Vault", "idnotfound");
		}
		
		Optional<Vault> result = valultRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getCloudType())) {
					existingObj.setCloudType(obj.getCloudType());
				}
				if(!StringUtils.isBlank(obj.getAccountId())) {
					existingObj.setAccountId(obj.getAccountId());
				}
				if(obj.getAccessDetails() != null) {
					existingObj.setAccessDetails(obj.getAccessDetails());
				}
				return existingObj;
			})
			.map(valultRepository::save);
		if(result.isPresent()) {
			return Optional.of(result.get());
		}
		return Optional.empty();
	}
	
	public List<Vault> searchAllVault(Map<String, String> obj) {
		logger.info("Search vault");
		Vault cld = new Vault();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("cloudType"))) {
			cld.setCloudType(obj.get("cloudType"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("accountId"))) {
			cld.setAccountId(obj.get("accountId"));
			isFilter = true;
		}
		
		List<Vault> list = null;
		if(isFilter) {
			list = valultRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = valultRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		
		return list;
	}
	
	
}
