package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DepartmentProductEnv.
 */
@Entity
@Table(name = "department_product_env")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DepartmentProductEnv extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "department_id")
	private Long departmentId;

	@Column(name = "deployment_environment_id")
	private Long deploymentEnvironmentId;

	@Column(name = "landing_zone")
	private String landingZone;

	public Long getId() {
		return this.id;
	}

	public DepartmentProductEnv id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDeploymentEnvironmentId() {
		return deploymentEnvironmentId;
	}

	public void setDeploymentEnvironmentId(Long deploymentEnvironmentId) {
		this.deploymentEnvironmentId = deploymentEnvironmentId;
	}

	public String getLandingZone() {
		return landingZone;
	}

	public void setLandingZone(String landingZone) {
		this.landingZone = landingZone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentId, deploymentEnvironmentId, id, landingZone, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentProductEnv other = (DepartmentProductEnv) obj;
		return Objects.equals(departmentId, other.departmentId)
				&& Objects.equals(deploymentEnvironmentId, other.deploymentEnvironmentId)
				&& Objects.equals(id, other.id) && Objects.equals(landingZone, other.landingZone)
				&& Objects.equals(productId, other.productId);
	}

	@Override
	public String toString() {
		return "DepartmentProduct [id=" + id + ", productId=" + productId + ", departmentId=" + departmentId
				+ ", deploymentEnvironmentId=" + deploymentEnvironmentId + ", landingZone=" + landingZone
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + "]";
	}

}
