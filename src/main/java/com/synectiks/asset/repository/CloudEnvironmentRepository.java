package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.CloudEnvironment;

/**
 * Spring Data SQL repository for the CloudEnvironment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudEnvironmentRepository extends JpaRepository<CloudEnvironment, Long> {}
