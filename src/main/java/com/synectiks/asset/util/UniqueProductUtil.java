package com.synectiks.asset.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synectiks.asset.config.Constants;
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
        if(Constants.cache.containsKey(Constants.PRODUCT_CACHE_KEY)) {
            List<String> productList = (List<String>)Constants.cache.get(Constants.PRODUCT_CACHE_KEY);
            if(productList != null && productList.size() > 0){
                return productList;
            }
        }
        logger.debug("Product list not found in cache. Getting it from database");
		ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(new HashMap<>());
		Map<String, String> map = new HashMap<>();
		for(ServiceDetail sd: sdr.getServices()) {
			map.put((String)sd.getMetadata_json().get("associatedProduct"), (String)sd.getMetadata_json().get("associatedProduct"));
		}
		return new ArrayList<>(map.keySet());
	}

    public List<String> getProducts(String cloud){
        logger.info("Request to get unique product list of cloud: {}",cloud);
        Map<String, String> obj = new HashMap<>();
        obj.put("cloudType", cloud);
        ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
        Map<String, String> map = new HashMap<>();
        for(ServiceDetail sd: sdr.getServices()) {
            map.put((String)sd.getMetadata_json().get("associatedProduct"), (String)sd.getMetadata_json().get("associatedProduct"));
        }
        return new ArrayList<>(map.keySet());
    }
}
