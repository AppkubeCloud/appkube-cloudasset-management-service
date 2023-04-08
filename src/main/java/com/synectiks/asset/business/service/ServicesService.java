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

import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ServicesRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

@Service
public class ServicesService {
	
	private final Logger log = LoggerFactory.getLogger(ServicesService.class);

	@Autowired
    private ServicesRepository servicesRepository;

	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
    public Services save(Services services) {
        log.debug("Request to save Services : {}", services);
        return servicesRepository.save(services);
    }

    public Optional<Services> partialUpdate(Services services) {
        log.debug("Request to partially update Services : {}", services);

        return servicesRepository
            .findById(services.getId())
            .map(existingServices -> {
                if (services.getName() != null) {
                    existingServices.setName(services.getName());
                }
                if (services.getDescription() != null) {
                    existingServices.setDescription(services.getDescription());
                }
                if (services.getStatus() != null) {
                    existingServices.setStatus(services.getStatus());
                }
                if (services.getType() != null) {
                    existingServices.setType(services.getType());
                }
                if (services.getCreatedOn() != null) {
                    existingServices.setCreatedOn(services.getCreatedOn());
                }
                if (services.getUpdatedOn() != null) {
                    existingServices.setUpdatedOn(services.getUpdatedOn());
                }
                if (services.getUpdatedBy() != null) {
                    existingServices.setUpdatedBy(services.getUpdatedBy());
                }
                if (services.getCreatedBy() != null) {
                    existingServices.setCreatedBy(services.getCreatedBy());
                }

                return existingServices;
            })
            .map(servicesRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Services> findAll() {
        log.debug("Request to get all Services");
        return servicesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Services> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Services> search(Map<String, String> filter) throws IOException {
    	log.debug("Request to get all Services on given filters");

    	Services product = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, Services.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            product.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            product.setUpdatedOn(null);
        }
        return servicesRepository.findAll(Example.of(product), Sort.by(Sort.Direction.DESC, "name"));
    }
	
}
