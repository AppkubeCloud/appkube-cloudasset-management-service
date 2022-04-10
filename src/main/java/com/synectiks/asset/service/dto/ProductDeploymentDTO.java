package com.synectiks.asset.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.synectiks.asset.domain.ProductDeployment} entity.
 */
public class ProductDeploymentDTO implements Serializable {

  private Long id;

  private String environmentName;

  @Size(max = 5000)
  private String description;

  private String billingFrequency;

  private String status;

  private Instant createdOn;

  private Instant updatedOn;

  private String updatedBy;

  private String createdBy;

  private DepartmentProductDTO departmentProduct;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEnvironmentName() {
    return environmentName;
  }

  public void setEnvironmentName(String environmentName) {
    this.environmentName = environmentName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBillingFrequency() {
    return billingFrequency;
  }

  public void setBillingFrequency(String billingFrequency) {
    this.billingFrequency = billingFrequency;
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

  public DepartmentProductDTO getDepartmentProduct() {
    return departmentProduct;
  }

  public void setDepartmentProduct(DepartmentProductDTO departmentProduct) {
    this.departmentProduct = departmentProduct;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductDeploymentDTO)) {
      return false;
    }

    ProductDeploymentDTO productDeploymentDTO = (ProductDeploymentDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, productDeploymentDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProductDeploymentDTO{" +
            "id=" + getId() +
            ", environmentName='" + getEnvironmentName() + "'" +
            ", description='" + getDescription() + "'" +
            ", billingFrequency='" + getBillingFrequency() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", departmentProduct=" + getDepartmentProduct() +
            "}";
    }
}
