package com.synectiks.asset.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Client's cloud accounts
 */
@Entity
@Table(name = "cloud_environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CloudEnvironment extends AbstractAuditingEntity implements Serializable {

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

  @Column(name = "status")
  private String status;

  @Column(name = "cloud")
  private String cloud;
  
  @Column(name = "vault_id")
  private String vaultId;
  
  @Column(name = "display_name")
  private String displayName;
  
  @Column(name = "role_arn")
  private String roleArn;
  
  @Column(name = "external_id")
  private String externalId;
  
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
  
  @Transient
  @JsonProperty
  private Integer totalBilling;
  
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

//  public Instant getCreatedOn() {
//    return this.createdOn;
//  }

//  public CloudEnvironment createdOn(Instant createdOn) {
//    this.setCreatedOn(createdOn);
//    return this;
//  }

//  public void setCreatedOn(Instant createdOn) {
//    this.createdOn = createdOn;
//  }

//  public Instant getUpdatedOn() {
//    return this.updatedOn;
//  }
//
//  public CloudEnvironment updatedOn(Instant updatedOn) {
//    this.setUpdatedOn(updatedOn);
//    return this;
//  }
//
//  public void setUpdatedOn(Instant updatedOn) {
//    this.updatedOn = updatedOn;
//  }

//  public String getUpdatedBy() {
//    return this.updatedBy;
//  }

  

//  public void setUpdatedBy(String updatedBy) {
//    this.updatedBy = updatedBy;
//  }
//
//  public String getCreatedBy() {
//    return this.createdBy;
//  }

 

//  public void setCreatedBy(String createdBy) {
//    this.createdBy = createdBy;
//  }

//  public Cloud getCloud() {
//    return this.cloud;
//  }
//
//  public void setCloud(Cloud cloud) {
//    this.cloud = cloud;
//  }

//  public CloudEnvironment cloud(Cloud cloud) {
//    this.setCloud(cloud);
//    return this;
//  }

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

	public Integer getTotalBilling() {
		return totalBilling;
	}

	public void setTotalBilling(Integer totalBilling) {
		this.totalBilling = totalBilling;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public String getVaultId() {
		return vaultId;
	}

	public void setVaultId(String vaultId) {
		this.vaultId = vaultId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getRoleArn() {
		return roleArn;
	}

	public void setRoleArn(String roleArn) {
		this.roleArn = roleArn;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

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
//	            ", orgId=" + getOrgId() +
	            ", status='" + getStatus() + "'" +
//	            ", createdOn='" + getCreatedOn() + "'" +
//	            ", updatedOn='" + getUpdatedOn() + "'" +
//	            ", updatedBy='" + getUpdatedBy() + "'" +
//	            ", createdBy='" + getCreatedBy() + "'" +
	            "}";
	}
}
