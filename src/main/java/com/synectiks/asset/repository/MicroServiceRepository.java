package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.MicroService;

/**
 * Spring Data SQL repository for the MicroService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicroServiceRepository extends JpaRepository<MicroService, Long> {}
