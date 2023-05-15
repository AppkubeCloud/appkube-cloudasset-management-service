package com.synectiks.asset.business.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
 * CloudEnvironment
 */
@Entity
@Table(name = "cloud_environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudEnvironment extends AbstractAuditingEntity implements Serializable {

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

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "status")
  private String status;

  @Column(name = "cloud")
  private String cloud;
  
//  @Column(name = "vault_id")
//  private String vaultId;
//  
  @Column(name = "display_name")
  private String displayName;
  
  @Column(name = "role_arn")
  private String roleArn;
  
  @Column(name = "external_id")
  private String externalId;
  
  @ManyToOne
  private Department department;
  
//  @Transient
//  @JsonProperty
//  private Integer totalProductEnclave;
//  
//  @Transient
//  @JsonProperty
//  private Integer totalProducts;
//  
//  @Transient
//  @JsonProperty
//  private Integer totalAppServices;
//  
//  @Transient
//  @JsonProperty
//  private Integer totalDataServices;
//  
//  @Transient
//  @JsonProperty
//  private Integer totalBilling;
  
  
}
