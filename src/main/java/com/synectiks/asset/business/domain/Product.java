package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Size(max = 5000)
	@Column(name = "description", length = 5000)
	private String description;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "product")
	@JsonIgnoreProperties(value = { "product" }, allowSetters = true)
	private Set<Module> modules = new HashSet<>();

	@Transient
	@JsonProperty
	private List<DeploymentEnvironment> deploymentEnvironments;

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

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public List<DeploymentEnvironment> getDeploymentEnvironments() {
		return deploymentEnvironments;
	}

	public void setDeploymentEnvironments(List<DeploymentEnvironment> deploymentEnvironments) {
		this.deploymentEnvironments = deploymentEnvironments;
	}

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
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Product{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
				+ ", status='" + getStatus() + "'" + ", createdOn='" + getCreatedOn() + "'" + ", updatedOn='"
				+ getUpdatedOn() + "'" + ", updatedBy='" + getUpdatedBy() + "'" + ", createdBy='" + getCreatedBy() + "'"
				+ "}";
	}

}
