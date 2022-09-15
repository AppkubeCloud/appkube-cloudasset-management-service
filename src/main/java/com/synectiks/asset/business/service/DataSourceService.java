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

import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.DataSource;
import com.synectiks.asset.repository.DataSourceRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class DataSourceService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceService.class);
		
	@Autowired
	private DataSourceRepository dataSourceRepository;
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	public Optional<DataSource> getDataSource(Long id) {
		logger.info("Get data source by id: {}", id);
		return dataSourceRepository.findById(id);
	}
	
	public List<DataSource> getAllDataSource() {
		logger.info("Get all data sources");
		return dataSourceRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<DataSource> deleteDataSource(Long id) {
		logger.info("Delete data source by id: {}", id);
		Optional<DataSource> oDp = getDataSource(id);
		if(!oDp.isPresent()) {
			logger.warn("Id {} not found. data source cannot be deleted", id);
			return oDp;
		}
		dataSourceRepository.deleteById(id);
		return oDp;
	}
	
	public DataSource createDataSource(DataSource obj){
		logger.info("Create new data source");
		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return dataSourceRepository.save(obj);
	}
	
	public DataSource updateDataSource(DataSource obj){
		logger.info("Update data source. Id: {}", obj.getId());
		if(!dataSourceRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "DataSource", "idnotfound");
		}
		if(Objects.isNull(obj.getCloudEnvironment()) || (!Objects.isNull(obj.getCloudEnvironment()) && obj.getCloudEnvironment().getId() < 0)) {
			throw new BadRequestAlertException("Invalid cloud environment (account) id", "DataSource", "idnotfound");
		}
		Optional<CloudEnvironment> oce = cloudEnvironmentService.getCloudEnvironment(obj.getCloudEnvironment().getId());
		if(!oce.isPresent()) {
			throw new BadRequestAlertException("Parent cloud environment not found", "DataSource", "parentidnotfound");
		}
		obj.setUpdatedOn(Instant.now());
		return dataSourceRepository.save(obj);
	}
	
	public Optional<DataSource> partialUpdateDataSource(DataSource obj){
		logger.info("Update data source partialy. Id: {}", obj.getId());
		if(!dataSourceRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "DataSource", "idnotfound");
		}
		
		if(!Objects.isNull(obj.getCloudEnvironment()) && obj.getCloudEnvironment().getId() < 0) {
			throw new BadRequestAlertException("Invalid cloud environment (account) id", "DataSource", "idnotfound");
		}
		
		Optional<DataSource> result = dataSourceRepository.findById(obj.getId())
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
				if(!Objects.isNull(obj.getCloudEnvironment())) {
					Optional<CloudEnvironment> oce = cloudEnvironmentService.getCloudEnvironment(obj.getCloudEnvironment().getId());
					if(oce.isPresent()) {
						existingObj.setCloudEnvironment(oce.get());
					}else {
						throw new BadRequestAlertException("Parent cloud environment not found", "DataSource", "parentidnotfound");
					}
				}
				existingObj.updatedOn(Instant.now());
				return existingObj;
			})
			.map(dataSourceRepository::save);
		return result;
	}
	
	public List<DataSource> searchAllDataSource(Map<String, String> obj) {
		logger.info("Search data sources");
		DataSource dp = new DataSource();
		boolean isFilter = false;
		
		if(!StringUtils.isBlank(obj.get("id"))) {
			dp.setId(Long.parseLong(obj.get("id")));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("name"))) {
			dp.setName(obj.get("name"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("description"))) {
			dp.setDescription(obj.get("description"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("type"))) {
			dp.setType(obj.get("type"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			dp.setStatus(obj.get("status"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("cloudEnvironmentId"))) {
			Optional<CloudEnvironment> oce = cloudEnvironmentService.getCloudEnvironment(Long.parseLong(obj.get("cloudEnvironmentId")));
			if(oce.isPresent()) {
				dp.setCloudEnvironment(oce.get());
				isFilter = true;
			}else {
				return Collections.emptyList();
			}
		}
		
		List<DataSource> list = null;
		if(isFilter) {
			list = dataSourceRepository.findAll(Example.of(dp), Sort.by(Direction.DESC, "id"));
		}else {
			list = dataSourceRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	
}
