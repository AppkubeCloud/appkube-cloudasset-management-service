package com.synectiks.asset.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.domain.MicroService;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.MicroServiceRepository;
import com.synectiks.asset.util.JsonAndObjectConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service Implementation for managing {@link MicroService}.
 */
@Service
@Transactional
public class MicroServiceService {

    private final Logger log = LoggerFactory.getLogger(MicroServiceService.class);

    @Autowired
    private MicroServiceRepository microServiceRepository;

	@Autowired
	private JsonAndObjectConverterUtil jsonAndObjectConverterUtil;
	
//	@Autowired
//	private CloudEnvironmentService cloudEnvironmentService;
//	
    public MicroService save(MicroService microService) {
        log.debug("Request to save MicroService: {}", microService);
        return microServiceRepository.save(microService);
    }

    public Optional<MicroService> partialUpdate(MicroService microService) {
        log.debug("Request to partially update MicroService : {}", microService);

        return microServiceRepository
            .findById(microService.getId())
            .map(existingMicroService -> {
                
                if (microService.getName() != null) {
                    existingMicroService.setName(microService.getName());
                }
                if (microService.getDepartment() != null) {
                    existingMicroService.setDepartment(microService.getDepartment());
                }
                if (microService.getProduct() != null) {
                    existingMicroService.setProduct(microService.getProduct());
                }
                if (microService.getEnvironment() != null) {
                    existingMicroService.setEnvironment(microService.getEnvironment());
                }
                if (microService.getServiceType() != null) {
                    existingMicroService.setServiceType(microService.getServiceType());
                }
                if (microService.getServiceTopology() != null) {
                    existingMicroService.setServiceTopology(microService.getServiceTopology());
                }
                if (microService.getBusinessLocation() != null) {
                    existingMicroService.setBusinessLocation(microService.getBusinessLocation());
                }
                if (microService.getSlaJson() != null) {
                    existingMicroService.setSlaJson(microService.getSlaJson());
                }
                if (microService.getCostJson() != null) {
                    existingMicroService.setCostJson(microService.getCostJson());
                }
                if (microService.getViewJson() != null) {
                    existingMicroService.setViewJson(microService.getViewJson());
                }
                if (microService.getConfigJson() != null) {
                    existingMicroService.setConfigJson(microService.getConfigJson());
                }
                if (microService.getComplianceJson() != null) {
                    existingMicroService.setComplianceJson(microService.getComplianceJson());
                }
				if (microService.getStatus() != null) {
                    existingMicroService.setStatus(microService.getStatus());
                }
                if (microService.getCreatedOn() != null) {
                    existingMicroService.setCreatedOn(microService.getCreatedOn());
                }
                if (microService.getUpdatedOn() != null) {
                    existingMicroService.setUpdatedOn(microService.getUpdatedOn());
                }
                if (microService.getUpdatedBy() != null) {
                    existingMicroService.setUpdatedBy(microService.getUpdatedBy());
                }
                if (microService.getCreatedBy() != null) {
                    existingMicroService.setCreatedBy(microService.getCreatedBy());
                }

                return existingMicroService;
            })
            .map(microServiceRepository::save);
    }

