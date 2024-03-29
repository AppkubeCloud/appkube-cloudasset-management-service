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
 * A CloudElement.
 */
@Entity
@Table(name = "cloud_element")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudElement extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "instance_id")
	private String instanceId;

	@Column(name = "element_type")
	private String elementType;

	@Column(name = "arn")
	private String arn;

	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "cloud_entity", columnDefinition = "jsonb")
	private Map<String, Object> cloudEntity;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "hardware_location", columnDefinition = "jsonb")
	private Map<String, Object> hardwareLocation;
	
	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "hosted_services", columnDefinition = "jsonb")
	private Map<String, Object> hostedServices;
	
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
	
	@Column(name = "tag_status")
	private String tagStatus;
	
	@Column(name = "status")
	private String status;

	@ManyToOne
	private CloudEnvironment cloudEnvironment;

}
