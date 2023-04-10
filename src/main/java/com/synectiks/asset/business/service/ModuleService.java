package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synectiks.asset.business.domain.Module;
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ModuleRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;

/**
 * Service Implementation for managing {@link Module}.
 */
@Service
public class ModuleService  {

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
    
    @Autowired
	private DepartmentProductEnvService departmentProductEnvService;

    
    public Module save(Module module) {
        log.debug("Request to save Module : {}", module);
        Module m = moduleRepository.save(module);
        if (m != null) {
			Map<String, String> filter = new HashMap<>();
			filter.put(Constants.MODULE_ID, String.valueOf(m.getId()));
			try {
				List<Services> servicesList = departmentProductEnvService.getServices(filter);
				m.setAppServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.APP_SERVICES)).collect(Collectors.toList()));
				m.setDataServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.DATA_SERVICES)).collect(Collectors.toList()));
			} catch (IOException e) {
				log.error("Error in getting services: ", e.getMessage());
			}
		}
        return m;
    }

    public Optional<Module> partialUpdate(Module module) {
        log.debug("Request to partially update Module : {}", module);

        return moduleRepository
            .findById(module.getId())
            .map(existingModule -> {
                if (module.getName() != null) {
                    existingModule.setName(module.getName());
                }
                if (module.getStatus() != null) {
                    existingModule.setStatus(module.getStatus());
                }
                if (module.getDescription() != null) {
                    existingModule.setDescription(module.getDescription());
                }
                if (module.getCreatedOn() != null) {
                    existingModule.setCreatedOn(module.getCreatedOn());
                }
                if (module.getCreatedBy() != null) {
                    existingModule.setCreatedBy(module.getCreatedBy());
                }
                if (module.getUpdatedOn() != null) {
                    existingModule.setUpdatedOn(module.getUpdatedOn());
                }
                if (module.getUpdatedBy() != null) {
                    existingModule.setUpdatedBy(module.getUpdatedBy());
                }
                
                Map<String, String> filter = new HashMap<>();
    			filter.put(Constants.MODULE_ID, String.valueOf(existingModule.getId()));
    			try {
    				List<Services> servicesList = departmentProductEnvService.getServices(filter);
    				existingModule.setAppServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.APP_SERVICES)).collect(Collectors.toList()));
    				existingModule.setDataServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.DATA_SERVICES)).collect(Collectors.toList()));
    			} catch (IOException e) {
    				log.error("Error in getting services: ", e.getMessage());
    			}
    			
                return existingModule;
            })
            .map(moduleRepository::save);
    }

    @Transactional(readOnly = true)
    public List<Module> findAll() {
        log.debug("Request to get all Modules");
        List<Module> list = moduleRepository.findAll();
        Map<String, String> filter = new HashMap<>();
        for(Module m: list) {
        	filter.clear();
    		filter.put(Constants.MODULE_ID, String.valueOf(m.getId()));
    		try {
    			List<Services> servicesList = departmentProductEnvService.getServices(filter);
    			m.setAppServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.APP_SERVICES)).collect(Collectors.toList()));
    			m.setDataServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.DATA_SERVICES)).collect(Collectors.toList()));
    		} catch (IOException e) {
    			log.error("Error in getting services: ", e.getMessage());
    		}
        }
        return list;
    }

    @Transactional(readOnly = true)
    public Optional<Module> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        Optional<Module> om = moduleRepository.findById(id);
        if(om.isPresent()) {
        	Map<String, String> filter = new HashMap<>();
			filter.put(Constants.MODULE_ID, String.valueOf(om.get().getId()));
			try {
				List<Services> servicesList = departmentProductEnvService.getServices(filter);
				om.get().setAppServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.APP_SERVICES)).collect(Collectors.toList()));
				om.get().setDataServices(servicesList.stream().filter(sd -> sd.getType().equalsIgnoreCase(Constants.DATA_SERVICES)).collect(Collectors.toList()));
			} catch (IOException e) {
				log.error("Error in getting services: ", e.getMessage());
			}
        }
        return om;
    }

    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Module> search(Map<String, String> filter) throws IOException {
    	log.debug("Request to get all modules on given filters");
    	String landingZone = null;
    	if (!StringUtils.isBlank(filter.get(Constants.LANDING_ZONE))) {
    		landingZone = filter.get(Constants.LANDING_ZONE);
			filter.remove(Constants.LANDING_ZONE);
		}
    	
    	Module module = jsonAndObjectConverterUtil.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, Module.class);

        //unset default value if createdOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.CREATED_ON))){
            module.setCreatedOn(null);
        }
        //unset default value if updatedOn is not coming in filter
        if(StringUtils.isBlank(filter.get(Constants.UPDATED_ON))){
            module.setUpdatedOn(null);
        }
        List<Module> list = moduleRepository.findAll(Example.of(module), Sort.by(Sort.Direction.DESC, "name"));
        
    	return list;
    }
}
