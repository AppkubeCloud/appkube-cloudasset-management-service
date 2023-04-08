package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.domain.CloudEnvironment;
import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.repository.CloudEnvironmentRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class CloudEnvironmentController {
	
	private final Logger log = LoggerFactory.getLogger(CloudEnvironmentController.class);
    
	private static final String ENTITY_NAME = "CloudEnvironment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CloudEnvironmentService cloudEnvironmentService;
    
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CloudEnvironmentRepository cloudEnvironmentRepository;

    

    /**
     * {@code POST  /cloud-environments} : Create a new cloudEnvironment.
     *
     * @param cloudEnvironment the cloudEnvironment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloudEnvironment, or with status {@code 400 (Bad Request)} if the cloudEnvironment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException 
     */
    @PostMapping("/cloud-environments")
    public ResponseEntity<CloudEnvironment> createCloudEnvironment(@Valid @RequestBody CloudEnvironment cloudEnvironment)
        throws URISyntaxException, IOException {
        log.debug("REST request to save CloudEnvironment : {}", cloudEnvironment);
        if (cloudEnvironment.getId() != null) {
            throw new BadRequestAlertException("A new cloudEnvironment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(cloudEnvironment.getDepartment() == null) {
        	log.warn("Department not found");
        	throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
        }else if(cloudEnvironment.getDepartment() != null && cloudEnvironment.getDepartment().getId() != null) {
			Optional<Department> od = departmentService.findOne(cloudEnvironment.getDepartment().getId());
			if(!od.isPresent()) {
				log.warn("Department not found in the system");
				throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
			}
		}
        
        String accountId = cloudEnvironment.getRoleArn().split(":")[4];
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId", accountId);
        List<CloudEnvironment> list = cloudEnvironmentService.search(filter);
         
        CloudEnvironment result = null;
        if(list == null || (list != null && list.size() == 0)) {
        	result = cloudEnvironmentService.save(cloudEnvironment);
        }else {
        	Set<String> set = new HashSet<>();
        	for(CloudEnvironment ce: list) {
        		set.add(ce.getRoleArn());
        	}
        	if(set.size() == 1) {
        		Optional<String> oArn = set.stream().findFirst();
        		if(oArn.isPresent() && oArn.get().equals(cloudEnvironment.getRoleArn())) {
        			result = cloudEnvironmentService.save(cloudEnvironment);
        		}else  {
        			throw new BadRequestAlertException("cloudEnvironment already discovered", ENTITY_NAME, "idexists");
        		} 
        	}else {
        		throw new BadRequestAlertException("cloudEnvironment already discovered", ENTITY_NAME, "idexists");
        	}
        }
        
        
        return ResponseEntity
            .created(new URI("/api/cloud-environments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cloud-environments/:id} : Updates an existing cloudEnvironment.
     *
     * @param id the id of the cloudEnvironment to save.
     * @param cloudEnvironment the cloudEnvironment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudEnvironment,
     * or with status {@code 400 (Bad Request)} if the cloudEnvironment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloudEnvironment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cloud-environments/{id}")
    public ResponseEntity<CloudEnvironment> updateCloudEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CloudEnvironment cloudEnvironment
    ) throws URISyntaxException {
        log.debug("REST request to update CloudEnvironment : {}, {}", id, cloudEnvironment);
        if (cloudEnvironment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cloudEnvironment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cloudEnvironmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CloudEnvironment result = cloudEnvironmentService.save(cloudEnvironment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cloudEnvironment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cloud-environments/:id} : Partial updates given fields of an existing cloudEnvironment, field will ignore if it is null
     *
     * @param id the id of the cloudEnvironment to save.
     * @param cloudEnvironment the cloudEnvironment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudEnvironment,
     * or with status {@code 400 (Bad Request)} if the cloudEnvironment is not valid,
     * or with status {@code 404 (Not Found)} if the cloudEnvironment is not found,
     * or with status {@code 500 (Internal Server Error)} if the cloudEnvironment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException 
     */
    @PatchMapping(value = "/cloud-environments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CloudEnvironment> partialUpdateCloudEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CloudEnvironment cloudEnvironment
    ) throws URISyntaxException, IOException {
        log.debug("REST request to partial update CloudEnvironment partially : {}, {}", id, cloudEnvironment);
        if (cloudEnvironment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cloudEnvironment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cloudEnvironmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if(cloudEnvironment.getDepartment() != null && cloudEnvironment.getDepartment().getId() != null) {
			Optional<Department> od = departmentService.findOne(cloudEnvironment.getDepartment().getId());
			if(!od.isPresent()) {
				throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
			}
		}
        
        String accountId = cloudEnvironment.getRoleArn().split(":")[4];
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId", accountId);
        List<CloudEnvironment> list = cloudEnvironmentService.search(filter);
         
        Optional<CloudEnvironment> result = null;
        if(list == null || (list != null && list.size() == 0)) {
        	result = cloudEnvironmentService.partialUpdate(cloudEnvironment);
        }else {
        	Set<String> set = new HashSet<>();
        	for(CloudEnvironment ce: list) {
        		set.add(ce.getRoleArn());
        	}
        	if(set.size() == 1) {
        		Optional<String> oArn = set.stream().findFirst();
        		if(oArn.isPresent() && oArn.get().equals(cloudEnvironment.getRoleArn())) {
        			result = cloudEnvironmentService.partialUpdate(cloudEnvironment);
        		}else  {
        			throw new BadRequestAlertException("cloudEnvironment already discovered", ENTITY_NAME, "idexists");
        		} 
        	}else {
        		throw new BadRequestAlertException("cloudEnvironment already discovered", ENTITY_NAME, "idexists");
        	}
        }

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cloudEnvironment.getId().toString())
        );
    }

    /**
     * {@code GET  /cloud-environments} : get all the cloudEnvironments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cloudEnvironments in body.
     */
    @GetMapping("/cloud-environments")
    public List<CloudEnvironment> getAllCloudEnvironments() {
        log.debug("REST request to get all CloudEnvironments");
        return cloudEnvironmentService.findAll();
    }

    /**
     * {@code GET  /cloud-environments/:id} : get the "id" cloudEnvironment.
     *
     * @param id the id of the cloudEnvironment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloudEnvironment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cloud-environments/{id}")
    public ResponseEntity<CloudEnvironment> getCloudEnvironment(@PathVariable Long id) {
        log.debug("REST request to get CloudEnvironment : {}", id);
        Optional<CloudEnvironment> cloudEnvironment = cloudEnvironmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cloudEnvironment);
    }

    /**
     * {@code DELETE  /cloud-environments/:id} : delete the "id" cloudEnvironment.
     *
     * @param id the id of the cloudEnvironment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cloud-environments/{id}")
    public ResponseEntity<Void> deleteCloudEnvironment(@PathVariable Long id) {
        log.debug("REST request to delete CloudEnvironment : {}", id);
        cloudEnvironmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
	/**
	 * {@code GET  /cloud-environments/search} : get all the cloud-environments on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of cloud-environments in body.
	 */
	@GetMapping("/cloud-environments/search")
	public List<CloudEnvironment> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all cloud-environments on given filters");
		return cloudEnvironmentService.search(filter);
	}
	
//	private static final Logger logger = LoggerFactory.getLogger(CloudEnvironmentController.class);
//	private static final String ENTITY_NAME = "CloudEnvironment";
//	
//	@Value("jhipster.clientApp.name")
//	private String applicationName;
//	
//	@Autowired
//	private CloudEnvironmentService cloudEnvironmentService;
//	
//	@Autowired
//	private ServiceDetailService serviceDetailService;
//	
//	@GetMapping("/cloud-environment/{id}")
//	public ResponseEntity<CloudEnvironment> getCloudEnvironment(@PathVariable Long id) {
//		logger.info("Request to get cloud-environment by Id: "+id);
//		Optional<CloudEnvironment> odp = cloudEnvironmentService.getCloudEnvironment(id);
//		return ResponseUtil.wrapOrNotFound(odp);
//	}
//	
//	@GetMapping("/cloud-environment")
//	public ResponseEntity<List<CloudEnvironment>> getAllCloudEnvironment() {
//		logger.info("Request to get all cloud-environments");
//		List<CloudEnvironment> list = cloudEnvironmentService.getAllCloudEnvironment();
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
//	
//	@DeleteMapping("/cloud-environment/{id}")
//	public ResponseEntity<Optional<CloudEnvironment>> deleteCloudEnvironment(@PathVariable Long id) {
//		logger.info("Request to delete cloud-environment by id: {}", id);
//		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.deleteCloudEnvironment(id);
//		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
//	}
//	
//	@PostMapping("/cloud-environment/aws")
//	public ResponseEntity<CloudEnvironment> createAwsCloudEnvironment(@RequestBody CloudEnvironment obj){
//		logger.info("Request to create new cloud-environment");
//		CloudEnvironment spa = cloudEnvironmentService.createAwsCloudEnvironment(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(spa);
//	}
//	
//	@PatchMapping("/cloud-environment/aws")
//	public ResponseEntity<Optional<CloudEnvironment>> partialUpdateAwsCloudEnvironment(@RequestBody CloudEnvironment obj){
//		logger.info("Request to partially update cloud-environment");
//		Optional<CloudEnvironment> oSpa = cloudEnvironmentService.partialUpdateAwsCloudEnvironment(obj);
//		return ResponseEntity.status(HttpStatus.OK).body(oSpa);
//	}
//	
//	@GetMapping("/cloud-environment/search")
//	public ResponseEntity<List<CloudEnvironment>> searchAllCloudEnvironmentNew(@RequestParam Map<String, String> obj){
//		logger.info("Request to search cloud-environment");
//		
//		List<CloudEnvironment> list = cloudEnvironmentService.searchAllCloudEnvironment(obj);
//		for(CloudEnvironment cloudEnvironment: list) {
//			Map<String, String> sdSearchMap = new HashMap<>();
//			sdSearchMap.put("associatedLandingZone", cloudEnvironment.getAccountId());
//			ServiceDetailReportResponse sdr = serviceDetailService.searchServiceDetailWithFilter(sdSearchMap);
//			int totalApp = 0 ;
//			int totalData = 0;
//			Map<String, String> productMap = new HashMap<String, String>();
//			Map<String, String> productEnclaveMap = new HashMap<String, String>();
//			Integer totalBilling = 0;
//			
//			for(ServiceDetail sdDetail: sdr.getServices()) {
//				if(Constants.SERVICE_BUSINESS_APP_SERVICE.equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) && !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
//					totalApp = totalApp + 1;
//				}else if(Constants.SERVICE_BUSINESS_DATA_SERVICE.equalsIgnoreCase((String)sdDetail.getMetadata_json().get("serviceType")) 
//						&& !StringUtils.isBlank((String)sdDetail.getMetadata_json().get("associatedProductEnclave"))) {
//					totalData = totalData + 1;
//				}
//				productMap.put((String)sdDetail.getMetadata_json().get("associatedProduct"), (String)sdDetail.getMetadata_json().get("associatedProduct"));
//				productEnclaveMap.put((String)sdDetail.getMetadata_json().get("associatedProductEnclave"), (String)sdDetail.getMetadata_json().get("associatedProductEnclave"));
//				
//				totalBilling = totalBilling + Integer.parseInt((String)((Map)sdDetail.getMetadata_json().get("stats")).get("totalCostSoFar"));
//			}
//			cloudEnvironment.setTotalAppServices(totalApp);
//			cloudEnvironment.setTotalDataServices(totalData);
//			cloudEnvironment.setTotalProducts(productMap.size());
//			cloudEnvironment.setTotalProductEnclave(productEnclaveMap.size());
//		}
//		
//		return ResponseEntity.status(HttpStatus.OK).body(list);
//	}
	
}
