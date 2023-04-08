package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Product;

/**
 * A ProductDeployment.
 */
@Entity
@Table(name = "product_deployment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductDeployment implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Size(max = 5000)
  @Column(name = "description", length = 5000)
  private String description;

  @Column(name = "billing_frequency")
  private String billingFrequency;

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
  private Department department;

  @ManyToOne
  private Product product;

  @ManyToOne
  private DeploymentEnvironment deploymentEnvironment;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ProductDeployment id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return this.description;
  }

  public ProductDeployment description(String description) {
    this.setDescription(description);
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBillingFrequency() {
    return this.billingFrequency;
  }

  public ProductDeployment billingFrequency(String billingFrequency) {
    this.setBillingFrequency(billingFrequency);
    return this;
  }

  public void setBillingFrequency(String billingFrequency) {
    this.billingFrequency = billingFrequency;
  }

  public String getStatus() {
    return this.status;
  }

  public ProductDeployment status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public ProductDeployment createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public ProductDeployment updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public ProductDeployment updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public ProductDeployment createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Department getDepartment() {
    return this.department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public ProductDeployment department(Department department) {
    this.setDepartment(department);
    return this;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public ProductDeployment product(Product product) {
    this.setProduct(product);
    return this;
  }

  public DeploymentEnvironment getDeploymentEnvironment() {
    return this.deploymentEnvironment;
  }

  public void setDeploymentEnvironment(DeploymentEnvironment deploymentEnvironment) {
    this.deploymentEnvironment = deploymentEnvironment;
  }

  public ProductDeployment deploymentEnvironment(DeploymentEnvironment deploymentEnvironment) {
    this.setDeploymentEnvironment(deploymentEnvironment);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductDeployment)) {
      return false;
    }
    return id != null && id.equals(((ProductDeployment) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProductDeployment{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", billingFrequency='" + getBillingFrequency() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
