package com.synectiks.asset.repository;

import java.util.List;

import com.synectiks.asset.business.domain.Catalogue;

public interface CatalogueRepositoryCustom {
	List<Catalogue> findCatalogue(String whereCondition);
}
