package com.synectiks.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.AssetServiceTag;

/**
 * Spring Data SQL repository for the AssetServiceTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetServiceTagRepository extends JpaRepository<AssetServiceTag, Long> {
	 
	@Query(value = "delete from asset_service_tag where id = :id", nativeQuery = true)
	public void deleteAssetTag(@Param("id") Long id);
}
