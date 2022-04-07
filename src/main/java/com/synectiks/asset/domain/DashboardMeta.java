package com.synectiks.asset.domain;

import java.io.Serializable;


public class DashboardMeta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private boolean isStarred = false;
    private boolean isHome = false;
    private boolean isSnapshot = false;           
    private String type = "db";
    private boolean canSave = false;
    private boolean canEdit = false;
    private boolean canAdmin = false;
    private boolean canStar = false;
    private String slug;
    private String url;
    private int version;
    private boolean hasAcl = false;
    private boolean isFolder = false;
    private Long folderId;
    private String folderTitle;
    private String folderUrl;
    private boolean provisioned = false;
    private String provisionedExternalId;
    
	public boolean isStarred() {
		return isStarred;
	}
	public void setStarred(boolean isStarred) {
		this.isStarred = isStarred;
	}
	public boolean isHome() {
		return isHome;
	}
	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	public boolean isSnapshot() {
		return isSnapshot;
	}
	public void setSnapshot(boolean isSnapshot) {
		this.isSnapshot = isSnapshot;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isCanSave() {
		return canSave;
	}
	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	public boolean isCanAdmin() {
		return canAdmin;
	}
	public void setCanAdmin(boolean canAdmin) {
		this.canAdmin = canAdmin;
	}
	public boolean isCanStar() {
		return canStar;
	}
	public void setCanStar(boolean canStar) {
		this.canStar = canStar;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isHasAcl() {
		return hasAcl;
	}
	public void setHasAcl(boolean hasAcl) {
		this.hasAcl = hasAcl;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public String getFolderTitle() {
		return folderTitle;
	}
	public void setFolderTitle(String folderTitle) {
		this.folderTitle = folderTitle;
	}
	public String getFolderUrl() {
		return folderUrl;
	}
	public void setFolderUrl(String folderUrl) {
		this.folderUrl = folderUrl;
	}
	public boolean isProvisioned() {
		return provisioned;
	}
	public void setProvisioned(boolean provisioned) {
		this.provisioned = provisioned;
	}
	public String getProvisionedExternalId() {
		return provisionedExternalId;
	}
	public void setProvisionedExternalId(String provisionedExternalId) {
		this.provisionedExternalId = provisionedExternalId;
	}
	
 }
