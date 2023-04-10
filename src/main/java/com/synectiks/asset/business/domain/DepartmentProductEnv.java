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

	@Column(name = "landing_zone")
	private String landingZone;
	
	@Column(name = "department_id")
	private Long departmentId;
	
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "deployment_environment_id")
	private Long deploymentEnvironmentId;

	@Column(name = "module_id")
	private Long moduleId;

	@Column(name = "services_id")
    private Long servicesId;

	@Column(name = "service_type")
	private String serviceType;

	@Column(name = "service_nature")
	private String serviceNature;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLandingZone() {
		return landingZone;
	}

	public void setLandingZone(String landingZone) {
		this.landingZone = landingZone;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getDeploymentEnvironmentId() {
		return deploymentEnvironmentId;
	}

	public void setDeploymentEnvironmentId(Long deploymentEnvironmentId) {
		this.deploymentEnvironmentId = deploymentEnvironmentId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getServicesId() {
		return servicesId;
	}

	public void setServicesId(Long servicesId) {
		this.servicesId = servicesId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentId, deploymentEnvironmentId, id, landingZone, moduleId, productId, serviceNature,
				serviceType, servicesId);
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
				&& Objects.equals(moduleId, other.moduleId) && Objects.equals(productId, other.productId)
				&& Objects.equals(serviceNature, other.serviceNature) && Objects.equals(serviceType, other.serviceType)
				&& Objects.equals(servicesId, other.servicesId);
	}

	@Override
	public String toString() {
		return "DepartmentProductEnv [id=" + id + ", landingZone=" + landingZone + ", departmentId=" + departmentId
				+ ", productId=" + productId + ", deploymentEnvironmentId=" + deploymentEnvironmentId + ", moduleId="
				+ moduleId + ", servicesId=" + servicesId + ", serviceType=" + serviceType + ", serviceNature="
				+ serviceNature + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + "]";
	}

	

	
	
}
