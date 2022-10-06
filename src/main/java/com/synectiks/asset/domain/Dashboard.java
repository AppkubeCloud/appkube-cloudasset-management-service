package com.synectiks.asset.domain;

import java.io.Serializable;


public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cloudName;
    private String elementType;
    private String tenantId;
    private String accountId;
    private String inputType;
    private String fileName;
	
    private String inputSourceId; 
    private String title;
    private String slug;
    private String uid;
    private String uuid;
    private String data;
    private boolean isCloud = true;
    
    private int orgId = 1;
    private int gnetId = 0;
    private int version = 1;
    private String pluginId;
    private int folderId = 0;
    private boolean isFolder = false;
    private boolean hasAcl = false;
    private String sourceJsonRef;
    private Long id;
    private String url;
    private DashboardMeta dashboardMeta; 
    private String cloudElementId;
    
	public String getCloudName() {
		return cloudName;
	}
	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInputSourceId() {
		return inputSourceId;
	}
	public void setInputSourceId(String inputSourceId) {
		this.inputSourceId = inputSourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isCloud() {
		return isCloud;
	}
	public void setCloud(boolean isCloud) {
		this.isCloud = isCloud;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public int getGnetId() {
		return gnetId;
	}
	public void setGnetId(int gnetId) {
		this.gnetId = gnetId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getPluginId() {
		return pluginId;
	}
	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public boolean isHasAcl() {
		return hasAcl;
	}
	public void setHasAcl(boolean hasAcl) {
		this.hasAcl = hasAcl;
	}
	public String getSourceJsonRef() {
		return sourceJsonRef;
	}
	public void setSourceJsonRef(String sourceJsonRef) {
		this.sourceJsonRef = sourceJsonRef;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return data ;
	}
	public DashboardMeta getDashboardMeta() {
		return dashboardMeta;
	}
	public void setDashboardMeta(DashboardMeta dashboardMeta) {
		this.dashboardMeta = dashboardMeta;
	}
	public String getCloudElementId() {
		return cloudElementId;
	}
	public void setCloudElementId(String cloudElementId) {
		this.cloudElementId = cloudElementId;
	}
	
 }
