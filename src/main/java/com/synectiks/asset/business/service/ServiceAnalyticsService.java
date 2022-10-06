package com.synectiks.asset.business.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.synectiks.asset.domain.ServiceAnalytics;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.repository.ServiceAnalyticsRepository;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.util.DateFormatUtil;
import com.synectiks.asset.util.RandomUtil;

@Service
public class ServiceAnalyticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceAnalyticsService.class);
		
	@Autowired
	ServiceAnalyticsRepository serviceAnalyticsRepository;
	
	@Autowired
	ServiceDetailService serviceDetailService;
	
	public Optional<ServiceAnalytics> getServiceAnalytics(Long id) {
		logger.info("Get service-analytics by id: {}", id);
		return serviceAnalyticsRepository.findById(id);
	}
	
	public List<ServiceAnalytics> getAllServiceAnalytics() {
		logger.info("Get all service-analytics");
		return serviceAnalyticsRepository.findAll(Sort.by(Direction.DESC, "id"));
	}
	
	public Optional<ServiceAnalytics> deleteServiceAnalytics(Long id) {
		logger.info("Delete service-analytics by id: {}", id);
		Optional<ServiceAnalytics> oObj = getServiceAnalytics(id);
		if(!oObj.isPresent()) {
			logger.warn("Id {} not found. service-analytics cannot be deleted", id);
			return oObj;
		}
		serviceAnalyticsRepository.deleteById(id);
		return oObj;
	}
	
	public ServiceAnalytics createServiceAnalytics(ServiceAnalytics obj){
		logger.info("Create new service-analytics");
		return serviceAnalyticsRepository.save(obj);
	}
	
	public void createBulkAnalyticData(String startDateTime, String endDateTime, String type) throws ParseException, InterruptedException {
		Date startDate = DateFormatUtil.convertStringToUtilDate("MM-dd-yyyy hh:mm", startDateTime);
		Date endDate = DateFormatUtil.convertStringToUtilDate("MM-dd-yyyy hh:mm", endDateTime);
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(new HashMap());
		for(ServiceDetail sd: sdr.getServices()) {
			long pause = 10000L;
			long endTime = endDate.getTime();
			long startTime = startDate.getTime();
			float prevScore = 0;
			while(startTime <= endTime) {
				float score = RandomUtil.getRandom(90, 100);
				logger.debug("date : "+ new Date(startTime)+", prev score: "+prevScore+", score: "+score);
				float dif = score-prevScore;
				float rog = ((dif/score)*100);
				ServiceAnalytics sa =ServiceAnalytics.builder()
				.timeInterval(new Timestamp(startTime))
				.type(type)
				.score((int)score)
				.ServiceDetail(sd)
				.rateOfGrowth(String.valueOf(rog))
				.build();
				serviceAnalyticsRepository.save(sa);
				startTime = startTime + pause;
				prevScore = score;

			}
		}

	}
	
}
