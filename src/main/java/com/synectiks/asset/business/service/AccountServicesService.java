package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.synectiks.asset.domain.AccountServices;
import com.synectiks.asset.repository.AccountServicesRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class AccountServicesService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountServicesService.class);
		
	@Autowired
	AccountServicesRepository accountServicesRepository;
	
	public Optional<AccountServices> getAccountServices(Long id) {
		logger.info("Get account services by id: {}", id);
		return accountServicesRepository.findById(id);
	}
	
	public List<AccountServices> getAllAccountServices() {
		logger.info("Get all account services");
		return accountServicesRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<AccountServices> deleteAccountServices(Long id) {
		logger.info("Delete account services by id: {}", id);
		Optional<AccountServices> oObj = getAccountServices(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Account services cannot be deleted", id);
			return oObj;
		}
		accountServicesRepository.deleteById(id);
		return oObj;
	}
	
	public AccountServices createAccountServices(AccountServices obj){
		logger.info("Create new account services");
		return accountServicesRepository.save(obj);
	}
	
	public AccountServices updateAccountServices(AccountServices obj){
		logger.info("Update account services. Id: {}", obj.getId());
		if(!accountServicesRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "AccountServices", "idnotfound");
		}
		return accountServicesRepository.save(obj);
	}
	
	public Optional<AccountServices> partialUpdateAccountServices(AccountServices obj){
		logger.info("Update account services partialy. Id: {}", obj.getId());
		if(!accountServicesRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "AccountServices", "idnotfound");
		}
		Optional<AccountServices> result = accountServicesRepository.findById(obj.getId())
			.map(existingObj ->{
				return existingObj;
			})
			.map(accountServicesRepository::save);
		return result;
	}
	
	public void createBulkData(JsonNode node) throws IOException {
		AccountServices psd = AccountServices.builder()
				.accountId(node.get("accountId").asText())
				.build();
//		MasterServiceDetailsResponse msdr = MasterServiceDetailsResponse.from(node);
//		psd.setMaster_services_details(msdr);
		createAccountServices(psd);
	}
	

	public List<AccountServices> searchAccountServices(Map<String, String> obj) {
		logger.info("Search account services ");
		
		if(obj.size() == 0 ) {
			return accountServicesRepository.findAll();
		}
		if(obj.containsKey("id")) {
			Optional<AccountServices> oas = getAccountServices(Long.parseLong(obj.get("id")));
			if(oas.isPresent()) {
				List<AccountServices> list = new ArrayList<>();
				list.add(oas.get());
				return list;
			}
		}
		if(obj.containsKey("accountId")) {
			AccountServices as = AccountServices.builder().accountId(obj.get("accountId")).build();
			Example<AccountServices> example = Example.of(as);
			return accountServicesRepository.findAll(example);
		}
		return null;
	}

}
