package com.synectiks.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.ServiceDetail;

/**
 * Spring Data SQL repository for the ServiceDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Long> {
	 
	String ORG_QUERY ="select distinct metadata_json -> 'associatedOU' as organization from service_detail ";
    @Query(value = ORG_QUERY, nativeQuery = true)
    List<String> getOrg();

    String ALL_LANDING_ZONE_QUERY ="select distinct metadata_json -> 'associatedLandingZone' as landingZone "
    		+ "from service_detail where metadata_json -> 'associatedLandingZone' is not null";
    @Query(value = ALL_LANDING_ZONE_QUERY, nativeQuery = true)
    List<String> getAllLandingZone();

    String LANDING_ZONE_QUERY ="select distinct metadata_json -> 'associatedLandingZone' as landingZone "
    		+ "from service_detail sd  "
    		+ "where metadata_json -> 'associatedProduct' = :product "
    		+ "and metadata_json -> 'associatedEnv' = :environment "
    		+ "and metadata_json -> 'associatedCommonService' = :module "
    		+ "and metadata_json -> 'name' = :service "
    		+ "union "
    		+ "select distinct metadata_json -> 'associatedLandingZone' as landingZone "
    		+ "from service_detail sd "
    		+ "where metadata_json -> 'associatedProduct' = :product "
    		+ "and metadata_json -> 'associatedEnv' = :environment "
    		+ "and metadata_json -> 'associatedBusinessService' = :module "
    		+ "and metadata_json -> 'name' = :service ";
    @Query(value = LANDING_ZONE_QUERY, nativeQuery = true)
    String getLandingZone(@Param("product") String product, 
    		@Param("environment") String environment,
    		@Param("module") String module, 
    		@Param("service") String service);

    String VPC_QUERY ="select distinct metadata_json -> 'associatedProductEnclave' as productEnclave "
    		+ "from service_detail where metadata_json -> 'associatedLandingZone' = :landingZone ";
    @Query(value = VPC_QUERY, nativeQuery = true)
    List<String> getVpc(@Param("landingZone") String landingZone);

    String HOSTING_TYPE_QUERY ="select distinct metadata_json -> 'serviceHostingType' as hostingType, "
    		+ "metadata_json -> 'associatedCluster' as associatedCluster "
    		+ "from service_detail where metadata_json -> 'associatedLandingZone' = :landingZone "
    		+ "and metadata_json -> 'associatedProductEnclave' = :productEnclave ";
    @Query(value = HOSTING_TYPE_QUERY, nativeQuery = true)
    String[] getHostingType(@Param("landingZone") String landingZone, @Param("productEnclave") String productEnclave);
    
}
