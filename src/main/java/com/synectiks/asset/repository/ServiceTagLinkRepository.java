package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.domain.ServiceTagLink;

/**
 * Spring Data SQL repository for the Services entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTagLinkRepository extends JpaRepository<ServiceTagLink, Long> {}
