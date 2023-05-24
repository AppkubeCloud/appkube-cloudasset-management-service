package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.Catalogue;

/**
 * Spring Data SQL repository for the Catalogue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long>, CatalogueRepositoryCustom {}
