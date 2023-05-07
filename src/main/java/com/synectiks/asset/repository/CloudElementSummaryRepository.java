package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.CloudElementSummary;

/**
 * Spring Data SQL repository for the CloudElementSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudElementSummaryRepository extends JpaRepository<CloudElementSummary, Long> {}
