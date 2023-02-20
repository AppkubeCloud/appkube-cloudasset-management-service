package com.synectiks.asset.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.synectiks.asset.config.ApplicationProperties;
import com.synectiks.asset.domain.Organization;

@Service
public class OrganizationService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	
	@Autowired
	private ApplicationProperties appProps;
	
	@Autowired
    private RestTemplate restTemplate;
	
//	@Autowired
//    DepartmentService departmentService;
	
//	@Autowired
//    private OrganizationRepository organizationRepository;
//	
//	public Organization save(Organization organization) {
//		logger.debug("Saving organization: {}", organization);
//		return organizationRepository.save(organization);
//	}
//	
//	public Organization update(Organization organization) {
//		logger.debug("Updating organization: {}", organization);
//		return organizationRepository.save(organization);
//	}
//	
//	public Optional<Organization> partialUpdate(Organization organization) {
//		logger.debug("Partially updating organization: {}", organization);
//		return organizationRepository.findById(organization.getId())
//				.map(existingObj ->{
//					if(!StringUtils.isBlank(organization.getName())) {
//						existingObj.setName(organization.getName());
//					}
//					if(!StringUtils.isBlank(organization.getDescription())) {
//						existingObj.setDescription(organization.getDescription());
//					}
//					if(!StringUtils.isBlank(organization.getStatus())) {
//						existingObj.setStatus(organization.getStatus());
//					}
//					return existingObj;
//				})
//				.map(organizationRepository::save);
//
//	}
//	
//	public List<Organization> findAll(){
//		logger.debug("Get all organizations");
//		return organizationRepository.findAll();
//	}
//	
//	public Optional<Organization> findOne(Long id){
//		logger.debug("Get organization id: {}", id);
//		return organizationRepository.findById(id);
//	}
//	
//	public void delete(Long id){
//		logger.debug("Delete organization id: {}", id);
//		organizationRepository.deleteById(id);
//	}
	
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
