package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.DiscoveredAssets;

/**
 * Spring Data SQL repository for the DiscoveredAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscoveredAssetsRepository extends JpaRepository<DiscoveredAssets, Long> {}
