package com.synectiks.asset.repository;

import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.domain.ServiceTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Services entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTagRepository extends JpaRepository<ServiceTag, Long> {}
