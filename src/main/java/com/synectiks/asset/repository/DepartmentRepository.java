package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.Department;

/**
 * Spring Data SQL repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {}
