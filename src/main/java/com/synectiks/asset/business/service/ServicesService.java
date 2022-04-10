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

import com.synectiks.asset.domain.Services;
import com.synectiks.asset.repository.ServicesRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServicesService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServicesService.class);
		
	@Autowired
	ServicesRepository servicesRepository;
	
	public Optional<Services> getServices(Long id) {
		logger.info("Get services by id: {}", id);
		return servicesRepository.findById(id);
	}
	
	public List<Services> getAllServices() {
		logger.info("Get all services");
		return servicesRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Services> deleteServices(Long id) {
		logger.info("Delete services by id: {}", id);
		Optional<Services> oObj = getServices(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. services cannot be deleted", id);
			return oObj;
		}
		servicesRepository.deleteById(id);
		return oObj;
	}
	
	public Services createServices(Services obj){
		logger.info("Create new services");
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return servicesRepository.save(obj);
	}
	
	public Services updateServices(Services obj){
		logger.info("Update services. Id: {}", obj.getId());
		if(!servicesRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Services", "idnotfound");
		}
		obj.setUpdatedOn(Instant.now());
		return servicesRepository.save(obj);
	}
	
	public Optional<Services> partialUpdateServices(Services obj){
		logger.info("Update services partialy. Id: {}", obj.getId());
		if(!servicesRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Services", "idnotfound");
		}
		
		Optional<Services> result = servicesRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getType())) {
					existingObj.setType(obj.getType());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(servicesRepository::save);
		return result;
	}
	
	public List<Services> searchAllServices(Map<String, String> obj) {
		logger.info("Search services");
		Services cld = new Services();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			cld.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			cld.setDescription(obj.get("description"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("type"))) {
			cld.setType(obj.get("type"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status"));
			isFilter = true;
		}
		
		List<Services> list = null;
		if(isFilter) {
			list = servicesRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = servicesRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
