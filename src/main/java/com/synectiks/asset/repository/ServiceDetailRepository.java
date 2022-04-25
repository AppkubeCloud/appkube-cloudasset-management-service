package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ServiceDetail;
import com.synectiks.asset.domain.Services;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServiceDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Long> {}
