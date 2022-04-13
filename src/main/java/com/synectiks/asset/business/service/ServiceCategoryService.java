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

import com.synectiks.asset.domain.ServiceCategory;
import com.synectiks.asset.repository.ServiceCategoryRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceCategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceCategoryService.class);
		
	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	public Optional<ServiceCategory> getServiceCategory(Long id) {
		logger.info("Get service category by id: {}", id);
		return serviceCategoryRepository.findById(id);
	}
	
	public List<ServiceCategory> getAllServiceCategory() {
		logger.info("Get all service categories");
		return serviceCategoryRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceCategory> deleteServiceCategory(Long id) {
		logger.info("Delete service category by id: {}", id);
		Optional<ServiceCategory> oObj = getServiceCategory(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. service category cannot be deleted", id);
			return oObj;
		}
		serviceCategoryRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceCategory createServiceCategory(ServiceCategory obj){
		logger.info("Create new service category");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceCategoryRepository.save(obj);
	}
	
	public ServiceCategory updateServiceCategory(ServiceCategory obj){
		logger.info("Update service category. Id: {}", obj.getId());
		if(!serviceCategoryRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceCategory", "idnotfound");
		}
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return serviceCategoryRepository.save(obj);
	}
	
	public Optional<ServiceCategory> partialUpdateServiceCategory(ServiceCategory obj){
		logger.info("Update service category partialy. Id: {}", obj.getId());
		if(!serviceCategoryRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceCategory", "idnotfound");
		}
		
		Optional<ServiceCategory> result = serviceCategoryRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
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
			.map(serviceCategoryRepository::save);
		return result;
	}
	
	public List<ServiceCategory> searchAllServiceCategory(Map<String, String> obj) {
		logger.info("Search service category");
		ServiceCategory cld = new ServiceCategory();
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
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		List<ServiceCategory> list = null;
		if(isFilter) {
			list = serviceCategoryRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceCategoryRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
