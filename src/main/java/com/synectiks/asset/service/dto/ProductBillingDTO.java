package com.synectiks.asset.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.synectiks.asset.domain.ProductBilling} entity.
 */
public class ProductBillingDTO implements Serializable {

  private Long id;

  private LocalDate periodFrom;

  private LocalDate periodTo;

  private LocalDate dueDate;

  private Double amount;

  @Size(max = 5000)
  private String description;

  private String status;

  private Instant createdOn;

  private Instant updatedOn;

  private String updatedBy;

  private String createdBy;

  private ProductDeploymentDTO productDeployment;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getPeriodFrom() {
    return periodFrom;
  }

  public void setPeriodFrom(LocalDate periodFrom) {
    this.periodFrom = periodFrom;
  }

  public LocalDate getPeriodTo() {
    return periodTo;
  }

  public void setPeriodTo(LocalDate periodTo) {
    this.periodTo = periodTo;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public ProductDeploymentDTO getProductDeployment() {
    return productDeployment;
  }

  public void setProductDeployment(ProductDeploymentDTO productDeployment) {
    this.productDeployment = productDeployment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductBillingDTO)) {
      return false;
    }

    ProductBillingDTO productBillingDTO = (ProductBillingDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, productBillingDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProductBillingDTO{" +
            "id=" + getId() +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", productDeployment=" + getProductDeployment() +
            "}";
    }
}
