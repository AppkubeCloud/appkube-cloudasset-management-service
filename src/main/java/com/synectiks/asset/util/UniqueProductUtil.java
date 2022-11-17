package com.synectiks.asset.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportResponse;

@Component("UniqueProductUtil")
public class UniqueProductUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(UniqueProductUtil.class);
	
	@Autowired
	private ServiceDetailService serviceDetailService;
	
	public List<String> getProducts(){
		logger.info("Request to get unique product list");
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(new HashMap<>());
		Map<String, String> map = new HashMap<>();
		for(ServiceDetail sd: sdr.getServices()) {
			map.put((String)sd.getMetadata_json().get("associatedProduct"), (String)sd.getMetadata_json().get("associatedProduct"));
		}
		return new ArrayList<>(map.keySet());
	}
}
