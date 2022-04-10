package com.synectiks.asset.repository;

import com.synectiks.asset.domain.DepartmentProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DepartmentProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentProductRepository extends JpaRepository<DepartmentProduct, Long> {}
