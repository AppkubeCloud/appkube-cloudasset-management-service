package com.synectiks.asset.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.synectiks.asset.domain.CloudElement} entity.
 */
public class CloudElementDTO implements Serializable {

  private Long id;

  private String name;

  private String parentId;

  private String status;

  private Instant createdOn;

  private Instant updatedOn;

  private String updatedBy;

  private String createdBy;

  @Lob
  private byte[] viewJson;

  private String viewJsonContentType;
  private ProductEnclaveDTO productEnclaveId;

  private CloudEnvironmentDTO cloudEnvironmentId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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

  public byte[] getViewJson() {
    return viewJson;
  }

  public void setViewJson(byte[] viewJson) {
    this.viewJson = viewJson;
  }

  public String getViewJsonContentType() {
    return viewJsonContentType;
  }

  public void setViewJsonContentType(String viewJsonContentType) {
    this.viewJsonContentType = viewJsonContentType;
  }

  public ProductEnclaveDTO getProductEnclaveId() {
    return productEnclaveId;
  }

  public void setProductEnclaveId(ProductEnclaveDTO productEnclaveId) {
    this.productEnclaveId = productEnclaveId;
  }

  public CloudEnvironmentDTO getCloudEnvironmentId() {
    return cloudEnvironmentId;
  }

  public void setCloudEnvironmentId(CloudEnvironmentDTO cloudEnvironmentId) {
    this.cloudEnvironmentId = cloudEnvironmentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CloudElementDTO)) {
      return false;
    }

    CloudElementDTO cloudElementDTO = (CloudElementDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, cloudElementDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CloudElementDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", viewJson='" + getViewJson() + "'" +
            ", productEnclaveId=" + getProductEnclaveId() +
            ", cloudEnvironmentId=" + getCloudEnvironmentId() +
            "}";
    }
}
