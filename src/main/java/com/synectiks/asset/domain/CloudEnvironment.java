package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Client's cloud accounts
 */
@Entity
@Table(name = "cloud_environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CloudEnvironment implements Serializable {

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

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "access_key")
  private String accessKey;

  @Column(name = "secret_key")
  private String secretKey;

  @Column(name = "region")
  private String region;

  @Column(name = "vault_id")
  private String vaultId;

  @Column(name = "org_id")
  private Long orgId;

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

  /**
   * deprecate relationships
   */
  @ManyToOne
  private Cloud cloud;

  @ManyToOne
  private Department department;

  @Transient
  @JsonProperty
  private Integer totalProductEnclave;
  
  @Transient
  @JsonProperty
  private Integer totalProducts;
  
  @Transient
  @JsonProperty
  private Integer totalAppServices;
  
  @Transient
  @JsonProperty
  private Integer totalDataServices;
  
  public Long getId() {
    return this.id;
  }

  public CloudEnvironment id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public CloudEnvironment name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public CloudEnvironment description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAccountId() {
    return this.accountId;
  }

  public CloudEnvironment accountId(String accountId) {
    this.setAccountId(accountId);
    return this;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccessKey() {
    return this.accessKey;
  }

  public CloudEnvironment accessKey(String accessKey) {
    this.setAccessKey(accessKey);
    return this;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return this.secretKey;
  }

  public CloudEnvironment secretKey(String secretKey) {
    this.setSecretKey(secretKey);
    return this;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getRegion() {
    return this.region;
  }

  public CloudEnvironment region(String region) {
    this.setRegion(region);
    return this;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getVaultId() {
    return this.vaultId;
  }

  public CloudEnvironment vaultId(String vaultId) {
    this.setVaultId(vaultId);
    return this;
  }

  public void setVaultId(String vaultId) {
    this.vaultId = vaultId;
  }

  public Long getOrgId() {
    return this.orgId;
  }

  public CloudEnvironment orgId(Long orgId) {
    this.setOrgId(orgId);
    return this;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public String getStatus() {
    return this.status;
  }

  public CloudEnvironment status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public CloudEnvironment createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public CloudEnvironment updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public CloudEnvironment updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public CloudEnvironment createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Cloud getCloud() {
    return this.cloud;
  }

  public void setCloud(Cloud cloud) {
    this.cloud = cloud;
  }

  public CloudEnvironment cloud(Cloud cloud) {
    this.setCloud(cloud);
    return this;
  }

  public Department getDepartment() {
    return this.department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public CloudEnvironment department(Department department) {
    this.setDepartment(department);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CloudEnvironment)) {
      return false;
    }
    return id != null && id.equals(((CloudEnvironment) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CloudEnvironment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", orgId=" + getOrgId() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }

	public Integer getTotalProductEnclave() {
		return totalProductEnclave;
	}

	public void setTotalProductEnclave(Integer totalProductEnclave) {
		this.totalProductEnclave = totalProductEnclave;
	}

	public Integer getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(Integer totalProducts) {
		this.totalProducts = totalProducts;
	}

	public Integer getTotalAppServices() {
		return totalAppServices;
	}

	public void setTotalAppServices(Integer totalAppServices) {
		this.totalAppServices = totalAppServices;
	}

	public Integer getTotalDataServices() {
		return totalDataServices;
	}

	public void setTotalDataServices(Integer totalDataServices) {
		this.totalDataServices = totalDataServices;
	}
}
