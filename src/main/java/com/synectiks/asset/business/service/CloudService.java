package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.Cloud;
import com.synectiks.asset.repository.CloudRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class CloudService {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudService.class);
		
	@Autowired
	CloudRepository cloudRepository;
	
	public Optional<Cloud> getCloud(Long id) {
		logger.info("Get cloud by id: {}", id);
		return cloudRepository.findById(id);
	}
	
	public List<Cloud> getAllCloud() {
		logger.info("Get all cloud");
		return cloudRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Cloud> deleteCloud(Long id) {
		logger.info("Delete cloud by id: {}", id);
		Optional<Cloud> oObj = getCloud(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. cloud cannot be deleted", id);
			return oObj;
		}
		cloudRepository.deleteById(id);
		return oObj;
	}
	
	public Cloud createCloud(Cloud obj){
		logger.info("Create new cloud");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getName())) {
			obj.setName(obj.getName().toUpperCase());
		}
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return cloudRepository.save(obj);
	}
	
	public Cloud updateCloud(Cloud obj){
		logger.info("Update cloud. Id: {}", obj.getId());
		if(!cloudRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Cloud", "idnotfound");
		}
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getName())) {
			obj.setName(obj.getName().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return cloudRepository.save(obj);
	}
	
	public Optional<Cloud> partialUpdateCloud(Cloud obj){
		logger.info("Update cloud partialy. Id: {}", obj.getId());
		if(!cloudRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Cloud", "idnotfound");
		}
		
		Optional<Cloud> result = cloudRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName().toUpperCase());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(cloudRepository::save);
		return result;
	}
	
	public List<Cloud> searchAllCloud(Map<String, String> obj) {
		logger.info("Search cloud");
		
		List<Cloud> list = cloudRepository.findAll();
		if (obj.size() == 0) {
			return list;
		}
		List<Cloud> list2 = list;
		
		if (obj.containsKey("id")) {
			list2 = list2.stream().filter(sd -> sd.getId().longValue() == Long.parseLong(obj.get("id")))
					.collect(Collectors.toList());
		}
		if (obj.containsKey("name")) {
			list2 = list2.stream().filter(sd -> sd.getName().equals(obj.get("name").toUpperCase()))
					.collect(Collectors.toList());
		}
		if (obj.containsKey("status")) {
			list2 = list2.stream().filter(sd -> sd.getStatus().equalsIgnoreCase(obj.get("status").toUpperCase()))
					.collect(Collectors.toList());
		}

		return list2;
	}
	

	
}
