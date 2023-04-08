package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class OrganizationService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;

    public Organization save(Organization organization) {
    	logger.debug("Request to save Organization : {}", organization);
        return organizationRepository.save(organization);
    }

    public Optional<Organization> partialUpdate(Organization organization) {
    	logger.debug("Request to partially update Organization : {}", organization);

        return organizationRepository
            .findById(organization.getId())
            .map(existingOrganization -> {
                if (organization.getName() != null) {
                    existingOrganization.setName(organization.getName());
                }
                if (organization.getStatus() != null) {
                    existingOrganization.setStatus(organization.getStatus());
                }
                if (organization.getCreatedOn() != null) {
                    existingOrganization.setCreatedOn(organization.getCreatedOn());
                }
                if (organization.getCreatedBy() != null) {
                    existingOrganization.setCreatedBy(organization.getCreatedBy());
                }
                if (organization.getUpdatedOn() != null) {
                    existingOrganization.setUpdatedOn(organization.getUpdatedOn());
                }
                if (organization.getUpdatedBy() != null) {
                    existingOrganization.setUpdatedBy(organization.getUpdatedBy());
                }

                return existingOrganization;
            })
            .map(organizationRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Organization> findAll() {
    	logger.debug("Request to get all Organizations");
        return organizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Organization> findOne(Long id) {
    	logger.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id);
    }

    public void delete(Long id) {
    	logger.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Organization> search(Map<String, String> filter) throws IOException {
    	logger.debug("Request to get all organizations on given filters");

        Organization organization = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, Organization.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            organization.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            organization.setUpdatedOn(null);
        }
        return organizationRepository.findAll(Example.of(organization), Sort.by(Sort.Direction.DESC, "name"));
    }
}
