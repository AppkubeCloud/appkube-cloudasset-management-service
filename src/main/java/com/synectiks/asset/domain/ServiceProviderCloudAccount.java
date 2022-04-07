package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Synectiks Cloud account details
 */
@Entity
@Table(name = "service_provider_cloud_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceProviderCloudAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "cloud_type")
  private String cloudType;

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "access_key")
  private String accessKey;

  @Column(name = "secret_key")
  private String secretKey;

  @Column(name = "region")
  private String region;

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

  public Long getId() {
    return this.id;
  }

  public ServiceProviderCloudAccount id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCloudType() {
    return this.cloudType;
  }

  public ServiceProviderCloudAccount cloudType(String cloudType) {
    this.setCloudType(cloudType);
    return this;
  }

  public void setCloudType(String cloudType) {
    this.cloudType = cloudType;
  }

  public String getAccountId() {
    return this.accountId;
  }

  public ServiceProviderCloudAccount accountId(String accountId) {
    this.setAccountId(accountId);
    return this;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccessKey() {
    return this.accessKey;
  }

  public ServiceProviderCloudAccount accessKey(String accessKey) {
    this.setAccessKey(accessKey);
    return this;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return this.secretKey;
  }

  public ServiceProviderCloudAccount secretKey(String secretKey) {
    this.setSecretKey(secretKey);
    return this;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getRegion() {
    return this.region;
  }

  public ServiceProviderCloudAccount region(String region) {
    this.setRegion(region);
    return this;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getStatus() {
    return this.status;
  }

  public ServiceProviderCloudAccount status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public ServiceProviderCloudAccount createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public ServiceProviderCloudAccount updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public ServiceProviderCloudAccount updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public ServiceProviderCloudAccount createdBy(String createdBy) {
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
    if (!(o instanceof ServiceProviderCloudAccount)) {
      return false;
    }
    return id != null && id.equals(((ServiceProviderCloudAccount) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProviderCloudAccount{" +
            "id=" + getId() +
            ", cloudType='" + getCloudType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
