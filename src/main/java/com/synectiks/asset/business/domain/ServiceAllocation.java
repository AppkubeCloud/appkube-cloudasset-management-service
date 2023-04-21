package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceAllocation.
 */
@Entity
@Table(name = "service_allocation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceAllocation extends AbstractAuditingEntity implements Serializable {

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

	@Column(name = "management_type")
	private String managementType;
	
	@Size(max = 500)
	@Column(name = "management_arn", length = 500)
	private String managementArn;
	
	@Column(name = "management_namespace")
	private String managementNamespace;
	
	@Column(name = "product_enclave")
	private String productEnclave;
	
	@Column(name = "product_enclave_id")
	private String productEnclaveId;
	
	@Size(max = 500)
	@Column(name = "product_enclave_arn", length = 500)
	private String productEnclaveArn;
	
	@Size(max = 20)
	@Column(name = "tag_status")
	private String tagStatus;

	@Size(max = 500)
	@Column(name = "tag", length = 500)
	private String tag;

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

	public String getManagementType() {
		return managementType;
	}

	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}

	public String getManagementArn() {
		return managementArn;
	}

	public void setManagementArn(String managementArn) {
		this.managementArn = managementArn;
	}

	public String getManagementNamespace() {
		return managementNamespace;
	}

	public void setManagementNamespace(String managementNamespace) {
		this.managementNamespace = managementNamespace;
	}

	public String getProductEnclave() {
		return productEnclave;
	}

	public void setProductEnclave(String productEnclave) {
		this.productEnclave = productEnclave;
	}

	public String getProductEnclaveId() {
		return productEnclaveId;
	}

	public void setProductEnclaveId(String productEnclaveId) {
		this.productEnclaveId = productEnclaveId;
	}

	public String getProductEnclaveArn() {
		return productEnclaveArn;
	}

	public void setProductEnclaveArn(String productEnclaveArn) {
		this.productEnclaveArn = productEnclaveArn;
	}

	public String getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentId, deploymentEnvironmentId, id, landingZone, managementArn, managementNamespace,
				managementType, moduleId, productEnclave, productEnclaveArn, productEnclaveId, productId, serviceNature,
				serviceType, servicesId, tag, tagStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceAllocation other = (ServiceAllocation) obj;
		return Objects.equals(departmentId, other.departmentId)
				&& Objects.equals(deploymentEnvironmentId, other.deploymentEnvironmentId)
				&& Objects.equals(id, other.id) && Objects.equals(landingZone, other.landingZone)
				&& Objects.equals(managementArn, other.managementArn)
				&& Objects.equals(managementNamespace, other.managementNamespace)
				&& Objects.equals(managementType, other.managementType) && Objects.equals(moduleId, other.moduleId)
				&& Objects.equals(productEnclave, other.productEnclave)
				&& Objects.equals(productEnclaveArn, other.productEnclaveArn)
				&& Objects.equals(productEnclaveId, other.productEnclaveId)
				&& Objects.equals(productId, other.productId) && Objects.equals(serviceNature, other.serviceNature)
				&& Objects.equals(serviceType, other.serviceType) && Objects.equals(servicesId, other.servicesId)
				&& Objects.equals(tag, other.tag) && Objects.equals(tagStatus, other.tagStatus);
	}

	@Override
	public String toString() {
		return "ServiceAllocation [id=" + id + ", landingZone=" + landingZone + ", departmentId=" + departmentId
				+ ", productId=" + productId + ", deploymentEnvironmentId=" + deploymentEnvironmentId + ", moduleId="
				+ moduleId + ", servicesId=" + servicesId + ", serviceType=" + serviceType + ", serviceNature="
				+ serviceNature + ", managementType=" + managementType + ", managementArn=" + managementArn
				+ ", managementNamespace=" + managementNamespace + ", productEnclave=" + productEnclave
				+ ", productEnclaveId=" + productEnclaveId + ", productEnclaveArn=" + productEnclaveArn + ", tagStatus="
				+ tagStatus + ", tag=" + tag + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	
	
}
