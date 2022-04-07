package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ServiceProviderCloudAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServiceProviderCloudAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProviderCloudAccountRepository extends JpaRepository<ServiceProviderCloudAccount, Long> {}
