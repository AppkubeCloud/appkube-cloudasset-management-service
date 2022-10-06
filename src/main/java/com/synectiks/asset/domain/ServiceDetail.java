package com.synectiks.asset.domain;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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

  @Convert(converter = CustomeHashMapConverter.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata_json;
  
  @Convert(converter = CustomeHashMapConverter.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> view_json;
  
  @Convert(converter = CustomeHashMapConverter.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> sla_json;
  
}
