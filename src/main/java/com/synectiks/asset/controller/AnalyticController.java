package com.synectiks.asset.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.response.ServiceDetailReportResponse;
import com.synectiks.asset.util.RandomUtil;
import com.synectiks.asset.util.UniqueProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalyticController {

	private static final Logger logger = LoggerFactory.getLogger(AnalyticController.class);

	@Autowired
	private UniqueProductUtil uniqueProductUtil;
	
	@Autowired
	private ServiceDetailService serviceDetailService;

	@GetMapping("/analytics/cloud-wise-spend")
	public ResponseEntity<Map<String, ObjectNode>> getCloudWiseSpend(@RequestParam Map<String, String> obj){
		logger.info("Request to get cloud wise spend");
		ObjectMapper mapper = Constants.instantiateMapper();
		Map<String, ObjectNode> map = new HashMap<>();
		for(String cloud: Constants.AVAILABLE_CLOUDS) {
			ObjectNode node = mapper.createObjectNode();
			Integer current = RandomUtil.getRandom(50000, 50500);
			Integer prev = RandomUtil.getRandom(40000, 50000);
			Integer variance = (current-prev)*100/current;
			node.put("currentTotal", current);
			node.put("previousTotal", prev);
			node.put("variance", variance);
			map.put(cloud, node);
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@GetMapping("/analytics/sla-central")
	public ResponseEntity<Map<String, ObjectNode>> getAvgSlaCentralData(@RequestParam Map<String, String> paramObj){
		logger.info("Request to get sla central analytics");
		Map<String, ObjectNode> map = new HashMap<>();
		ObjectMapper mapper = Constants.instantiateMapper();
		Map<String, String> obj = new HashMap<>();
        for(String cloud: Constants.AVAILABLE_CLOUDS) {
            List<String> producList = uniqueProductUtil.getProducts(cloud);
            // cloud, product, environment: search service detail HRMS/PROD ---
            for(String product: producList){
                obj.clear();
                obj.put("cloudType", cloud);
                obj.put("associatedProduct", product);
                obj.put("associatedEnv", "PROD");
                ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(obj);
                Integer perfSla = 0;
                Integer avlSla = 0;
                Integer relSla = 0;
                Integer secSla = 0;
                Integer endSla = 0;
                Integer avgPerfSla = 0;
                Integer avgAvlSla = 0;
                Integer avgRelSla = 0;
                Integer avgSecSla = 0;
                Integer avgEndSla = 0;
                for(ServiceDetail serviceDetail: sdr.getServices()){
                	Map<String, Object> dsTypeMap = serviceDetail.getSla_json();
                	if(dsTypeMap != null) {
                		perfSla = perfSla + (Integer)((Map)dsTypeMap.get(Constants.PERFORMANCE)).get("sla");
                        avlSla = avlSla + (Integer)((Map)dsTypeMap.get(Constants.AVAILABILITY)).get("sla");
                        relSla = relSla + (Integer)((Map)dsTypeMap.get(Constants.RELIABILITY)).get("sla");
                        secSla = secSla + (Integer)((Map)dsTypeMap.get(Constants.SECURITY)).get("sla");
                        endSla = endSla + (Integer)((Map)dsTypeMap.get(Constants.ENDUSAGE)).get("sla");
                	}
                    
                }
                if(perfSla > 0) {
                	avgPerfSla = perfSla/sdr.getServices().size();
                }
                if(avlSla > 0) {
                	avgAvlSla = avlSla/sdr.getServices().size();
                }
                if(relSla > 0) {
                	avgRelSla = relSla/sdr.getServices().size();
                }
                if(secSla > 0) {
                	avgSecSla = secSla/sdr.getServices().size();
                }
                if(endSla > 0) {
                	avgEndSla = endSla/sdr.getServices().size();
                }
                ObjectNode node = mapper.createObjectNode();
    			node.put("Performance", avgPerfSla);
    			node.put("Availability", avgAvlSla);
    			node.put("Reliability", avgRelSla);
    			node.put("Security", avgSecSla);
    			node.put("End Usage", avgEndSla);
    			map.put(product, node);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
