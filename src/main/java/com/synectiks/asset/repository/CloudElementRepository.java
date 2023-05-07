package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.CloudElement;

/**
 * Spring Data SQL repository for the CloudElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudElementRepository extends JpaRepository<CloudElement, Long> {}
