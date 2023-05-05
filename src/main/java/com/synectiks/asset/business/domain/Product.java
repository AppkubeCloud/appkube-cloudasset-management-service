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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	@Column(name = "landing_zone")
	private String landingZone;
	
	@Column(name = "organization_id")
	private Long organizationId;
	
	@Column(name = "department_id")
	private Long departmentId;
	
	@Column(name = "deployment_environment_id")
	private Long deploymentEnvironmentId;
	
	@Column(name = "module_name")
	private String moduleName;
	
	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name = "service_category")
	private String serviceCategory; //(app[NodesJs / Golang], data[SQLDB, CacheDB])
	
	@Column(name = "service_type")
	private String serviceType; //(app/data)
	
	@Column(name = "service_nature")
	private String serviceNature; //(business/common)
	
	@Column(name = "discovered_asset_id")
	private Long discoveredAssetId;
	
	@Size(max = 500)
	@Column(name = "tag", length = 500)
	private String tag;
	
	@Column(name = "hosting_type")
	private String hostingType; // CLOUD/cluster/GLOBAL
	
	@Column(name = "hosting_url")
	private String hostingUrl; // cluster arn
	
	@Column(name = "hosting_namespace")
	private String hostingNamespace; // cluster namespace
	
	@Column(name = "service_location")
	private String serviceLocation; // global service/cloud service location
	
	@Column(name = "product_enclave")
	private String productEnclave; // VPC
	
	@Column(name = "product_enclave_id")
	private String productEnclaveId; // VPC ID
	
	@Column(name = "product_enclave_arn")
	private String productEnclaveArn;
	
	@Column(name = "cloud_element")
	private String cloudElement;
	
	@Column(name = "cloud_element_id")
	private String cloudElementId;
	
	@Column(name = "cloud_element_key")
	private String cloudElementKey;
	
	@Column(name = "service_detail_id")
	private Long serviceDetailId;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnoreProperties(value = { "product" }, allowSetters = true)
	private Set<Module> modules = new HashSet<>();

	@Transient
	@JsonProperty
	private List<DeploymentEnvironment> deploymentEnvironments;

	
	@Column(name = "organization_name")
	private String organizationName;
	
	@Column(name = "department_name")
	private String departmentName;
	
	@Column(name = "deployment_environment_name")
	private String deploymentEnvironmentName;
}
