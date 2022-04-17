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

import com.synectiks.asset.domain.ServiceCategory;
import com.synectiks.asset.domain.ServiceTag;
import com.synectiks.asset.repository.ServiceTagRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceTagService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTagService.class);
		
	@Autowired
	ServiceTagRepository serviceTagRepository;
	
	@Autowired
	ServiceCategoryService serviceCategoryService;
	
	public Optional<ServiceTag> getServiceTag(Long id) {
		logger.info("Get service-tag by id: {}", id);
		return serviceTagRepository.findById(id);
	}
	
	public List<ServiceTag> getAllServiceTag() {
		logger.info("Get all service-tag");
		return serviceTagRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceTag> deleteServiceTag(Long id) {
		logger.info("Delete service-tag by id: {}", id);
		Optional<ServiceTag> oObj = getServiceTag(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. service-tag cannot be deleted", id);
			return oObj;
		}
		serviceTagRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceTag createServiceTag(ServiceTag obj){
		logger.info("Create new service-tag");
		if(Objects.isNull(obj.getServiceCategory()) || (!Objects.isNull(obj.getServiceCategory()) && obj.getServiceCategory().getId() < 0)) {
			throw new BadRequestAlertException("Service category id not found", "ServiceTag", "idnotfound");
		}
		Optional<ServiceCategory> osc = serviceCategoryService.getServiceCategory(obj.getServiceCategory().getId());
		if(!osc.isPresent()) {
			throw new BadRequestAlertException("Invalid service category id", "ServiceTag", "idnotfound");
		}
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getTagName())) {
			obj.setTagName(obj.getTagName().toUpperCase());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceTagRepository.save(obj);
	}
	
	public ServiceTag updateServiceTag(ServiceTag obj){
		logger.info("Update service-tag. Id: {}", obj.getId());
		if(!serviceTagRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceTag", "idnotfound");
		}
		if(Objects.isNull(obj.getServiceCategory()) || (!Objects.isNull(obj.getServiceCategory()) && obj.getServiceCategory().getId() < 0)) {
			throw new BadRequestAlertException("Service category id not found", "ServiceTag", "idnotfound");
		}
		Optional<ServiceCategory> osc = serviceCategoryService.getServiceCategory(obj.getServiceCategory().getId());
		if(!osc.isPresent()) {
			throw new BadRequestAlertException("Invalid service category id", "ServiceTag", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getTagName())) {
			obj.setTagName(obj.getTagName().toUpperCase());
		}
		
		obj.setUpdatedOn(Instant.now());
		return serviceTagRepository.save(obj);
	}
	
	public Optional<ServiceTag> partialUpdateServiceTag(ServiceTag obj){
		logger.info("Update service-tag partialy. Id: {}", obj.getId());
		if(!serviceTagRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceTag", "idnotfound");
		}
		if(Objects.isNull(obj.getServiceCategory()) || (!Objects.isNull(obj.getServiceCategory()) && obj.getServiceCategory().getId() < 0)) {
			throw new BadRequestAlertException("Service category id not found", "ServiceTag", "idnotfound");
		}
		
		Optional<ServiceTag> result = serviceTagRepository.findById(obj.getId())
			.map(existingObj ->{
				Optional<ServiceCategory> osc = serviceCategoryService.getServiceCategory(obj.getServiceCategory().getId());
				if(!osc.isPresent()) {
					throw new BadRequestAlertException("Invalid service category id", "ServiceTag", "idnotfound");
				}
				if(!StringUtils.isBlank(obj.getTagName())) {
					existingObj.setTagName(obj.getTagName().toUpperCase());
				}
				
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				existingObj.setUpdatedOn(Instant.now());
				return existingObj;
			})
			.map(serviceTagRepository::save);
		return result;
	}
	
	public List<ServiceTag> searchAllServiceTag(Map<String, String> obj) {
		logger.info("Search service-tag");
		ServiceTag cld = new ServiceTag();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			cld.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("tagName"))) {
			cld.setTagName(obj.get("tagName"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("serviceCategoryId"))) {
			Optional<ServiceCategory> osc = serviceCategoryService.getServiceCategory(Long.parseLong(obj.get("serviceCategoryId")));
			if(osc.isPresent()) {
				cld.setServiceCategory(osc.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		List<ServiceTag> list = null;
		if(isFilter) {
			list = serviceTagRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceTagRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
