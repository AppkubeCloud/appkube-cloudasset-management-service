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

import com.synectiks.asset.business.domain.ServiceMetadata;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ServiceMetadataRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class ServiceMetadataService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceMetadataService.class);
	
	@Autowired
	private ServiceMetadataRepository serviceMetadataRepository;
	
	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;

    public ServiceMetadata save(ServiceMetadata organization) {
    	logger.debug("Request to save ServiceMetadata : {}", organization);
        return serviceMetadataRepository.save(organization);
    }

    public Optional<ServiceMetadata> partialUpdate(ServiceMetadata serviceMetadata) {
    	logger.debug("Request to partially update ServiceMetadata : {}", serviceMetadata);

        return serviceMetadataRepository
            .findById(serviceMetadata.getId())
            .map(existingOrganization -> {
                if (serviceMetadata.getName() != null) {
                    existingOrganization.setName(serviceMetadata.getName());
                }
                if (serviceMetadata.getDescription() != null) {
                    existingOrganization.setDescription(serviceMetadata.getDescription());
                }
                if (serviceMetadata.getServiceType() != null) {
                    existingOrganization.setServiceType(serviceMetadata.getServiceType());
                }
                if (serviceMetadata.getServiceCategory() != null) {
                    existingOrganization.setServiceCategory(serviceMetadata.getServiceCategory());
                }
                if (serviceMetadata.getStatus() != null) {
                    existingOrganization.setStatus(serviceMetadata.getStatus());
                }
                if (serviceMetadata.getCreatedOn() != null) {
                    existingOrganization.setCreatedOn(serviceMetadata.getCreatedOn());
                }
                if (serviceMetadata.getCreatedBy() != null) {
                    existingOrganization.setCreatedBy(serviceMetadata.getCreatedBy());
                }
                if (serviceMetadata.getUpdatedOn() != null) {
                    existingOrganization.setUpdatedOn(serviceMetadata.getUpdatedOn());
                }
                if (serviceMetadata.getUpdatedBy() != null) {
                    existingOrganization.setUpdatedBy(serviceMetadata.getUpdatedBy());
                }

                return existingOrganization;
            })
            .map(serviceMetadataRepository::save);
    }

    @Transactional(readOnly = true)
    public List<ServiceMetadata> findAll() throws IOException {
    	logger.debug("Request to get all ServiceMetadatas");
    	List<ServiceMetadata> list = serviceMetadataRepository.findAll();
    	return list;
    }

    @Transactional(readOnly = true)
    public Optional<ServiceMetadata> findOne(Long id) throws IOException {
    	logger.debug("Request to get ServiceMetadata : {}", id);
    	Optional<ServiceMetadata> o = serviceMetadataRepository.findById(id);
    	return o;
    }

    public void delete(Long id) {
    	logger.debug("Request to delete ServiceMetadata : {}", id);
        serviceMetadataRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ServiceMetadata> search(Map<String, String> filter) throws IOException {
    	logger.debug("Request to get all ServiceMetadatas on given filters");
    	
    	ServiceMetadata serviceMetadata = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, ServiceMetadata.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            serviceMetadata.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            serviceMetadata.setUpdatedOn(null);
        }
        List<ServiceMetadata> list = serviceMetadataRepository.findAll(Example.of(serviceMetadata), Sort.by(Sort.Direction.DESC, "id"));
        
    	return list;
    }
}
