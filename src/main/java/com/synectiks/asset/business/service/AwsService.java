package com.synectiks.asset.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.DashboardMeta;
import com.synectiks.asset.domain.ServiceProviderCloudAccount;
import com.synectiks.asset.util.Utils;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

@Service
public class AwsService {
	
	private static final Logger logger = LoggerFactory.getLogger(AwsService.class);
	
//	@Autowired
//	private CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	private ServiceProviderCloudAccountService serviceProviderCloudAccountService;
	
	
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
		
		String bucket = getBucket(jsonLocation);
		String fileName = getFileName(jsonLocation);
		
//		try {
//			String ary[] = jsonLocation.split("/");
//			if(ary.length < 2) {
//				throw new BadRequestAlertException("Mandatory fields jsonLocation either missing or not correct. Its should be bucket/filename.json", "Dashboard", "mandatory.field.missing");
//			}
//			bucket = ary[0];
//			fileName = ary[1];
//		}catch(BadRequestAlertException e) {
//			throw e;
//		}catch(Exception e) {
//			throw new BadRequestAlertException("Mandatory fields missing", "Dashboard", "mandatory.field.missing");
//		}
		
		Map<String, String> searchMap = new HashMap<>();
		ServiceProviderCloudAccount spca = serviceProviderCloudAccountService.searchAllServiceProviderCloudAccount(searchMap).get(0);
		
		Dashboard dashboard = new Dashboard();
		AmazonS3 s3Client = Utils.getAmazonS3Client(spca.getAccessKey(), spca.getSecretKey(), spca.getRegion());
		if(s3Client == null) {
			throw new BadRequestAlertException("AWS S3 client connection could not be establised", "Dashboard", "aws.s3.connection.failed");
		}
		
		try {
			S3Object file = s3Client.getObject(bucket, fileName);
			
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
			dataNode.put("id", 0);
			dataNode.put("uid", "");
			data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataNode);
			logger.debug("Datasource updated. Json : "+ data);
			
			String uid = RandomStringUtils.random(8, true, true);
			dashboard.setUid(uid);
			dashboard.setData(data);
			
			DashboardMeta meta = new DashboardMeta();
			meta.setSlug(dashboard.getSlug());
			
			dashboard.setDashboardMeta(meta);
		}finally {
			if(s3Client != null) {
				s3Client.shutdown();
			}
		}
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
	
//	public static void main(String a[]) {
//		String jsonLocation ="s3://updated-dashboards-bucket/NewDashboards/CloudTrail/AWS CloudTrail - Overview.txt";
////		String f = jsonLocation.substring(jsonLocation.indexOf("//")+2);
////		System.out.println(f);
////		String fName = f.substring(f.lastIndexOf("/")+1);
//		System.out.println("File : "+getFileName(jsonLocation));
//		
////		String bPath = f.substring(0,f.lastIndexOf("/"));
//		System.out.println("Path : "+getBucket(jsonLocation));
//		
//	}
	
	private String getBucket(String jsonLocation) {
		String f = jsonLocation.substring(jsonLocation.indexOf("//")+2);
		return f.substring(0,f.lastIndexOf("/"));
	}
	
	private String getFileName(String jsonLocation) {
		String f = jsonLocation.substring(jsonLocation.indexOf("//")+2);
		return f.substring(f.lastIndexOf("/")+1);
		
	}
}
