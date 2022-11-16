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

import com.synectiks.asset.domain.CfgCacheConfig;
import com.synectiks.asset.repository.CfgCacheConfigRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class CfgCacheConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(CfgCacheConfigService.class);
		
	@Autowired
	private CfgCacheConfigRepository cfgCacheConfigRepository;
	
	public Optional<CfgCacheConfig> getCfgCacheConfig(Long id) {
		logger.info("Get CfgCacheConfig by id: {}", id);
		Optional<CfgCacheConfig> oObj = cfgCacheConfigRepository.findById(id);
		return oObj;
	}
	
	public Optional<CfgCacheConfig> getCfgCacheConfigByName(String name) {
		logger.info("Get CfgCacheConfig by name: {}", name);
		CfgCacheConfig cfg = new CfgCacheConfig();
		cfg.setName(name);
		Optional<CfgCacheConfig> oObj = cfgCacheConfigRepository.findOne(Example.of(cfg));
		return oObj;
	}
	
	public List<CfgCacheConfig> getAllCfgCacheConfig() {
		logger.info("Get all CfgCacheConfig");
		List<CfgCacheConfig> listDp = cfgCacheConfigRepository.findAll(Sort.by(Direction.DESC, "id"));
		return listDp;
	}
	
	public Optional<CfgCacheConfig> deleteCfgCacheConfig(Long id) {
		logger.info("Delete CfgCacheConfig by id: {}", id);
		Optional<CfgCacheConfig> oDp = getCfgCacheConfig(id);
		if(!oDp.isPresent()) {
			logger.warn("Id {} not found. CfgCacheConfig cannot be deleted", id);
			return oDp;
		}
		cfgCacheConfigRepository.deleteById(id);
		return oDp;
	}
	
	public CfgCacheConfig createCfgCacheConfig(CfgCacheConfig obj){
		logger.info("Create new CfgCacheConfig");
		return cfgCacheConfigRepository.save(obj);
	}
	
	public CfgCacheConfig updateCfgCacheConfig(CfgCacheConfig obj){
		logger.info("Update CfgCacheConfig. Id: {}", obj.getId());
		if(!cfgCacheConfigRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "CfgCacheConfig", "idnotfound");
		}
		return cfgCacheConfigRepository.save(obj);
	}
	
	public Optional<CfgCacheConfig> partialUpdateCfgCacheConfig(CfgCacheConfig obj){
		logger.info("Update CfgCacheConfig partialy. Id: {}", obj.getId());
		if(!cfgCacheConfigRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "CfgCacheConfig", "idnotfound");
		}
		Optional<CfgCacheConfig> result = cfgCacheConfigRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(obj.isDirtyFlag() != null) {
					existingObj.setDirtyFlag(obj.isDirtyFlag());
				}
				return existingObj;
			})
			.map(cfgCacheConfigRepository::save);
		CfgCacheConfig dp = result.get();
		return Optional.of(dp);
	}
	
	public List<CfgCacheConfig> searchAllCfgCacheConfig(Map<String, String> obj) {
		logger.info("Search CfgCacheConfig");
		CfgCacheConfig dp = new CfgCacheConfig();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			dp.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			dp.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("isDirtyFlag"))) {
			dp.setDirtyFlag(Boolean.getBoolean(obj.get("isDirtyFlag")));
			isFilter = true;
		}
		
		List<CfgCacheConfig> list = null;
		if(isFilter) {
			list = cfgCacheConfigRepository.findAll(Example.of(dp), Sort.by(Direction.DESC, "id"));
		}else {
			list = cfgCacheConfigRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		
		return list;
	}
	

	
}
