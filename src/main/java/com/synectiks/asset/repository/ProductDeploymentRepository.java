package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ProductDeployment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductDeployment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDeploymentRepository extends JpaRepository<ProductDeployment, Long> {}
