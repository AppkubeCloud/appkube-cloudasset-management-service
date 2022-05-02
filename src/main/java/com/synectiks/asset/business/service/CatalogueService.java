package com.synectiks.asset.business.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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
		return catalogueRepository.save(obj);
	}
	
	public Catalogue updateCatalogue(Catalogue obj){
		logger.info("Update catalogue. Id: {}", obj.getId());
		if(!catalogueRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Catalogue", "idnotfound");
		}
		return catalogueRepository.save(obj);
	}
	
	public Optional<Catalogue> partialUpdateCatalogue(Catalogue obj){
		logger.info("Update catalogue partialy. Id: {}", obj.getId());
		if(!catalogueRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "Catalogue", "idnotfound");
		}
		
		Optional<Catalogue> result = catalogueRepository.findById(obj.getId())
			.map(existingObj ->{
				return existingObj;
			})
			.map(catalogueRepository::save);
		return result;
	}
	
	public Catalogue searchCatalogue(Map<String, String> obj) {
		logger.info("Search catalogue");
		Gson gson = new Gson(); 
		String json = gson.toJson(obj); 
		List<Catalogue> list = catalogueRepository.findCatalogue(json);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Catalogue> searchAllCatalogue(Map<String, String> obj) {
		logger.info("Search catalogue");
		Gson gson = new Gson(); 
		String json = gson.toJson(obj); 
		List<Catalogue> list = catalogueRepository.findCatalogue(json);
		return list;
	}
	
}
