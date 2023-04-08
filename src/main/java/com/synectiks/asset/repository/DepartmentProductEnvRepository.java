package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.DepartmentProductEnv;

/**
 * Spring Data SQL repository for the DepartmentProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentProductEnvRepository extends JpaRepository<DepartmentProductEnv, Long> {}
