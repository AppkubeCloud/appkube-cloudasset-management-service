package com.synectiks.asset.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String accountId;
    private String type;
    private String title;
    private String description;
    private String sourceJsonRef;
    private byte[] sourceJson;
    private String sourceJsonContentType;
    private boolean status;
    private Instant createdOn;
    private Instant updatedOn;
    private String updatedBy;
    private String createdBy;
//    private String unit;
    private String instance;
    private boolean isOpened = false;
    private Asset[] subData;
    
    private String tenantId;
    private String dashboardUuid;
    private String elementType;
    private String elementSubType;
    private String inputType;
    private String dashboardNature;
    private String organizationName;
    private String organizationalUnit;
    private String fileName;
    Map<String, List<Asset>> assetMap;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSourceJsonRef() {
		return sourceJsonRef;
	}
	public void setSourceJsonRef(String sourceJsonRef) {
		this.sourceJsonRef = sourceJsonRef;
	}
	public byte[] getSourceJson() {
		return sourceJson;
	}
	public void setSourceJson(byte[] sourceJson) {
		this.sourceJson = sourceJson;
	}
	public String getSourceJsonContentType() {
		return sourceJsonContentType;
	}
	public void setSourceJsonContentType(String sourceJsonContentType) {
		this.sourceJsonContentType = sourceJsonContentType;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Instant getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}
	public Instant getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public boolean isOpened() {
		return isOpened;
	}
	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}
	public Asset[] getSubData() {
		return subData;
	}
	public void setSubData(Asset[] subData) {
		this.subData = subData;
	}
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDashboardUuid() {
		return dashboardUuid;
	}
	public void setDashboardUuid(String dashboardUuid) {
		this.dashboardUuid = dashboardUuid;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getDashboardNature() {
		return dashboardNature;
	}
	public void setDashboardNature(String dashboardNature) {
		this.dashboardNature = dashboardNature;
	}
	
	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}
	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}
	@Override
	public String toString() {
		return "Asset [id=" + id + ", accountId=" + accountId + ", type=" + type + ", title=" + title + ", description="
				+ description + ", sourceJsonRef=" + sourceJsonRef + ", sourceJson=" + Arrays.toString(sourceJson)
				+ ", sourceJsonContentType=" + sourceJsonContentType + ", status=" + status + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy + ", instance="
				+ instance + ", isOpened=" + isOpened + ", subData=" + Arrays.toString(subData) + ", tenantId="
				+ tenantId + ", dashboardUuid=" + dashboardUuid + ", elementType=" + elementType + ", inputType="
				+ inputType + ", dashboardNature=" + dashboardNature + ", organizationName=" + organizationName
				+ ", organizationUnit=" + organizationalUnit + "]";
	}
	public Map<String, List<Asset>> getAssetMap() {
		return assetMap;
	}
	public void setAssetMap(Map<String, List<Asset>> assetMap) {
		this.assetMap = assetMap;
	}
	public String getElementSubType() {
		return elementSubType;
	}
	public void setElementSubType(String elementSubType) {
		this.elementSubType = elementSubType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
    
    
    
}
