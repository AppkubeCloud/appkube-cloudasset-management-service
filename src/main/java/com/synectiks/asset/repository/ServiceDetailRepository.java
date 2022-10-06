package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.ServiceDetail;

/**
 * Spring Data SQL repository for the ServiceDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Long> {
	 
}
