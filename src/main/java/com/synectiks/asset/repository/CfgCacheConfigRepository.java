package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.CfgCacheConfig;

/**
 * Spring Data SQL repository for the CfgCacheConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CfgCacheConfigRepository extends JpaRepository<CfgCacheConfig, Long> {}
