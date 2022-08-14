package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.AccountServices;

/**
 * Spring Data SQL repository for the AccountServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountServicesRepository extends JpaRepository<AccountServices, Long> {
	 
}
