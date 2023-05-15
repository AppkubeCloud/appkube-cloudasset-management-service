package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DeploymentEnvironment.
 */
@Entity
@Table(name = "deployment_environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeploymentEnvironment extends AbstractAuditingEntity implements Serializable {

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

//	@Transient
//	@JsonProperty
//	private List<String> modules;

//	public Long getId() {
//		return this.id;
//	}
//
//	public DeploymentEnvironment id(Long id) {
//		this.setId(id);
//		return this;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

//	public String getName() {
//		return this.name;
//	}
//
//	public DeploymentEnvironment name(String name) {
//		this.setName(name);
//		return this;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public String getDescription() {
//		return this.description;
//	}
//
//	public DeploymentEnvironment description(String description) {
//		this.setDescription(description);
//		return this;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}

//	public String getStatus() {
//		return this.status;
//	}
//
//	public DeploymentEnvironment status(String status) {
//		this.setStatus(status);
//		return this;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) {
//			return true;
//		}
//		if (!(o instanceof DeploymentEnvironment)) {
//			return false;
//		}
//		return id != null && id.equals(((DeploymentEnvironment) o).id);
//	}

//	@Override
//	public int hashCode() {
//		// see
//		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
//		return getClass().hashCode();
//	}
//
//	// prettier-ignore
//	@Override
//	public String toString() {
//		return "DeploymentEnvironment{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='"
//				+ getDescription() + "'" + ", status='" + getStatus() + "'" + ", createdOn='" + getCreatedOn() + "'"
//				+ ", updatedOn='" + getUpdatedOn() + "'" + ", updatedBy='" + getUpdatedBy() + "'" + ", createdBy='"
//				+ getCreatedBy() + "'" + "}";
//	}

//	public List<String> getModules() {
//		return modules;
//	}
//
//	public void setModules(List<String> modules) {
//		this.modules = modules;
//	}

//	public List<Services> getServiceList() {
//		return serviceList;
//	}
//
//	public void setServiceList(List<Services> serviceList) {
//		this.serviceList = serviceList;
//	}
}
