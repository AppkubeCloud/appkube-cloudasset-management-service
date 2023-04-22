package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A DiscoveredAssets.
 */
@Entity
@Table(name = "discovered_assets")
public class DiscoveredAssets extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "cloud", length = 50)
    private String cloud;

    @Column(name = "element_id")
    private String elementId;

    @Column(name = "element_type")
    private String elementType;

    @Column(name = "landing_zone")
    private String landingZone;

    @Column(name = "product_enclave")
    private String productEnclave;

    @Column(name = "arn")
    private String arn;

    @Column(name = "status")
    private String status;
    
    @Column(name = "tag_status")
    private String tagStatus;

    @OneToMany(mappedBy = "discoveredAsset")
    @JsonIgnoreProperties(value = { "discoveredAsset", "discoveredAsset" }, allowSetters = true)
    private Set<AssetServiceTag> assetServiceTags = new HashSet<>();
    
    public Long getId() {
        return this.id;
    }

    public DiscoveredAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCloud() {
        return this.cloud;
    }

    public DiscoveredAssets cloud(String cloud) {
        this.setCloud(cloud);
        return this;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getElementId() {
        return this.elementId;
    }

    public DiscoveredAssets elementId(String elementId) {
        this.setElementId(elementId);
        return this;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementType() {
        return this.elementType;
    }

    public DiscoveredAssets elementType(String elementType) {
        this.setElementType(elementType);
        return this;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getLandingZone() {
        return this.landingZone;
    }

    public DiscoveredAssets landingZone(String landingZone) {
        this.setLandingZone(landingZone);
        return this;
    }

    public void setLandingZone(String landingZone) {
        this.landingZone = landingZone;
    }

    public String getProductEnclave() {
        return this.productEnclave;
    }

    public DiscoveredAssets productEnclave(String productEnclave) {
        this.setProductEnclave(productEnclave);
        return this;
    }

    public void setProductEnclave(String productEnclave) {
        this.productEnclave = productEnclave;
    }

    public String getArn() {
        return this.arn;
    }

    public DiscoveredAssets arn(String arn) {
        this.setArn(arn);
        return this;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getStatus() {
        return this.status;
    }

    public DiscoveredAssets status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscoveredAssets)) {
            return false;
        }
        return id != null && id.equals(((DiscoveredAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscoveredAssets{" +
            "id=" + getId() +
            ", cloud='" + getCloud() + "'" +
            ", elementId='" + getElementId() + "'" +
            ", elementType='" + getElementType() + "'" +
            ", landingZone='" + getLandingZone() + "'" +
            ", productEnclave='" + getProductEnclave() + "'" +
            ", arn='" + getArn() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }

	public String getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}

	public Set<AssetServiceTag> getAssetServiceTags() {
		return assetServiceTags;
	}

	public void setAssetServiceTags(Set<AssetServiceTag> assetServiceTags) {
		this.assetServiceTags = assetServiceTags;
	}
}
