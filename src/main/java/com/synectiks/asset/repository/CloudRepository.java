package com.synectiks.asset.repository;

import com.synectiks.asset.domain.Cloud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cloud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudRepository extends JpaRepository<Cloud, Long> {}
