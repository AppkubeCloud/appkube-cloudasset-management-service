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
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.business.service.ModuleService;
import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.business.service.ServicesService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ModuleRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api")
public class ModuleController {

    private final Logger log = LoggerFactory.getLogger(ModuleController.class);

    private static final String ENTITY_NAME = "Module";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
	private CloudEnvironmentService cloudEnvironmentService;
    
    @Autowired
	private DepartmentService departmentService;

	@Autowired
	private ProductService productService;

    @Autowired
    private DeploymentEnvironmentService deploymentEnvironmentService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ServicesService servicesService;

    @Autowired
	private ServiceAllocationService departmentProductEnvService;
    
    /**
     * {@code POST  /modules} : Create a new module.
     *
     * @param module the module to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new module, or with status {@code 400 (Bad Request)} if the module has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modules")
    public ResponseEntity<Module> createModule(@Valid @RequestBody Module module) throws URISyntaxException {
        log.debug("REST request to save Module : {}", module);
        if (module.getId() != null) {
            throw new BadRequestAlertException("A new module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Module result = moduleService.save(module);
        return ResponseEntity
            .created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modules/:id} : Updates an existing module.
     *
     * @param id the id of the module to save.
     * @param module the module to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module,
     * or with status {@code 400 (Bad Request)} if the module is not valid,
     * or with status {@code 500 (Internal Server Error)} if the module couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modules/{id}")
    public ResponseEntity<Module> updateModule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Module module
    ) throws URISyntaxException {
        log.debug("REST request to update Module : {}, {}", id, module);
        if (module.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Module result = moduleService.save(module);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, module.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /modules/:id} : Partial updates given fields of an existing module, field will ignore if it is null
     *
     * @param id the id of the module to save.
     * @param module the module to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated module,
     * or with status {@code 400 (Bad Request)} if the module is not valid,
     * or with status {@code 404 (Not Found)} if the module is not found,
     * or with status {@code 500 (Internal Server Error)} if the module couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Module> partialUpdateModule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Module module
    ) throws URISyntaxException {
        log.debug("REST request to partial update Module partially : {}, {}", id, module);
        if (module.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, module.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Module> result = moduleService.partialUpdate(module);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, module.getId().toString())
        );
    }

    /**
     * {@code GET  /modules} : get all the modules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modules in body.
     */
    @GetMapping("/modules")
    public List<Module> getAllModules() {
        log.debug("REST request to get all Modules");
        return moduleService.findAll();
    }

    /**
     * {@code GET  /modules/:id} : get the "id" module.
     *
     * @param id the id of the module to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the module, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<Module> getModule(@PathVariable Long id) {
        log.debug("REST request to get Module : {}", id);
        Optional<Module> module = moduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(module);
    }

    /**
     * {@code DELETE  /modules/:id} : delete the "id" module.
     *
     * @param id the id of the module to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.debug("REST request to delete Module : {}", id);
        moduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
    /**
	 * {@code GET  /modules/search} : get all the modules on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of modules in body.
	 */
	@GetMapping("/modules/search")
	public List<Module> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all modules on given filters");
		return moduleService.search(filter);
	}
	
	
	/**
	 * {@code POST  /modules/add-service} : Add a service in a module.
	 *
	 * @param departmentProductEnv the departmentProductEnv to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException
	 */
	@PostMapping("/modules/add-service")
	public ResponseEntity<Module> addService(@RequestBody ServiceAllocation departmentProductEnv)
			throws URISyntaxException, IOException {
		log.debug("REST request to add a service in a module : {}", departmentProductEnv);

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
		
		if (departmentProductEnv.getServicesId() == null) {
			throw new BadRequestAlertException("Invalid service id", ENTITY_NAME, "idnull");
		}
		Optional<Services> osr = servicesService.findOne(departmentProductEnv.getServicesId());
		if (!osr.isPresent()) {
			throw new BadRequestAlertException("Service not found", ENTITY_NAME, "idnotfound");
		}
		
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.LANDING_ZONE, departmentProductEnv.getLandingZone());
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(departmentProductEnv.getDepartmentId()));
		filter.put(Constants.PRODUCT_ID, String.valueOf(departmentProductEnv.getProductId()));
		filter.put(Constants.DEPLOYMENT_ENVIRONMENT_ID, String.valueOf(departmentProductEnv.getDeploymentEnvironmentId()));
		filter.put(Constants.MODULE_ID, String.valueOf(departmentProductEnv.getModuleId()));
		filter.put(Constants.SERVICES_ID, String.valueOf(departmentProductEnv.getServicesId()));
		
		List<ServiceAllocation> dpeList = departmentProductEnvService.search(filter);
		if(dpeList.size() > 0) {
			throw new BadRequestAlertException("Service is already associated with module", ENTITY_NAME, "idexists");
		}
		filter.remove(Constants.SERVICES_ID);
		dpeList = departmentProductEnvService.search(filter);
		ServiceAllocation existingModule = null;
		for(ServiceAllocation dpe: dpeList) {
			if(dpe.getLandingZone().equals(departmentProductEnv.getLandingZone())
					&& dpe.getDepartmentId() == departmentProductEnv.getDepartmentId()
					&& dpe.getProductId() == departmentProductEnv.getProductId()
					&& dpe.getDeploymentEnvironmentId() == departmentProductEnv.getDeploymentEnvironmentId()
					&& dpe.getModuleId() == departmentProductEnv.getModuleId()
					&& dpe.getServicesId() == null) {
				existingModule = dpe;
				break;
			}
		}
		if(existingModule != null) {
			existingModule.setServicesId(departmentProductEnv.getServicesId());
			existingModule.setServiceType(osr.get().getType());
			existingModule.setServiceNature(osr.get().getServiceNature());
			departmentProductEnvService.save(existingModule);
		}else {
			departmentProductEnv.setServiceType(osr.get().getType());
			departmentProductEnv.setServiceNature(osr.get().getServiceNature());
			departmentProductEnvService.save(departmentProductEnv);
		}
		
		om = moduleService.findOne(departmentProductEnv.getModuleId());

		return ResponseEntity
				.created(new URI("/modules/add-service/" + om.get().getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, om.get().getId().toString()))
				.body(om.get());
	}
	

}
