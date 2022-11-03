package com.synectiks.asset.controller.testservicedata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

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

public class ClusterServiceDto extends ServiceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private ClusterServiceLoaction clusterServiceLoaction ;
    

	public ClusterServiceLoaction getClusterServiceLoaction() {
		return clusterServiceLoaction;
	}

	public void setClusterServiceLoaction(ClusterServiceLoaction clusterServiceLoaction) {
		this.clusterServiceLoaction = clusterServiceLoaction;
	}
	
	private class ClusterServiceLoaction{
    	private String landingZone;
        private String productEnclave;
        private String clusterId;
        private String clusterName;     
        private String clusterNamespace;
        private String managementUrl;
		public String getLandingZone() {
			return landingZone;
		}
		public void setLandingZone(String landingZone) {
			this.landingZone = landingZone;
		}
		public String getProductEnclave() {
			return productEnclave;
		}
		public void setProductEnclave(String productEnclave) {
			this.productEnclave = productEnclave;
		}
		public String getClusterId() {
			return clusterId;
		}
		public void setClusterId(String clusterId) {
			this.clusterId = clusterId;
		}
		public String getClusterName() {
			return clusterName;
		}
		public void setClusterName(String clusterName) {
			this.clusterName = clusterName;
		}
		public String getClusterNamespace() {
			return clusterNamespace;
		}
		public void setClusterNamespace(String clusterNamespace) {
			this.clusterNamespace = clusterNamespace;
		}
	
		public String getManagementUrl() {
			return managementUrl;
		}
		public void setManagementUrl(String managementUrl) {
			this.managementUrl = managementUrl;
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
		// String serviceNature = (String)jsonObject.get("serviceNature");
		// this.setServiceNature(serviceNature);
		// if("Common".equalsIgnoreCase(serviceNature)) {
		// 	this.setAssociatedCommonService((String)jsonObject.get("associatedCommonService"));
		// }else if("Business".equalsIgnoreCase(serviceNature)) {
		// 	this.setAssociatedBusinessService((String)jsonObject.get("associatedBusinessService"));
		// }
		
		JSONObject clusterJson = (JSONObject)jsonObject.get("clusterServiceLoaction");
		ClusterServiceLoaction csl = new ClusterServiceLoaction();
		
		csl.setLandingZone((String)clusterJson.get("landingZone"));
		csl.setProductEnclave((String)clusterJson.get("productEnclave"));
		csl.setClusterId((String)clusterJson.get("clusterId"));
		csl.setClusterName((String)clusterJson.get("clusterName"));
		csl.setClusterNamespace((String)clusterJson.get("clusterNamespace"));
		csl.setManagementUrl((String)clusterJson.get("managementUrl"));
		this.setClusterServiceLoaction(csl);
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

	// @Override
	// public String toString() {
	// 	return "ClusterServiceDto [id=" + id + ", serviceType=" + serviceType + ", serviceHostingType="
	// 			+ serviceHostingType + ", serviceNature=" + serviceNature + ", associatedCommonService="
	// 			+ associatedCommonService + ", associatedBusinessService=" + associatedBusinessService
	// 			+ ", clusterServiceLoaction=" + clusterServiceLoaction + ", associatedOU=" + associatedOU
	// 			+ ", associatedDept=" + associatedDept + ", associatedProduct=" + associatedProduct + ", associatedEnv="
	// 			+ associatedEnv + "]";
	// }

	
	
}
