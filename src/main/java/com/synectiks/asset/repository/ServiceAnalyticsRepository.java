package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.ServiceAnalytics;

/**
 * Spring Data SQL repository for the ServiceAnalytics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAnalyticsRepository extends JpaRepository<ServiceAnalytics, Long> {}
