package com.synectiks.asset.repository;

import com.synectiks.asset.domain.CloudEnvironment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CloudEnvironment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudEnvironmentRepository extends JpaRepository<CloudEnvironment, Long> {}
