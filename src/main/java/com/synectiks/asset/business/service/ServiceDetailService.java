package com.synectiks.asset.business.service;

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
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceDetailRepository;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceDetailService.class);
		
	@Autowired
	ServiceDetailRepository serviceDetailJsonRepository;
	
	public Optional<ServiceDetail> getServiceDetail(Long id) {
		logger.info("Get service detail by id: {}", id);
		return serviceDetailJsonRepository.findById(id);
	}
	
	public List<ServiceDetail> getAllServiceDetail() {
		logger.info("Get all service detail");
		return serviceDetailJsonRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceDetail> deleteServiceDetail(Long id) {
		logger.info("Delete service detail by id: {}", id);
		Optional<ServiceDetail> oObj = getServiceDetail(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Service detail cannot be deleted", id);
			return oObj;
		}
		serviceDetailJsonRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceDetail createServiceDetail(ServiceDetail obj){
		logger.info("Create new service detail");
		return serviceDetailJsonRepository.save(obj);
	}
	
	public ServiceDetail updateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail. Id: {}", obj.getId());
		if(!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		return serviceDetailJsonRepository.save(obj);
	}
	
	public Optional<ServiceDetail> partialUpdateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail partialy. Id: {}", obj.getId());
		if(!serviceDetailJsonRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		Optional<ServiceDetail> result = serviceDetailJsonRepository.findById(obj.getId())
			.map(existingObj ->{
				return existingObj;
			})
			.map(serviceDetailJsonRepository::save);
		return result;
	}
	
	public ServiceDetailReportResponse searchAllServiceDetail(Map<String, String> obj) {
		logger.info("Search service detail json");
		Gson gson = new Gson(); 
		String json = gson.toJson(obj); 
		List<ServiceDetail> list = serviceDetailJsonRepository.findServiceDetails(json);
		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();
		resp.setServices(list);
		return resp;
	}
	
}
