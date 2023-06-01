package com.synectiks.asset.business.domain;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.synectiks.asset.business.service.CustomeHashMapConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A MicroService.
 */
@Entity
@Table(name = "micro_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroService extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "department")
	private String departmentName;

	@Column(name = "product")
	private String product;

	@Column(name = "environment")
	private String environment;

	@Column(name = "service_type")
	private String serviceType;

	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "service_topology", columnDefinition = "jsonb")
	private Map<String, Object> serviceTopology;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "business_location", columnDefinition = "jsonb")
	private Map<String, Object> businessLocation;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "sla_json", columnDefinition = "jsonb")
	private Map<String, Object> slaJson;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "cost_json", columnDefinition = "jsonb")
	private Map<String, Object> costJson;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "view_json", columnDefinition = "jsonb")
	private Map<String, Object> viewJson;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "config_json", columnDefinition = "jsonb")
	private Map<String, Object> configJson;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "compliance_json", columnDefinition = "jsonb")
	private Map<String, Object> complianceJson;
	
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	private Department department;
	
	@ManyToOne
	private DeploymentEnvironment deploymentEnvironment;
	

}
