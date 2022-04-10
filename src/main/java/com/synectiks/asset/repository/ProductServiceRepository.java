package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ProductService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductServiceRepository extends JpaRepository<ProductService, Long> {}
