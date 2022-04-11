package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Size(max = 5000)
  @Column(name = "description", length = 5000)
  private String description;

  @Column(name = "status")
  private String status;

  @Column(name = "created_on")
  private Instant createdOn;

  @Column(name = "updated_on")
  private Instant updatedOn;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_by")
  private String createdBy;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  @Transient
  @JsonProperty
  private List<Services> serviceList;
  
  @Transient
  @JsonProperty
  private List<DeploymentEnvironment> deploymentEnvironmentList;
  
  @Transient
  @JsonProperty
  private ProductBilling productBilling;
  
  public Long getId() {
    return this.id;
  }

  public Product id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public Product name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public Product description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return this.status;
  }

  public Product status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public Product createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public Product updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public Product updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public Product createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Product)) {
      return false;
    }
    return id != null && id.equals(((Product) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }

	public List<Services> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Services> serviceList) {
		this.serviceList = serviceList;
	}

	public List<DeploymentEnvironment> getDeploymentEnvironmentList() {
		return deploymentEnvironmentList;
	}

	public void setDeploymentEnvironmentList(List<DeploymentEnvironment> deploymentEnvironmentList) {
		this.deploymentEnvironmentList = deploymentEnvironmentList;
	}

	public ProductBilling getProductBilling() {
		return productBilling;
	}

	public void setProductBilling(ProductBilling productBilling) {
		this.productBilling = productBilling;
	}
}
