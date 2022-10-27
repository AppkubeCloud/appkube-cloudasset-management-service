package com.synectiks.asset.controller.testservicedata;

import java.io.Serializable;
import java.util.Map;

import org.json.simple.JSONObject;

public class CloudServiceDto extends ServiceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String landingZone;
	private String productEnclave;
	private String cloudElementType;
	private Map<String, String> cloudElementId;
	private String managementUrl;
	
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

	public String getCloudElementType() {
		return cloudElementType;
	}

	public void setCloudElementType(String cloudElementType) {
		this.cloudElementType = cloudElementType;
	}

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
	
	@Override
	public ServiceDto readJson(String json) {
		return null;
	}
}
