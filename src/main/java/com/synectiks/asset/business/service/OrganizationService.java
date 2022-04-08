package com.synectiks.asset.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.synectiks.asset.config.ApplicationProperties;
import com.synectiks.asset.domain.Department;
import com.synectiks.asset.domain.Organization;

@Service
public class OrganizationService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	
	@Autowired
	ApplicationProperties appProps;
	
	@Autowired
    RestTemplate restTemplate;
	
//	@Autowired
//    DepartmentService departmentService;
	
	public Organization getOrgByUserName(String userName) {
        String secSrvUrl = appProps.getSecurityServiceUrl();
        String url = secSrvUrl+"/security/organization/getOrganizationByUserName?userName="+userName;
        Organization org = this.restTemplate.getForObject(url, Organization.class);
//        getDepartment(org);
        return org;
    }

	
	
	public Organization getOrgById(Long id) {
        String secSrvUrl = appProps.getSecurityServiceUrl();
        String url = secSrvUrl+"/security/organization/getOrganization/"+id;
        Organization org = this.restTemplate.getForObject(url, Organization.class);
//        getDepartment(org);
		
        return org;
    }
	
//	private void getDepartment(Organization org) {
//		Map<String, String> obj = new HashMap<>();
//        obj.put("orgId", String.valueOf(org.getId()));
//        List<Department> departmentList = departmentService.searchAllDepartment(obj);
//        org.setDepartmentList(departmentList);
//	}
}
