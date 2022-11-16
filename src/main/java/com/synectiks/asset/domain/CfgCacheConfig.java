package com.synectiks.asset.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CfgCacheConfig.
 */
@Entity
@Table(name = "cfg_cache_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CfgCacheConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "dirty_flag")
  private Boolean dirtyFlag;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean isDirtyFlag() {
		return dirtyFlag;
	}
	
	public void setDirtyFlag(Boolean dirtyFlag) {
		this.dirtyFlag = dirtyFlag;
	}
}
