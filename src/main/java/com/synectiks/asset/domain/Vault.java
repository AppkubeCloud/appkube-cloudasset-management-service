package com.synectiks.asset.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Vault.
 */
@Entity
@Table(name = "vault")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vault implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "cloud_type")
  private String cloudType;

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "access_key")
  private String accessKey;

  @Column(name = "secret_key")
  private String secretKey;

  @Column(name = "region")
  private String region;

  
}
