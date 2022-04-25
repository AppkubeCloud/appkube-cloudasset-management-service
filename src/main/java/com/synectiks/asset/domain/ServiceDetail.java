package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
 * A ServiceDetail.
 */
@Entity
@Table(name = "service_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Size(max = 5000)
  @Column(name = "description", length = 5000)
  private String description;

  @Column(name = "associated_ou")
  private String associatedOU;
  
  @Column(name = "associated_dept")
  private String associatedDept;
  
  @Column(name = "associated_product")
  private String associatedProduct;
  
  @Column(name = "associated_env")
  private String associatedEnv;
  
  @Column(name = "associated_landing_zone")
  private String associatedLandingZone;
  
  @Column(name = "associated_product_enclave")
  private String associatedProductEnclave;
  
  @Column(name = "associated_cluster")
  private String associatedCluster;
  
  @Column(name = "service_nature")
  private String serviceNature; 
  
  @Column(name = "associated_common_service")
  private String associatedCommonService;
  
  @Column(name = "associated_business_service")
  private String associatedBusinessService;
  
  @Column(name = "service_type")
  private String serviceType; 
  
  @Column(name = "service_hosting_type")
  private String serviceHostingType;        
  
  @Column(name = "associated_cluster_namespace")
  private String associatedClusterNamespace;
  
  @Column(name = "associated_managed_cloud_service_location")
  private String associatedManagedCloudServiceLocation;
  
  @Column(name = "associated_cloud_element_id")
  private String associatedCloudElementId;    
  
  @Column(name = "associated_global_service_location")
  private String associatedGlobalServiceLocation;  
	
  
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
  
}
