package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.DeploymentEnvironment;

/**
 * Spring Data SQL repository for the DeploymentEnvironment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeploymentEnvironmentRepository extends JpaRepository<DeploymentEnvironment, Long> {}
