package com.synectiks.asset.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synectiks.asset.domain.ServiceDetail;

public class ServiceDetailRepositoryCustomImpl implements ServiceDetailRepositoryCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceDetailRepositoryCustomImpl.class);
			
	@PersistenceContext
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceDetail> findServiceDetails(String whereCondition){
		String QUERY = "SELECT * FROM service_detail ";
		if(!StringUtils.isBlank(whereCondition)) {
			QUERY = QUERY + " WHERE metadata_json @> '"+whereCondition+"'";
		}
		logger.debug("Jsonb query: {} ",QUERY);
		Query query = entityManager.createNativeQuery(QUERY, ServiceDetail.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	@org.springframework.data.jpa.repository.Query()
	public void updateViewJson(String id, String key) {
//		String QUERY = "UPDATE service_detail "
//				+ "SET view_json = jsonb_set(view_json, '{"+key+"}', ( "
//				+ "  SELECT COALESCE(jsonb_agg(element), '[]' '::' jsonb) "
//				+ "  FROM jsonb_array_elements(view_json -> 'performance') element "
//				+ "  WHERE element ->> 'id' <> '"+id+"'))";
		String QUERY = "update service_detail set view_json = jsonb_set(view_json,'{"+key+"}',jsonb_path_query_array(view_json,'$.performance[*]?(@.\"id\" = '"+id+"')'))";
		try {
			Query query = entityManager.createNativeQuery(QUERY);
			query.executeUpdate();
		}catch(Exception e) {
			logger.error("json b query exception : ", e);
		}
		
		
	}
}
