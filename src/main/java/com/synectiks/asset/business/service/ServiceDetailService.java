package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
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

import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceDetailRepository;
import com.synectiks.asset.response.AvailabilityResponse;
import com.synectiks.asset.response.DataProtectionResponse;
import com.synectiks.asset.response.PerformanceResponse;
import com.synectiks.asset.response.SecurityResponse;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.response.ServiceDetailReportingResponse;
import com.synectiks.asset.response.ServiceDetailResponse;
import com.synectiks.asset.response.ServiceDetailStats;
import com.synectiks.asset.response.UserExperianceResponse;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class ServiceDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceDetailService.class);
		
	@Autowired
	ServiceDetailRepository serviceDetailRepository;
	
	public Optional<ServiceDetail> getServiceDetail(Long id) {
		logger.info("Get service detail by id: {}", id);
		return serviceDetailRepository.findById(id);
	}
	
	public List<ServiceDetail> getAllServiceDetail() {
		logger.info("Get all service detail");
		return serviceDetailRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceDetail> deleteServiceDetail(Long id) {
		logger.info("Delete service detail by id: {}", id);
		Optional<ServiceDetail> oObj = getServiceDetail(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. Service detail cannot be deleted", id);
			return oObj;
		}
		serviceDetailRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceDetail createServiceDetail(ServiceDetail obj){
		logger.info("Create new service detail");
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}

		Instant instant = Instant.now();
		obj.setCreatedOn(instant);
		obj.setUpdatedOn(instant);
		return serviceDetailRepository.save(obj);
	}
	
	public ServiceDetail updateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail. Id: {}", obj.getId());
		if(!serviceDetailRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			obj.setStatus(obj.getStatus().toUpperCase());
		}
		obj.setUpdatedOn(Instant.now());
		return serviceDetailRepository.save(obj);
	}
	
	public Optional<ServiceDetail> partialUpdateServiceDetail(ServiceDetail obj){
		logger.info("Update service detail partialy. Id: {}", obj.getId());
		if(!serviceDetailRepository.existsById(obj.getId())) {
			throw new BadRequestAlertException("Entity not found", "ServiceDetail", "idnotfound");
		}
		Optional<ServiceDetail> result = serviceDetailRepository.findById(obj.getId())
			.map(existingObj ->{
				if(!StringUtils.isBlank(obj.getName())) {
					existingObj.setName(obj.getName());
				}
				if(!StringUtils.isBlank(obj.getDescription())) {
					existingObj.setDescription(obj.getDescription());
				}
				
				if(!StringUtils.isBlank(obj.getAssociatedOU())) {
					existingObj.setAssociatedOU(obj.getAssociatedOU());
				}
				if(!StringUtils.isBlank(obj.getAssociatedDept())) {
					existingObj.setAssociatedDept(obj.getAssociatedDept());
				}
				if(!StringUtils.isBlank(obj.getAssociatedProduct())) {
					existingObj.setAssociatedProduct(obj.getAssociatedProduct());
				}
				if(!StringUtils.isBlank(obj.getAssociatedEnv())) {
					existingObj.setAssociatedEnv(obj.getAssociatedEnv());
				}
				if(!StringUtils.isBlank(obj.getAssociatedLandingZone())) {
					existingObj.setAssociatedLandingZone(obj.getAssociatedLandingZone());
				}
				if(!StringUtils.isBlank(obj.getAssociatedProductEnclave())) {
					existingObj.setAssociatedProductEnclave(obj.getAssociatedProductEnclave());
				}
				if(!StringUtils.isBlank(obj.getAssociatedCluster())) {
					existingObj.setAssociatedCluster(obj.getAssociatedCluster());
				}
				if(!StringUtils.isBlank(obj.getServiceNature())) {
					existingObj.setServiceNature(obj.getServiceNature());
				}
				if(!StringUtils.isBlank(obj.getAssociatedCommonService())) {
					existingObj.setAssociatedCommonService(obj.getAssociatedCommonService());
				}
				if(!StringUtils.isBlank(obj.getAssociatedBusinessService())) {
					existingObj.setAssociatedBusinessService(obj.getAssociatedBusinessService());
				}
				if(!StringUtils.isBlank(obj.getServiceType())) {
					existingObj.setServiceType(obj.getServiceType());
				}
				if(!StringUtils.isBlank(obj.getServiceHostingType())) {
					existingObj.setServiceHostingType(obj.getServiceHostingType());
				}
				if(!StringUtils.isBlank(obj.getAssociatedClusterNamespace())) {
					existingObj.setAssociatedClusterNamespace(obj.getAssociatedClusterNamespace());
				}
				if(!StringUtils.isBlank(obj.getAssociatedManagedCloudServiceLocation())) {
					existingObj.setAssociatedManagedCloudServiceLocation(obj.getAssociatedManagedCloudServiceLocation());
				}
				if(!StringUtils.isBlank(obj.getAssociatedCloudElementId())) {
					existingObj.setAssociatedCloudElementId(obj.getAssociatedCloudElementId());
				}
				if(!StringUtils.isBlank(obj.getAssociatedGlobalServiceLocation())) {
					existingObj.setAssociatedGlobalServiceLocation(obj.getAssociatedGlobalServiceLocation());
				}
				if(!StringUtils.isBlank(obj.getAssociatedGlobalServiceLocation())) {
					existingObj.setAssociatedGlobalServiceLocation(obj.getAssociatedGlobalServiceLocation());
				}
				
				if(!StringUtils.isBlank(obj.getStatus())) {
					existingObj.setStatus(obj.getStatus().toUpperCase());
				}
				
				existingObj.setUpdatedOn(Instant.now());
				return existingObj;
			})
			.map(serviceDetailRepository::save);
		return result;
	}
	
	public List<ServiceDetail> searchAllServiceDetail(Map<String, String> obj) {
		logger.info("Search service detail");
		ServiceDetail cld = new ServiceDetail();
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
		
		
		if(!StringUtils.isBlank(obj.get("associatedOU"))) {
			cld.setAssociatedOU(obj.get("associatedOU"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedDept"))) {
			cld.setAssociatedDept(obj.get("associatedDept"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedProduct"))) {
			cld.setAssociatedProduct(obj.get("associatedProduct"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedEnv"))) {
			cld.setAssociatedEnv(obj.get("associatedEnv"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedLandingZone"))) {
			cld.setAssociatedLandingZone(obj.get("associatedLandingZone"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedProductEnclave"))) {
			cld.setAssociatedProductEnclave(obj.get("associatedProductEnclave"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedCluster"))) {
			cld.setAssociatedCluster(obj.get("associatedCluster"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("serviceNature"))) {
			cld.setServiceNature(obj.get("serviceNature"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedCommonService"))) {
			cld.setAssociatedCommonService(obj.get("associatedCommonService"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedBusinessService"))) {
			cld.setAssociatedBusinessService(obj.get("associatedBusinessService"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("serviceType"))) {
			cld.setServiceType(obj.get("serviceType"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("serviceHostingType"))) {
			cld.setServiceHostingType(obj.get("serviceHostingType"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedClusterNamespace"))) {
			cld.setAssociatedClusterNamespace(obj.get("associatedClusterNamespace"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedManagedCloudServiceLocation"))) {
			cld.setAssociatedManagedCloudServiceLocation(obj.get("associatedManagedCloudServiceLocation"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedCloudElementId"))) {
			cld.setAssociatedCloudElementId(obj.get("associatedCloudElementId"));
			isFilter = true;
		}
		if(!StringUtils.isBlank(obj.get("associatedGlobalServiceLocation"))) {
			cld.setAssociatedGlobalServiceLocation(obj.get("associatedGlobalServiceLocation"));
			isFilter = true;
		}
		
		if(!StringUtils.isBlank(obj.get("status"))) {
			cld.setStatus(obj.get("status").toUpperCase());
			isFilter = true;
		}
		
		List<ServiceDetail> list = null;
		if(isFilter) {
			list = serviceDetailRepository.findAll(Example.of(cld), Sort.by(Direction.DESC, "id"));
		}else {
			list = serviceDetailRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		return list;
	}
	

	public ServiceDetailReportingResponse searchServiceDetail(Map<String, String> obj) {
		List<ServiceDetailReportResponse> services = new ArrayList<>();
		List<ServiceDetail> serviceDetailList = searchAllServiceDetail(obj);
		
		for(ServiceDetail sd: serviceDetailList) {
			ServiceDetailResponse sdr = ServiceDetailResponse.from(sd);
			sdr.setPerformance(PerformanceResponse.builder().score(RandomUtil.getRandom()).build());
			sdr.setStats(getStats());
			sdr.setAvailability(AvailabilityResponse.builder().score(RandomUtil.getRandom()).build());
			sdr.setSecurity(SecurityResponse.builder().score(RandomUtil.getRandom()).build());
			sdr.setDataProtection(DataProtectionResponse.builder().score(RandomUtil.getRandom()).build());
			sdr.setUserExperiance(UserExperianceResponse.builder().score(RandomUtil.getRandom()).build());
			
			ServiceDetailReportResponse serviceDetailReportResponse = ServiceDetailReportResponse.from(sd.getId(), sdr);
			services.add(serviceDetailReportResponse);
		}
		return ServiceDetailReportingResponse.from(services);
	}
	
	private ServiceDetailStats getStats() {
		Integer a = RandomUtil.getRandom();
		Integer b = RandomUtil.getRandom();
		Integer c = RandomUtil.getRandom();
		Integer total = a+b+c;
		return ServiceDetailStats.from(total, a, b, c);
	}
}
