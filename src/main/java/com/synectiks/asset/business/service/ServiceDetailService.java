package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceDetailRepository;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.ServiceDetailResponse;
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
		resp.setTotal(list.size());
		return resp;
	}
	
	public void updateViewJson(JsonNode node) {
		String apiKey[] = {"performance","availability","reliability","endUsage","security","compliance","alerts"};
		for(String key: apiKey) {
			serviceDetailJsonRepository.updateViewJson(node.get("id").asText(), key);
		}
	}
	
	public void createBulkData(JsonNode node) throws IOException {
		ServiceDetail sd = ServiceDetail.builder().build();
		ServiceDetailResponse sdr = ServiceDetailResponse.from(node);
		sd.setDetails(sdr);
		createServiceDetail(sd);
	}
	
	public ServiceDetailReportResponse searchServiceDetailWithFilter(Map<String, String> obj) {
		logger.info("Search service detail with filter");
		ServiceDetailReportResponse resp = ServiceDetailReportResponse.builder().build();
		
		List<ServiceDetail> list = serviceDetailJsonRepository.findAll();
		
		if(obj.size() == 0 ) {
			resp.setServices(list);
			resp.setTotal(list.size());
			return resp;
		}
		
		List<ServiceDetail> list2 = list;
		if(obj.containsKey("name")) {
			list2 = list2.stream()
					.filter(sd -> sd.getDetails().getName().equals(obj.get("name")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("description")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getDescription().equals(obj.get("description")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceType")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getServiceType().equalsIgnoreCase(obj.get("serviceType")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedOU")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedOU().equalsIgnoreCase(obj.get("associatedOU")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedEnv")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedEnv().equalsIgnoreCase(obj.get("associatedEnv")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceNature")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getServiceNature().equalsIgnoreCase(obj.get("serviceNature")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedDept")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedDept().equalsIgnoreCase(obj.get("associatedDept")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCluster")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedCluster().equalsIgnoreCase(obj.get("associatedCluster")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedProduct")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedProduct().equalsIgnoreCase(obj.get("associatedProduct")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("serviceHostingType")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getServiceHostingType().equalsIgnoreCase(obj.get("serviceHostingType")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedLandingZone")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedLandingZone().equalsIgnoreCase(obj.get("associatedLandingZone")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCommonService")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedCommonService().equalsIgnoreCase(obj.get("associatedCommonService")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedCloudElementId")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedCloudElementId().equals(obj.get("associatedCloudElementId")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedProductEnclave")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedProductEnclave().equalsIgnoreCase(obj.get("associatedProductEnclave")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedBusinessService")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedBusinessService().equalsIgnoreCase(obj.get("associatedBusinessService")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedClusterNamespace")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedClusterNamespace().equalsIgnoreCase(obj.get("associatedClusterNamespace")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedGlobalServiceLocation")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedGlobalServiceLocation().equalsIgnoreCase(obj.get("associatedGlobalServiceLocation")))
					.collect(Collectors.toList());
		}
		if(obj.containsKey("associatedManagedCloudServiceLocation")) {
			 list2 = list2.stream()
					.filter(sd -> sd.getDetails().getAssociatedManagedCloudServiceLocation().equalsIgnoreCase(obj.get("associatedManagedCloudServiceLocation")))
					.collect(Collectors.toList());
		}
		resp.setServices(list2);
		resp.setTotal(list2.size());
		return resp;
	}


}
