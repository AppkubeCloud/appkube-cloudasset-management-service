package com.synectiks.asset.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synectiks.asset.business.domain.CloudEnvironment;

/**
 * Spring Data SQL repository for the CloudEnvironment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudEnvironmentRepository extends JpaRepository<CloudEnvironment, Long> {
	
	String ENV_COUNT_QUERY ="select cloud, count(*) as environments, "
			+ "sum(cast (summary_json -> 'TotalDiscoveredResources' as integer)) as assets, "
			+ "0 as alerts, 0 as total_billing "
			+ "from cloud_environment ce, cloud_element_summary ces, department dep, organization org  "
			+ "where ce.id = ces.cloud_environment_id "
			+ "and ce.department_id = dep.id and dep.organization_id = org.id "
			+ "and org.id = :orgId "
			+ "group by ce.cloud, ces.summary_json";
    
	@Query(value = ENV_COUNT_QUERY, nativeQuery = true)
	public List<Map<String, Object>> getCount(@Param("orgId") Long orgId);
	
	String ENV_CLOUD_WISE_COUNT_QUERY ="select cloud, count(*) as environments, "
			+ "sum(cast (summary_json -> 'TotalDiscoveredResources' as integer)) as assets, "
			+ "0 as alerts, 0 as total_billing "
			+ "from cloud_environment ce, cloud_element_summary ces, department dep, organization org "
			+ "where ce.id = ces.cloud_environment_id "
			+ "and ce.department_id = dep.id and dep.organization_id = org.id "
			+ "and upper(ce.cloud) = upper(:cloud) "
			+ "and org.id = :orgId "
			+ "group by ce.cloud, ces.summary_json";
    
	@Query(value = ENV_CLOUD_WISE_COUNT_QUERY, nativeQuery = true)
	public Map<String, Object> getCount(@Param("cloud") String cloud, @Param("orgId") Long orgId);
}
