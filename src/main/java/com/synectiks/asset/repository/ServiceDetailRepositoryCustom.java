package com.synectiks.asset.repository;

import java.util.List;

import com.synectiks.asset.domain.ServiceDetail;

public interface ServiceDetailRepositoryCustom {
	List<ServiceDetail> findServiceDetails(String whereCondition);
}
