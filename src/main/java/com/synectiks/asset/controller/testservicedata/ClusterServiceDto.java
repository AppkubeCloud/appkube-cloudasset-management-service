package com.synectiks.asset.controller.testservicedata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

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
	private Long id;
    private String landingZone;
    private String productEnclave;
    private String clusterId;
    private String clusterName;     
    private String clusterNamespace;
    private String serviceName;
    private String managementUrl;
    private String serviceType;
    private String serviceHostingType;
    private String serviceNature;
    private String associatedCommonService;
    private String associatedBusinessService;
    
   	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getManagementUrl() {
		return managementUrl;
	}

	public void setManagementUrl(String managementUrl) {
		this.managementUrl = managementUrl;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceHostingType() {
		return serviceHostingType;
	}

	public void setServiceHostingType(String serviceHostingType) {
		this.serviceHostingType = serviceHostingType;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

	public String getAssociatedCommonService() {
		return associatedCommonService;
	}

	public void setAssociatedCommonService(String associatedCommonService) {
		this.associatedCommonService = associatedCommonService;
	}

	public String getAssociatedBusinessService() {
		return associatedBusinessService;
	}

	public void setAssociatedBusinessService(String associatedBusinessService) {
		this.associatedBusinessService = associatedBusinessService;
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
		
		String serviceNature = (String)jsonObject.get("serviceNature");
		this.setServiceNature(serviceNature);
		if("Common".equalsIgnoreCase(serviceNature)) {
			this.setAssociatedCommonService((String)jsonObject.get("associatedCommonService"));
		}else if("Business".equalsIgnoreCase(serviceNature)) {
			this.setAssociatedBusinessService((String)jsonObject.get("associatedBusinessService"));
		}
		
		JSONObject clusterJson = (JSONObject)jsonObject.get("clusterServiceLoaction");
		
		this.setLandingZone((String)clusterJson.get("landingZone"));
		this.setProductEnclave((String)clusterJson.get("productEnclave"));
		this.setClusterId((String)clusterJson.get("clusterId"));
		this.setClusterName((String)clusterJson.get("clusterName"));
		this.setClusterNamespace((String)clusterJson.get("clusterNamespace"));
		this.setServiceName((String)clusterJson.get("serviceName"));
		this.setDescription((String)clusterJson.get("description"));
		this.setManagementUrl((String)clusterJson.get("managementUrl"));
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
	
	@Override
	public String toString() {
		return "ClusterServiceDto [id=" + id + ", landingZone=" + landingZone + ", productEnclave=" + productEnclave
				+ ", clusterId=" + clusterId + ", clusterName=" + clusterName + ", clusterNamespace=" + clusterNamespace
				+ ", serviceName=" + serviceName + ", managementUrl=" + managementUrl + ", serviceType=" + serviceType
				+ ", serviceHostingType=" + serviceHostingType + ", serviceNature=" + serviceNature
				+ ", associatedCommonService=" + associatedCommonService + ", associatedBusinessService="
				+ associatedBusinessService + ", associatedOU=" + associatedOU + ", associatedDept=" + associatedDept
				+ ", associatedProduct=" + associatedProduct + ", associatedEnv=" + associatedEnv + "]";
	}
	
	
}
