package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceTag.
 */
@Entity
@Table(name = "service_tag_link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceTagLink implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Size(max = 5000)
  @Column(name = "description", length = 5000)
  private String description;
  
  @Column(name = "status")
  private String status;

  @Column(name = "created_on")
  private Instant createdOn;

  @Column(name = "updated_on")
  private Instant updatedOn;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "name")
  private String name;

  @Column(name = "hosting_type")
  private String hostingType;

  @ManyToOne
  private Services services;
  
  @ManyToOne
  private ServiceTag serviceTag;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Instant getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}
	
	public Instant getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Services getServices() {
		return services;
	}
	
	public void setServices(Services services) {
		this.services = services;
	}
	
	public ServiceTag getServiceTag() {
		return serviceTag;
	}
	
	public void setServiceTag(ServiceTag serviceTag) {
		this.serviceTag = serviceTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostingType() {
		return hostingType;
	}

	public void setHostingType(String hostingType) {
		this.hostingType = hostingType;
	}
	  
	  	

 
}
