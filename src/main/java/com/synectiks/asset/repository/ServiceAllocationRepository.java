package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.ServiceAllocation;

/**
 * Spring Data SQL repository for the ServiceAllocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAllocationRepository extends JpaRepository<ServiceAllocation, Long> {}
