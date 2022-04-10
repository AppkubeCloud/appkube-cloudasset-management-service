package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ServiceBilling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServiceBilling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceBillingRepository extends JpaRepository<ServiceBilling, Long> {}
