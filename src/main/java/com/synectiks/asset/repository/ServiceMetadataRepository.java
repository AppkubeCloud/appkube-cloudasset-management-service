package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.ServiceMetadata;

/**
 * Spring Data SQL repository for the ServiceMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceMetadataRepository extends JpaRepository<ServiceMetadata, Long> {}
