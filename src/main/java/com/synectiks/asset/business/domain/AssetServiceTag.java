package com.synectiks.asset.business.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A AssetServiceTag.
 */
@Entity
@Table(name = "asset_service_tag")
public class AssetServiceTag extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "assetServiceTags" }, allowSetters = true)
    private DiscoveredAssets discoveredAsset;
    
    @ManyToOne
    @JsonIgnoreProperties(value = { "assetServiceTags" }, allowSetters = true)
    private ServiceAllocation serviceAllocation;

	@Size(max = 500)
	@Column(name = "tag", length = 500)
	private String tag;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DiscoveredAssets getDiscoveredAsset() {
		return discoveredAsset;
	}

	public void setDiscoveredAsset(DiscoveredAssets discoveredAsset) {
		this.discoveredAsset = discoveredAsset;
	}

	public ServiceAllocation getServiceAllocation() {
		return serviceAllocation;
	}

	public void setServiceAllocation(ServiceAllocation serviceAllocation) {
		this.serviceAllocation = serviceAllocation;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
