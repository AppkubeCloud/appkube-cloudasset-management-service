package com.synectiks.asset.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.domain.CloudEnvironment;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.DashboardMeta;
import com.synectiks.asset.util.Utils;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class AwsService {
	
	private static final Logger logger = LoggerFactory.getLogger(AwsService.class);
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	public Dashboard getDashboardFromAwsS3(Map<String, String> object) throws IOException {
		logger.info("Downloading dashboard json from AWS");
		
		if (StringUtils.isBlank(object.get("dataSourceName")) || StringUtils.isBlank(object.get("associatedCloudElementType")) ||
				StringUtils.isBlank(object.get("associatedSLAType")) || StringUtils.isBlank(object.get("jsonLocation")) ||
				StringUtils.isBlank(object.get("associatedCloud")) || StringUtils.isBlank(object.get("accountId")) ) {
			logger.error("Mandatory fields missing");
			throw new BadRequestAlertException("Mandatory fields missing", "Dashboard", "mandatory.field.missing");
		}
		
//		String dashboardId = object.get("dashboardId");
//		String dashboardName = object.get("dashboardName");
//		String dataSourceId = object.get("dataSourceId");
		String dataSourceName = object.get("dataSourceName");
		String associatedCloudElementType = object.get("associatedCloudElementType");
		String associatedSLAType = object.get("associatedSLAType");
		String jsonLocation = object.get("jsonLocation");
		String associatedCloud = object.get("associatedCloud");
//		String associatedCreds = object.get("associatedCreds");
		String accountId = object.get("accountId");
		
		String bucket = null;
		String fileName = null;
		
		try {
			String ary[] = jsonLocation.split("/");
			if(ary.length < 2) {
				throw new BadRequestAlertException("Mandatory fields jsonLocation either missing or not correct. Its should be bucket/filename.json", "Dashboard", "mandatory.field.missing");
			}
			bucket = ary[0];
			fileName = ary[1];
		}catch(BadRequestAlertException e) {
			throw e;
		}catch(Exception e) {
			throw new BadRequestAlertException("Mandatory fields missing", "Dashboard", "mandatory.field.missing");
		}
		
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("accountId", accountId);
		List<CloudEnvironment> ceList = cloudEnvironmentService.searchAllCloudEnvironment(searchMap);
		if(ceList.size() == 0) {
			return null;
		}
		CloudEnvironment ce = ceList.get(0);
		
		AmazonS3 s3Client = Utils.getAmazonS3Client(ce.getAccessKey(), ce.getSecretKey(), ce.getRegion());
		S3Object file = s3Client.getObject(bucket, fileName);
		
		Dashboard dashboard = new Dashboard();
		
		dashboard.setCloudName(associatedCloud);
		dashboard.setElementType(associatedCloudElementType);
//		dashboard.setTenantId(tenantId);
		dashboard.setAccountId(accountId);
		dashboard.setInputType(associatedSLAType);
		dashboard.setFileName(fileName);
		dashboard.setInputSourceId(dataSourceName);
		String dName = associatedCloud+"_"+associatedCloudElementType+"_"+dataSourceName;
		dashboard.setTitle(dName);
		dashboard.setSlug(dName);
		String data = displayTextInputStream(file.getObjectContent());
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = mapper.createArrayNode();
		
		ObjectNode dataNode = (ObjectNode)mapper.readTree(data);
		for(JsonNode panel : dataNode.get("panels")) {
			ObjectNode oPanel = (ObjectNode)panel;
			oPanel.put("datasource", dataSourceName);
			arrayNode.add(oPanel);
        }
		dataNode.put("panels", arrayNode);
		data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataNode);
		logger.debug("Datasource updated. Json : "+ data);
		
		String uid = RandomStringUtils.random(8, true, true);
		dashboard.setUid(uid);
		dashboard.setData(data);
		
		DashboardMeta meta = new DashboardMeta();
		meta.setSlug(dashboard.getSlug());
		
		dashboard.setDashboardMeta(meta);
		return dashboard;
	}
	
	private String displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuffer sb = new StringBuffer();
		String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(" ");
//        	logger.debug(sb.toString());
        }
        return sb.toString();
	}
	
}
