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

import com.synectiks.asset.domain.Catalogue;
import com.synectiks.asset.repository.CatalogueRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class CatalogueService {
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogueService.class);
		
	@Autowired
	CatalogueRepository catalogueRepository;
	
	public Optional<Catalogue> getCatalogue(Long id) {
		logger.info("Get catalogue by id: {}", id);
		return catalogueRepository.findById(id);
	}
	
	public List<Catalogue> getAllCatalogue() {
		logger.info("Get all catalogue");
		return catalogueRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<Catalogue> deleteCatalogue(Long id) {
		logger.info("Delete catalogue by id: {}", id);
		Optional<Catalogue> oObj = getCatalogue(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Catalogue cannot be deleted", id);
			return oObj;
		}
		catalogueRepository.deleteById(id);
		return oObj;
	}
	
	public Catalogue createCatalogue(Catalogue obj){
		logger.info("Create new catalogue");
		obj.setUuid(com.synectiks.asset.util.Utils.getRandomStringUuid());
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return catalogueRepository.save(obj);
	}
	
	public Catalogue updateCatalogue(Catalogue obj){
		logger.info("Update catalogue. Id: {}", obj.getId());
		if(!catalogueRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Catalogue", "idnotfound");
		}
		obj.setUpdatedOn(Instant.now());
		return catalogueRepository.save(obj);
	}
	
	public Optional<Catalogue> partialUpdateCatalogue(Catalogue obj){
		logger.info("Update catalogue partialy. Id: {}", obj.getId());
		if(!catalogueRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Catalogue", "idnotfound");
		}
		
		Optional<Catalogue> result = catalogueRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				if(!StringUtils.isBlank(obj.getDashboardNature())) {
					existingObj.setDashboardNature(obj.getDashboardNature());
				}
				if(!StringUtils.isBlank(obj.getAwsBucket())) {
					existingObj.setAwsBucket(obj.getAwsBucket());
				}
				if(!StringUtils.isBlank(obj.getFileName())) {
					existingObj.setFileName(obj.getFileName());
				}
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus());
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(catalogueRepository::save);
		return result;
	}
	
	public List<Catalogue> searchAllCatalogue(Map<String, String> obj) {
		logger.info("Search catalogue");
		Catalogue cld = new Catalogue();
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
		if(!StringUtils.isBlank(obj.get("uuid"))) {
			cld.setUuid(obj.get("uuid"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("dashboardNature"))) {
			cld.setDashboardNature(obj.get("dashboardNature"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("awsBucket"))) {
			cld.setAwsBucket(obj.get("awsBucket"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("fileName"))) {
			cld.setFileName(obj.get("fileName"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status"));
			isFilter = true;
		}
		
		List<Catalogue> list = null;
		if(isFilter) {
			list = catalogueRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = catalogueRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