    @Transactional(readOnly = true)
    public List<MicroService> findAll() {
        log.debug("Request to get all MicroServices");
        return microServiceRepository.findAll(Sort.by(Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Optional<MicroService> findOne(Long id) {
        log.debug("Request to get MicroService: {}", id);
        return microServiceRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete MicroService : {}", id);
        microServiceRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public List<MicroService> search(Map<String, String> filter) throws IOException {
		log.debug("Request to get all MicroServices on given filters");

		MicroService microService = jsonAndObjectConverterUtil
				.convertSourceObjectToTarget(Constants.instantiateMapper(), filter, MicroService.class);

		// unset default value if createdOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.CREATED_ON))) {
			microService.setCreatedOn(null);
		}
		// unset default value if updatedOn is not coming in filter
		if (StringUtils.isBlank(filter.get(Constants.UPDATED_ON))) {
			microService.setUpdatedOn(null);
		}
		List<MicroService> list = microServiceRepository.findAll(Example.of(microService),
				Sort.by(Sort.Direction.DESC, "id"));

		return list;
	}
  //micro-services
	
  		public List<MicroService> getOrganizationMicroServices(Long orgId) {
  			log.debug("Request to get list of services  of an Organization");
  			return microServiceRepository.getOrganizationMicroServices(orgId);

  		}

  		public List<String> getOrganizationproductsMicroServices(Long orgId, String product) {

  			log.debug("Request to get list of services  of an Organization an Products");
  			return microServiceRepository.getOrganizationproductsMicroServices(orgId, product);
  		}

  		public List<String> getOrganizationEnvMicroServices(Long orgId, Long env) {
  			log.debug("Request to get list of services  of an Organization an Env");
  			return microServiceRepository.getOrganizationEnvMicroServices(orgId, env);
  		}

  		public List<MicroService> getOrganizationProductEnvMicroServices(Long orgId, String product, Long env) {
  			log.debug("Request to get list of services  of an Organization an product an Env");
  			return microServiceRepository.getOrganizationProductEnvMicroServices(orgId,product ,env);
  		}

  		public List<String> getOrganizationServiceTypeMicroServices(Long orgId, String serviceType) {
  			log.debug("Request to get list of services  of an Organization an serviceType");
  			return microServiceRepository.getOrganizationServiceTypeMicroServices(orgId,serviceType);
  		}
  		
  		public List<ObjectNode> getOrganizationDepartmentsMicroServices(Long orgId, Long depId) throws IOException {
  			log.debug("Request to get list of services of an department an Organization");
  			return microServiceRepository.getOrganizationDepartmentsMicroServices(orgId, depId);
  		}

  		public List<String> getOrganizationDepartmentsProductMicroServices(Long orgId, Long depId, String product) {
  			// TODO Auto-generated method stub
  			log.debug("Request to get list of services of an department an product an Organization");
  			return microServiceRepository.getOrganizationDepartmentsProductMicroServices(orgId, depId,product);
  		}

  		public List<String> getOrganizationDepartmentsEnvMicroServices(Long orgId, Long depId, Long env) {
  			// TODO Auto-generated method stub
  			log.debug("Request to get list of services of an department  an env an Organization");
  			return microServiceRepository.getOrganizationDepartmentsEnvMicroServices(orgId, depId,env);
  		}

  		public List<ObjectNode> getOrganizationDepartmentsProductEnvMicroServices(Long orgId, String product, Long depId,
  				Long env) {
  			// TODO Auto-generated method stub
  			log.debug("Request to get list of services of an department an product an env an Organization");
  			return microServiceRepository.getOrganizationDepartmentsProductEnvMicroServices(orgId,depId,product,env);
  		}

  		public List<String> getOrganizationDepartmentsServiceTypeMicroServices(Long orgId, Long depId, String serviceType) {
  			// TODO Auto-generated method stub
  			log.debug("Request to get list of services of an department an serviceType  an Organization");
  			return microServiceRepository.getOrganizationDepartmentsServiceTypeMicroServices(orgId,depId,serviceType);
  		}
  		
  		public List<ObjectNode> getOrganizationServiceNameMicroServices(Long orgId, String name) {
			// TODO Auto-generated method stub
			log.debug("Request to get list of services-cost of an serviceName  an Organization");
			return microServiceRepository.getOrganizationServiceNameMicroServices(orgId,name);
		}

		public List<ObjectNode> getOrganizationServiceNameDailyMicroServices(Long orgId, String serviceName) {
			log.debug("Request to get list of services-cost-daily of an serviceName  an Organization");
			return microServiceRepository.getOrganizationServiceNameDailyMicroServices(orgId,serviceName);
		}

		public List<ObjectNode> getOrganizationServiceNameWeeklyMicroServices(Long orgId, String serviceName) {
			// TODO Auto-generated method stub
			log.debug("Request to get list of services-cost-weekly of an serviceName  an Organization");
			return microServiceRepository.getOrganizationServiceNameWeeklyMicroServices(orgId,serviceName);
		}

		public List<ObjectNode> getOrganizationServiceNameMonthlyMicroServices(Long orgId, String serviceName) {
			// TODO Auto-generated method stub
			log.debug("Request to get list of services-cost-monthly of an serviceName  an Organization");
			return microServiceRepository.getOrganizationServiceNameMonthlyMicroServices(orgId,serviceName);
		}

		public List<ObjectNode> getOrganizationDepartmentsServiceNameMicroServices(Long orgId, Long depId,
				String serviceName) {
			log.debug("Request to get list of services-cost of an depId an serviceName  an Organization");
			return microServiceRepository.getOrganizationDepartmentsServiceNameMicroServices(orgId,depId,serviceName);
		}

		public List<ObjectNode> getOrganizationDepartmentsServiceNameDailyMicroServices(Long orgId, Long depId,
				String serviceName) {
			log.debug("Request to get list of services-cost-daily of an depId an serviceName  an Organization");
			return microServiceRepository.getOrganizationDepartmentsServiceNameDailyMicroServices(orgId,depId,serviceName);
		}

		public List<ObjectNode> getOrganizationDepartmentsServiceNameWeeklyMicroServices(Long orgId, Long depId,
				String serviceName) {
			log.debug("Request to get list of services-cost-weekly of an depId an serviceName  an Organization");
			return microServiceRepository.getOrganizationDepartmentsServiceNameWeeklyMicroServices(orgId,depId,serviceName);
		}

		public List<ObjectNode> getOrganizationDepartmentsServiceNameMonthlyMicroServices(Long orgId, Long depId,
				String serviceName) {
			log.debug("Request to get list of services-cost-monthly of an depId an serviceName  an Organization");
			return microServiceRepository.getOrganizationDepartmentsServiceNameMonthlyMicroServices(orgId,depId,serviceName);
		}

		public List<String> getOrganizationLandingZoneMicroServices(Long orgId, String landingZone) {
			log.debug("Request to get list of services of  an landingZone  an Organization");
			return microServiceRepository.getOrganizationLandingZoneMicroServices(orgId,landingZone);

		}

		public List<String> getOrganizationProductsMicroServices(Long orgId, String landingZone) {
			log.debug("Request to get list of services of  an landingZone  an Organization");
			return microServiceRepository.getOrganizationProductsMicroServices(orgId,landingZone);
		}

		public List<String> getOrganizationDepartmentsServicesMicroServices(Long orgId, Long depId,
				String landingZone) {
			log.debug("Request to get list of services of an department an landingZone  an Organization");
			return microServiceRepository.getOrganizationDepartmentsServicesMicroServices(orgId,depId,landingZone);
		}

		public List<String> getOrganizationDepartmentsProductsMicroServices(Long orgId, Long depId,
				String landingZone) {
			log.debug("Request to get list of services of an department an landingZone  an Organization");
			return microServiceRepository.getOrganizationDepartmentsProductsMicroServices(orgId,depId,landingZone);
		}
}
