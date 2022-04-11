package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductBilling.
 */
@Entity
@Table(name = "product_billing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductBilling implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "period_from")
  private LocalDate periodFrom;

  @Column(name = "period_to")
  private LocalDate periodTo;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "amount")
  private Double amount;

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
  
  @ManyToOne
  private Product product;
  
  @ManyToOne
  private Department department;
  
  @ManyToOne
  private DeploymentEnvironment deploymentEnvironment;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ProductBilling id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getPeriodFrom() {
    return this.periodFrom;
  }

  public ProductBilling periodFrom(LocalDate periodFrom) {
    this.setPeriodFrom(periodFrom);
    return this;
  }

  public void setPeriodFrom(LocalDate periodFrom) {
    this.periodFrom = periodFrom;
  }

  public LocalDate getPeriodTo() {
    return this.periodTo;
  }

  public ProductBilling periodTo(LocalDate periodTo) {
    this.setPeriodTo(periodTo);
    return this;
  }

  public void setPeriodTo(LocalDate periodTo) {
    this.periodTo = periodTo;
  }

  public LocalDate getDueDate() {
    return this.dueDate;
  }

  public ProductBilling dueDate(LocalDate dueDate) {
    this.setDueDate(dueDate);
    return this;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Double getAmount() {
    return this.amount;
  }

  public ProductBilling amount(Double amount) {
    this.setAmount(amount);
    return this;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return this.description;
  }

  public ProductBilling description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return this.status;
  }

  public ProductBilling status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public ProductBilling createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public ProductBilling updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public ProductBilling updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public ProductBilling createdBy(String createdBy) {
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
    if (!(o instanceof ProductBilling)) {
      return false;
    }
    return id != null && id.equals(((ProductBilling) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProductBilling{" +
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
            "}";
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public DeploymentEnvironment getDeploymentEnvironment() {
		return deploymentEnvironment;
	}

	public void setDeploymentEnvironment(DeploymentEnvironment deploymentEnvironment) {
		this.deploymentEnvironment = deploymentEnvironment;
	}
}
