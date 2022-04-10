package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ProductBilling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductBilling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductBillingRepository extends JpaRepository<ProductBilling, Long> {}
