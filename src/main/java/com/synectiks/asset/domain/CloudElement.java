package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Discovered assets of a cloud environment
 */
@Entity
@Table(name = "cloud_element")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CloudElement implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "parent_id")
  private String parentId;

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

  @Lob
  @Column(name = "view_json")
  private byte[] viewJson;

  @Column(name = "view_json_content_type")
  private String viewJsonContentType;

  @ManyToOne
  @JsonIgnoreProperties(value = { "cloudEnvironment" }, allowSetters = true)
  private ProductEnclave productEnclave;

  @ManyToOne
  @JsonIgnoreProperties(value = { "cloud", "department" }, allowSetters = true)
  private CloudEnvironment cloudEnvironment;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public CloudElement id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public CloudElement name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentId() {
    return this.parentId;
  }

  public CloudElement parentId(String parentId) {
    this.setParentId(parentId);
    return this;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getStatus() {
    return this.status;
  }

  public CloudElement status(String status) {
    this.setStatus(status);
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public CloudElement createdOn(Instant createdOn) {
    this.setCreatedOn(createdOn);
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public CloudElement updatedOn(Instant updatedOn) {
    this.setUpdatedOn(updatedOn);
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public CloudElement updatedBy(String updatedBy) {
    this.setUpdatedBy(updatedBy);
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public CloudElement createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public byte[] getViewJson() {
    return this.viewJson;
  }

  public CloudElement viewJson(byte[] viewJson) {
    this.setViewJson(viewJson);
    return this;
  }

  public void setViewJson(byte[] viewJson) {
    this.viewJson = viewJson;
  }

  public String getViewJsonContentType() {
    return this.viewJsonContentType;
  }

  public CloudElement viewJsonContentType(String viewJsonContentType) {
    this.viewJsonContentType = viewJsonContentType;
    return this;
  }

  public void setViewJsonContentType(String viewJsonContentType) {
    this.viewJsonContentType = viewJsonContentType;
  }

  public ProductEnclave getProductEnclave() {
    return this.productEnclave;
  }

  public void setProductEnclave(ProductEnclave productEnclave) {
    this.productEnclave = productEnclave;
  }

  public CloudElement productEnclave(ProductEnclave productEnclave) {
    this.setProductEnclave(productEnclave);
    return this;
  }

  public CloudEnvironment getCloudEnvironment() {
    return this.cloudEnvironment;
  }

  public void setCloudEnvironment(CloudEnvironment cloudEnvironment) {
    this.cloudEnvironment = cloudEnvironment;
  }

  public CloudElement cloudEnvironment(CloudEnvironment cloudEnvironment) {
    this.setCloudEnvironment(cloudEnvironment);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CloudElement)) {
      return false;
    }
    return id != null && id.equals(((CloudElement) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CloudElement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", viewJson='" + getViewJson() + "'" +
            ", viewJsonContentType='" + getViewJsonContentType() + "'" +
            "}";
    }
}
