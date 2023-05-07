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

	@Column(name = "element_id")
	private String elementId;

	@Column(name = "element_type")
	private String elementType;

	@Column(name = "arn")
	private String arn;

	@Column(name = "tag_status")
	private String tagStatus;

	@Convert(converter = CustomeHashMapConverter.class)
	@Column(name = "element_json", columnDefinition = "jsonb")
	private Map<String, Object> elementJson;

	@Column(name = "status")
	private String status;

	@ManyToOne
	private CloudEnvironment cloudEnvironment;

}
