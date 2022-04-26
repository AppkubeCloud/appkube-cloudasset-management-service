package com.synectiks.asset.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
			QUERY = QUERY + " WHERE details @> '"+whereCondition+"'";
		}
		logger.debug("Jsonb query: {} ",QUERY);
		Query query = entityManager.createNativeQuery(QUERY, ServiceDetail.class);
		return query.getResultList();
	}
	
}
