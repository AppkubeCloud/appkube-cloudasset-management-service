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
import com.synectiks.asset.domain.ServiceTagLink;
import com.synectiks.asset.domain.Services;
import com.synectiks.asset.repository.ServiceTagLinkRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceTagLinkService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceTagLinkService.class);
		
	@Autowired
	ServiceTagLinkRepository serviceTagLinkRepository;
	
	@Autowired
	ServicesService servicesService;
	
	@Autowired
	ServiceTagService serviceTagService;
	
	
	public Optional<ServiceTagLink> getServiceTagLink(Long id) {
		logger.info("Get service-tag-link by id: {}", id);
		return serviceTagLinkRepository.findById(id);
	}
	
	public List<ServiceTagLink> getAllServiceTagLink() {
		logger.info("Get all service-tag-link");
		return serviceTagLinkRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceTagLink> deleteServiceTagLink(Long id) {
		logger.info("Delete service-tag-link by id: {}", id);
		Optional<ServiceTagLink> oObj = getServiceTagLink(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. service-tag-link cannot be deleted", id);
			return oObj;
		}
		serviceTagLinkRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceTagLink createServiceTagLink(ServiceTagLink obj){
		logger.info("Create new service-tag-link");
		
		if(Objects.isNull(obj.getServices()) || (!Objects.isNull(obj.getServices()) && obj.getServices().getId() < 0)) {
			throw new BadRequestAlertException("Service id not found", "ServiceTagLink", "idnotfound");
		}
		Optional<Services> osc = servicesService.getServices(obj.getServices().getId());
		if(!osc.isPresent()) {
			throw new BadRequestAlertException("Invalid service id", "ServiceTagLink", "idnotfound");
		}
		
		if(Objects.isNull(obj.getServiceTag()) || (!Objects.isNull(obj.getServiceTag()) && obj.getServiceTag().getId() < 0)) {
			throw new BadRequestAlertException("ServiceTag id not found", "ServiceTagLink", "idnotfound");
		}
		Optional<ServiceTag> ost = serviceTagService.getServiceTag(obj.getServiceTag().getId());
		if(!osc.isPresent()) {
			throw new BadRequestAlertException("Invalid service-tag id", "ServiceTagLink", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getDescription())) {
			obj.setDescription(obj.getDescription());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceTagLinkRepository.save(obj);
	}
	
	public ServiceTagLink updateServiceTagLink(ServiceTagLink obj){
		logger.info("Update service-tag-link. Id: {}", obj.getId());
		if(!serviceTagLinkRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceTagLink", "idnotfound");
		}
		if(Objects.isNull(obj.getServices()) || (!Objects.isNull(obj.getServices()) && obj.getServices().getId() < 0)) {
			throw new BadRequestAlertException("Service id not found", "ServiceTagLink", "idnotfound");
		}
		Optional<Services> osc = servicesService.getServices(obj.getServices().getId());
		if(!osc.isPresent()) {
			throw new BadRequestAlertException("Invalid service id", "ServiceTagLink", "idnotfound");
		}
		
		if(Objects.isNull(obj.getServiceTag()) || (!Objects.isNull(obj.getServiceTag()) && obj.getServiceTag().getId() < 0)) {
			throw new BadRequestAlertException("ServiceTag id not found", "ServiceTagLink", "idnotfound");
		}
		Optional<ServiceTag> ost = serviceTagService.getServiceTag(obj.getServiceTag().getId());
		if(!ost.isPresent()) {
			throw new BadRequestAlertException("Invalid service-tag id", "ServiceTagLink", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		
		obj.setUpdatedOn(Instant.now());
		return serviceTagLinkRepository.save(obj);
	}
	
	public Optional<ServiceTagLink> partialUpdateServiceTagLink(ServiceTagLink obj){
		logger.info("Update service-tag-link partialy. Id: {}", obj.getId());
		if(!serviceTagLinkRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceTagLink", "idnotfound");
		}
		
		Optional<ServiceTagLink> result = serviceTagLinkRepository.findById(obj.getId())
			.map(existingObj ->{
				if(Objects.isNull(obj.getServices()) || (!Objects.isNull(obj.getServices()) && obj.getServices().getId() < 0)) {
					throw new BadRequestAlertException("Service id not found", "ServiceTagLink", "idnotfound");
				}
				Optional<Services> osc = servicesService.getServices(obj.getServices().getId());
				if(!osc.isPresent()) {
					throw new BadRequestAlertException("Invalid service id", "ServiceTagLink", "idnotfound");
				}
				existingObj.setServices(osc.get());
				
				if(Objects.isNull(obj.getServiceTag()) || (!Objects.isNull(obj.getServiceTag()) && obj.getServiceTag().getId() < 0)) {
					throw new BadRequestAlertException("ServiceTag id not found", "ServiceTagLink", "idnotfound");
				}
				Optional<ServiceTag> ost = serviceTagService.getServiceTag(obj.getServiceTag().getId());
				if(!ost.isPresent()) {
					throw new BadRequestAlertException("Invalid service-tag id", "ServiceTagLink", "idnotfound");
				}
				existingObj.setServiceTag(ost.get());
				
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				
				if(!StringUtils.isBlank(obj.getHostingType())) {
					existingObj.setHostingType(obj.getHostingType());
				}
				
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				
				existingObj.setUpdatedOn(Instant.now());
				return existingObj;
			})
			.map(serviceTagLinkRepository::save);
		return result;
	}
	
	public List<ServiceTagLink> searchAllServiceTagLink(Map<String, String> obj) {
		logger.info("Search service-tag-link");
		ServiceTagLink cld = new ServiceTagLink();
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
		
		if(!StringUtils.isBlank(obj.get("hostingType"))) {
			cld.setHostingType(obj.get("hostingType"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			cld.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("servicesId"))) {
			Optional<Services> osc = servicesService.getServices(Long.parseLong(obj.get("servicesId")));
			if(osc.isPresent()) {
				cld.setServices(osc.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		if(!StringUtils.isBlank(obj.get("serviceTagId"))) {
			Optional<ServiceTag> ost = serviceTagService.getServiceTag(Long.parseLong(obj.get("serviceTagId")));
			if(ost.isPresent()) {
				cld.setServiceTag(ost.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		List<ServiceTagLink> list = null;
		if(isFilter) {
			list = serviceTagLinkRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceTagLinkRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
