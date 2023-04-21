package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
import com.synectiks.asset.business.domain.ServiceAllocation;
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Module;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.business.service.ModuleService;
import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DeploymentEnvironmentRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class DeploymentEnvironmentController {
	
    private final Logger log = LoggerFactory.getLogger(DeploymentEnvironmentController.class);

    private static final String ENTITY_NAME = "DeploymentEnvironment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private DeploymentEnvironmentService deploymentEnvironmentService;

    @Autowired
    private DeploymentEnvironmentRepository deploymentEnvironmentRepository;

    @Autowired
	private CloudEnvironmentService cloudEnvironmentService;

    @Autowired
	private DepartmentService departmentService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ServiceAllocationService departmentProductEnvService;
	
    /**
     * {@code POST  /deployment-environments} : Create a new deploymentEnvironment.
     *
     * @param deploymentEnvironment the deploymentEnvironment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deploymentEnvironment, or with status {@code 400 (Bad Request)} if the deploymentEnvironment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deployment-environments")
    public ResponseEntity<DeploymentEnvironment> createDeploymentEnvironment(
        @Valid @RequestBody DeploymentEnvironment deploymentEnvironment
    ) throws URISyntaxException {
        log.debug("REST request to save DeploymentEnvironment : {}", deploymentEnvironment);
        if (deploymentEnvironment.getId() != null) {
            throw new BadRequestAlertException("A new deploymentEnvironment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeploymentEnvironment result = deploymentEnvironmentService.save(deploymentEnvironment);
        return ResponseEntity
            .created(new URI("/api/deployment-environments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deployment-environments/:id} : Updates an existing deploymentEnvironment.
     *
     * @param id the id of the deploymentEnvironment to save.
     * @param deploymentEnvironment the deploymentEnvironment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deploymentEnvironment,
     * or with status {@code 400 (Bad Request)} if the deploymentEnvironment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deploymentEnvironment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deployment-environments/{id}")
    public ResponseEntity<DeploymentEnvironment> updateDeploymentEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DeploymentEnvironment deploymentEnvironment
    ) throws URISyntaxException {
        log.debug("REST request to update DeploymentEnvironment : {}, {}", id, deploymentEnvironment);
        if (deploymentEnvironment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deploymentEnvironment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deploymentEnvironmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeploymentEnvironment result = deploymentEnvironmentService.save(deploymentEnvironment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deploymentEnvironment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deployment-environments/:id} : Partial updates given fields of an existing deploymentEnvironment, field will ignore if it is null
     *
     * @param id the id of the deploymentEnvironment to save.
     * @param deploymentEnvironment the deploymentEnvironment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deploymentEnvironment,
     * or with status {@code 400 (Bad Request)} if the deploymentEnvironment is not valid,
     * or with status {@code 404 (Not Found)} if the deploymentEnvironment is not found,
     * or with status {@code 500 (Internal Server Error)} if the deploymentEnvironment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deployment-environments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeploymentEnvironment> partialUpdateDeploymentEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DeploymentEnvironment deploymentEnvironment
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeploymentEnvironment partially : {}, {}", id, deploymentEnvironment);
        if (deploymentEnvironment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deploymentEnvironment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deploymentEnvironmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeploymentEnvironment> result = deploymentEnvironmentService.partialUpdate(deploymentEnvironment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deploymentEnvironment.getId().toString())
        );
    }

    /**
     * {@code GET  /deployment-environments} : get all the deploymentEnvironments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deploymentEnvironments in body.
     */
    @GetMapping("/deployment-environments")
    public List<DeploymentEnvironment> getAllDeploymentEnvironments() {
        log.debug("REST request to get all DeploymentEnvironments");
        return deploymentEnvironmentService.findAll();
    }

    /**
     * {@code GET  /deployment-environments/:id} : get the "id" deploymentEnvironment.
     *
     * @param id the id of the deploymentEnvironment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deploymentEnvironment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deployment-environments/{id}")
    public ResponseEntity<DeploymentEnvironment> getDeploymentEnvironment(@PathVariable Long id) {
        log.debug("REST request to get DeploymentEnvironment : {}", id);
        Optional<DeploymentEnvironment> deploymentEnvironment = deploymentEnvironmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deploymentEnvironment);
    }

    /**
     * {@code DELETE  /deployment-environments/:id} : delete the "id" deploymentEnvironment.
     *
     * @param id the id of the deploymentEnvironment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deployment-environments/{id}")
    public ResponseEntity<Void> deleteDeploymentEnvironment(@PathVariable Long id) {
        log.debug("REST request to delete DeploymentEnvironment : {}", id);
        deploymentEnvironmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
	 * {@code GET  /deployment-environments/search} : get all the deployment-environments on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of deployment-environments in body.
	 */
	@GetMapping("/deployment-environments/search")
	public List<DeploymentEnvironment> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all deployment-environments on given filters");
		return deploymentEnvironmentService.search(filter);
	}
    
	/**
	 * {@code POST  /deployment-environments/add-module} : Add a module in a deployment-environment.
	 *
	 * @param departmentProductEnv the departmentProductEnv to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException
	 */
	@PostMapping("/deployment-environments/add-module")
	public ResponseEntity<DeploymentEnvironment> addModule(@RequestBody ServiceAllocation departmentProductEnv)
			throws URISyntaxException, IOException {
		log.debug("REST request to add a module : {}", departmentProductEnv);

		// find out cloud_environment on department and landing_zone
		if (StringUtils.isBlank(departmentProductEnv.getLandingZone())) {
			throw new BadRequestAlertException("Invalid landing zone/cloud environment", ENTITY_NAME, "idnull");
		}
		Map<String, String> obj = new HashMap<>();
		obj.put("departmentId", String.valueOf(departmentProductEnv.getDepartmentId()));
		obj.put("accountId", departmentProductEnv.getLandingZone());
		List<CloudEnvironment> ceList = cloudEnvironmentService.search(obj);
		if (ceList == null || (ceList != null && ceList.size() == 0)) {
			throw new BadRequestAlertException("Landing zone/Cloud environment not found", ENTITY_NAME, "idnotfound");
		}

		if (departmentProductEnv.getDepartmentId() == null) {
			throw new BadRequestAlertException("Invalid department id", ENTITY_NAME, "idnull");
		}
		Optional<Department> od = departmentService.findOne(departmentProductEnv.getDepartmentId());
		if (!od.isPresent()) {
			throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
		}

		if (departmentProductEnv.getProductId() == null) {
			throw new BadRequestAlertException("Invalid product id", ENTITY_NAME, "idnull");
		}
		Optional<Product> opd = productService.findOne(departmentProductEnv.getProductId());
		if (!opd.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}

		if (departmentProductEnv.getDeploymentEnvironmentId() == null) {
			throw new BadRequestAlertException("Invalid deployment environment id", ENTITY_NAME, "idnull");
		}
		Optional<DeploymentEnvironment> ode = deploymentEnvironmentService.findOne(departmentProductEnv.getDeploymentEnvironmentId());
		if (!ode.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", ENTITY_NAME, "idnotfound");
		}
		
		if (departmentProductEnv.getModuleId() == null) {
			throw new BadRequestAlertException("Invalid module id", ENTITY_NAME, "idnull");
		}
		Optional<Module> om = moduleService.findOne(departmentProductEnv.getModuleId());
		if (!om.isPresent()) {
			throw new BadRequestAlertException("Module not found", ENTITY_NAME, "idnotfound");
		}
		
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.LANDING_ZONE, departmentProductEnv.getLandingZone());
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(departmentProductEnv.getDepartmentId()));
		filter.put(Constants.PRODUCT_ID, String.valueOf(departmentProductEnv.getProductId()));
		filter.put(Constants.DEPLOYMENT_ENVIRONMENT_ID, String.valueOf(departmentProductEnv.getDeploymentEnvironmentId()));
		filter.put(Constants.MODULE_ID, String.valueOf(departmentProductEnv.getModuleId()));
		List<ServiceAllocation> dpeList = departmentProductEnvService.search(filter);
		if(dpeList.size() > 0) {
			throw new BadRequestAlertException("Module is already associated with deployment environment", ENTITY_NAME, "idexists");
		}
		filter.remove(Constants.MODULE_ID);
		dpeList = departmentProductEnvService.search(filter);
		ServiceAllocation existingModule = null;
		for(ServiceAllocation dpe: dpeList) {
			if(dpe.getLandingZone().equals(departmentProductEnv.getLandingZone())
					&& dpe.getDepartmentId() == departmentProductEnv.getDepartmentId()
					&& dpe.getProductId() == departmentProductEnv.getProductId()
					&& dpe.getDeploymentEnvironmentId() == departmentProductEnv.getDeploymentEnvironmentId()
					&& dpe.getModuleId() == null) {
				existingModule = dpe;
				break;
			}
		}
		if(existingModule != null) {
			existingModule.setModuleId(departmentProductEnv.getModuleId());
			departmentProductEnvService.save(existingModule);
		}else {
			departmentProductEnvService.save(departmentProductEnv);
		}
		
		ode = deploymentEnvironmentService.findOne(departmentProductEnv.getDeploymentEnvironmentId());

		return ResponseEntity
				.created(new URI("products/add-module/" + ode.get().getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ode.get().getId().toString()))
				.body(ode.get());
	}
	


}
