package com.synectiks.asset.business.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A ServiceMetadata.
 */
@Entity
@Table(name = "ServiceMetadata")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceMetadata extends AbstractAuditingEntity implements Serializable {

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
	
	@Column(name = "service_type")
	private String serviceType; //(app/data)
	
	@Column(name = "service_category")
	private String serviceCategory; //(app[NodesJs / Golang], data[SQLDB, CacheDB, SearchDB , NOSQL DB , GRAPH DB , METRIC DB , LOG DB , Object Store , Github])

	@Column(name = "status")
	private String status;
	
}
