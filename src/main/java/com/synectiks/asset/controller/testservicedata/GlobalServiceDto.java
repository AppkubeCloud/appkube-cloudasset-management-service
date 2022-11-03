package com.synectiks.asset.controller.testservicedata;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.business.service.ServiceDetailService;
import com.synectiks.asset.domain.ServiceDetail;

public class GlobalServiceDto extends ServiceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GlobalServiceLoaction globalServiceLoaction;

	public GlobalServiceLoaction getGlobalServiceLoaction() {
		return globalServiceLoaction;
	}

	public void setGlobalServiceLoaction(GlobalServiceLoaction globalServiceLoaction) {
		this.globalServiceLoaction = globalServiceLoaction;
	}

	public class GlobalServiceLoaction{

		private String cloudElementType;
		private String productEnclave;
		private Map<String, String> cloudElementId;  
		private String managementUrl;

		public Map<String, String> getCloudElementId() {
			return cloudElementId;
		}
		public void setCloudElementId(Map<String, String> cloudElementId) {
			this.cloudElementId = cloudElementId;
		}
		
		public String getManagementUrl() {
			return managementUrl;
		}
		public void setManagementUrl(String managementUrl) {
			this.managementUrl = managementUrl;
		}
		public String getProductEnclave() {
			return productEnclave;
		}
		public void setProductEnclave(String productEnclave) {
			this.productEnclave = productEnclave;
		}
		public String getCloudElementType() {
			return cloudElementType;
		}
		public void setCloudElementType(String cloudElementType) {
			this.cloudElementType = cloudElementType;
		}
		
	}
	
	@Override
	public ServiceDto readJson(String jsonFile) {
				
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			Object obj = parser.parse(new FileReader(jsonFile));
            jsonObject =  (JSONObject) obj;
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.setAssociatedOU((String)jsonObject.get("associatedOU"));
		this.setAssociatedDept((String)jsonObject.get("associatedDept"));
		this.setAssociatedProduct((String)jsonObject.get("associatedProduct"));
		this.setAssociatedEnv((String)jsonObject.get("associatedEnv"));
		this.setServiceType((String)jsonObject.get("serviceType"));
		this.setServiceHostingType((String)jsonObject.get("serviceHostingType"));
		this.setServiceNature((String)jsonObject.get("serviceNature"));
		this.setServiceName((String)jsonObject.get("serviceName"));
		this.setDescription((String)jsonObject.get("description"));
		this.setAssociatedCommonService((String)jsonObject.get("associatedCommonService"));
		this.setAssociatedBusinessService((String)jsonObject.get("associatedBusinessService"));

		JSONObject globalJson = (JSONObject)jsonObject.get("globalServiceLoaction");
		GlobalServiceLoaction csl = new GlobalServiceLoaction();
		
		csl.setProductEnclave((String)globalJson.get("productEnclave"));
		csl.setCloudElementId((Map)globalJson.get("cloudElementId"));
		csl.setCloudElementType((String)globalJson.get("cloudElementType"));
		csl.setManagementUrl((String)globalJson.get("managementUrl"));
		
	    return this;

	
	}

	@Override
	public void save() throws JsonParseException, JsonMappingException, IOException {
		//convert dto to JsonNode to HashMap to service_details
		ObjectMapper mapper = new ObjectMapper(); 
		JsonNode node = mapper.convertValue(this, JsonNode.class);
		System.out.println(node.toPrettyString());
		HashMap<String, Object> obj = mapper.readValue(node.toString().getBytes(), new TypeReference<HashMap<String,Object>>() {});
		ServiceDetail sd = ServiceDetail.builder().metadata_json(obj).build();
		ServiceDetailService sds = AssetserviceApp.getBean(ServiceDetailService.class);
		sds.createServiceDetail(sd);
		
	
	}
}
