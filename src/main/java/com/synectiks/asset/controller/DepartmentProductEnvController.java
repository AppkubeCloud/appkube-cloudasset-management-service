package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import com.synectiks.asset.business.domain.DepartmentProductEnv;
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.DepartmentProductEnvService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.business.service.ServicesService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DepartmentProductEnvRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class DepartmentProductEnvController {

	private final Logger log = LoggerFactory.getLogger(DepartmentProductEnvController.class);
	private static final String ENTITY_NAME = "DepartmentProductEnv";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	private DepartmentProductEnvService departmentProductEnvService;

	@Autowired
	private DeploymentEnvironmentService deploymentEnvironmentService;

	@Autowired
	private ServicesService servicesService;

	
	@Autowired
	private DepartmentProductEnvRepository departmentProductEnvRepository;

	/**
	 * {@code POST  /department-product-env} : Create a new departmentProductEnv.
	 *
	 * @param departmentProductEnv the departmentProductEnv to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException 
	 */
	@PostMapping("/department-product-env")
	public ResponseEntity<DepartmentProductEnv> createDepartmentEnvProduct(@RequestBody DepartmentProductEnv departmentProductEnv)
			throws URISyntaxException, IOException {
		log.debug("REST request to save DepartmentProductEnv : {}", departmentProductEnv);
		if (departmentProductEnv.getId() != null) {
			throw new BadRequestAlertException("A new departmentProductEnv cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		
		log.debug("validating department");
		if (departmentProductEnv.getDepartmentId() == null) {
			throw new BadRequestAlertException("Invalid department id", ENTITY_NAME, "idnull");
		}
		Optional<Department> od = departmentService.findOne(departmentProductEnv.getDepartmentId());
		if (!od.isPresent()) {
			throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
		}

		log.debug("validating product");
		if (departmentProductEnv.getProductId() == null) {
			throw new BadRequestAlertException("Invalid product id", ENTITY_NAME, "idnull");
		}
		Optional<Product> op = productService.findOne(departmentProductEnv.getProductId());
		if (!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating landing zone");
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(departmentProductEnv.getDepartmentId()));
		filter.put(Constants.ACCOUNT_ID, departmentProductEnv.getLandingZone());
		List<CloudEnvironment> ceList = cloudEnvironmentService.search(filter);
		if(ceList == null || ( ceList != null && ceList.size() == 0)) {
			throw new BadRequestAlertException("Landing zone(Account id) not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating deployment environment");
		if (departmentProductEnv.getDeploymentEnvironmentId() == null) {
			throw new BadRequestAlertException("Invalid deployment environment id", ENTITY_NAME, "idnull");
		}
		Optional<DeploymentEnvironment> de = deploymentEnvironmentService.findOne(departmentProductEnv.getDeploymentEnvironmentId());
		if (!de.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating service");
		if (departmentProductEnv.getServicesId() == null) {
			throw new BadRequestAlertException("Invalid service id", ENTITY_NAME, "idnull");
		}
		Optional<Services> oss = servicesService.findOne(departmentProductEnv.getServicesId());
		if (!oss.isPresent()) {
			throw new BadRequestAlertException("Service not found", ENTITY_NAME, "idnotfound");
		}
		departmentProductEnv.setServiceType(oss.get().getType());
		departmentProductEnv.setServiceNature(oss.get().getServiceNature());
		
		
		DepartmentProductEnv result = departmentProductEnvService.save(departmentProductEnv);
		
		return ResponseEntity
				.created(new URI("/api/department-product-env/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /department-product-env/:id} : Updates an existing
	 * departmentProductEnv.
	 *
	 * @param id                the id of the departmentProductEnv to save.
	 * @param departmentProductEnv the departmentProductEnv to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         departmentProductEnv couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/department-product-env/{id}")
	public ResponseEntity<DepartmentProductEnv> updateDepartmentProductEnv(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody DepartmentProductEnv departmentProductEnv) throws URISyntaxException {
		log.debug("REST request to update DepartmentProductEnv : {}, {}", id, departmentProductEnv);
		if (departmentProductEnv.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, departmentProductEnv.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!departmentProductEnvRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
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
		
		Optional<Product> op = productService.findOne(departmentProductEnv.getProductId());
		if (!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}

		
		DepartmentProductEnv result = departmentProductEnvService.save(departmentProductEnv);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				departmentProductEnv.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /department-product-env/:id} : Partial updates given fields of an
	 * existing departmentProductEnv, field will ignore if it is null
	 *
	 * @param id                the id of the departmentProductEnv to save.
	 * @param departmentProductEnv the departmentProductEnv to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv is not valid, or
	 *         with status {@code 404 (Not Found)} if the departmentProductEnv is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         departmentProductEnv couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException 
	 */
	@PatchMapping(value = "/department-product-env/{id}", consumes = { "application/json",
			"application/merge-patch+json" })
	public ResponseEntity<DepartmentProductEnv> partialUpdateDepartmentProductEnv(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody DepartmentProductEnv departmentProductEnv) throws URISyntaxException, IOException {
		log.debug("REST request to partial update DepartmentProductEnv partially : {}, {}", id, departmentProductEnv);
		if (departmentProductEnv.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, departmentProductEnv.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!departmentProductEnvRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
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
		Optional<Product> op = productService.findOne(departmentProductEnv.getProductId());
		if (!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}
		
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(departmentProductEnv.getDepartmentId()));
		filter.put(Constants.ACCOUNT_ID, departmentProductEnv.getLandingZone());
		List<CloudEnvironment> ceList = cloudEnvironmentService.search(filter);
		if(ceList == null || ( ceList != null && ceList.size() == 0)) {
			throw new BadRequestAlertException("Landing zone(Account id) not found", ENTITY_NAME, "idnotfound");
		}

		if (departmentProductEnv.getServicesId() == null) {
			throw new BadRequestAlertException("Invalid service id", ENTITY_NAME, "idnull");
		}
		Optional<Services> oss = servicesService.findOne(departmentProductEnv.getServicesId());
		if (!oss.isPresent()) {
			throw new BadRequestAlertException("Service not found", ENTITY_NAME, "idnotfound");
		}
		
		Optional<DepartmentProductEnv> result = departmentProductEnvService.partialUpdate(departmentProductEnv);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false,
				ENTITY_NAME, departmentProductEnv.getId().toString()));
	}

	/**
	 * {@code GET  /department-product-env} : get all the departmentProductEnv.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of departmentProductEnv in body.
	 */
	@GetMapping("/department-product-env")
	public List<DepartmentProductEnv> getAllDepartmentProductEnv() {
		log.debug("REST request to get all DepartmentProductEnv");
		return departmentProductEnvService.findAll();
	}

	/**
	 * {@code GET  /department-product-env/:id} : get the "id" departmentProductEnv.
	 *
	 * @param id the id of the departmentProductEnv to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the departmentProductEnv, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/department-product-env/{id}")
	public ResponseEntity<DepartmentProductEnv> getDepartmentProductEnv(@PathVariable Long id) {
		log.debug("REST request to get DepartmentProductEnv : {}", id);
		Optional<DepartmentProductEnv> departmentProductEnv = departmentProductEnvService.findOne(id);
		return ResponseUtil.wrapOrNotFound(departmentProductEnv);
	}

	/**
	 * {@code DELETE  /department-product-env/:id} : delete the "id" departmentProductEnv.
	 *
	 * @param id the id of the departmentProductEnv to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/department-product-env/{id}")
	public ResponseEntity<Void> deleteDepartmentProductEnv(@PathVariable Long id) {
		log.debug("REST request to delete DepartmentProductEnv : {}", id);
		departmentProductEnvService.delete(id);
		return ResponseEntity.noContent()
				
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /department-product-env/search} : get all the departmentProductEnv on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of departmentProductEnv in body.
	 */
	@GetMapping("/department-product-env/search")
	public List<DepartmentProductEnv> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all departmentProductEnv on given filters");
		return departmentProductEnvService.search(filter);
	}
	
	
	
//	@PostMapping("/department/atach-product/department/{departmentid}/cloud-environment/{cloudEnvId}/product/{productId}")
//	public ResponseEntity<Department> atachProduct(@PathVariable Long departmentid, @PathVariable Long cloudEnvId, @PathVariable Long productId){
//		logger.info("Request to attach new product with department");
//		Department dp = departmentProductService.atachProduct(departmentid, cloudEnvId, productId);
//		return ResponseEntity.status(HttpStatus.OK).body(dp);
//	}
//	
//	@PostMapping("/department/detach-product/department/{departmentid}/cloud-environment/{cloudEnvId}/product/{productId}")
//	public ResponseEntity<Boolean> detachProduct(@PathVariable Long departmentid, @PathVariable Long cloudEnvId, @PathVariable Long productId){
//		logger.info("Request to attach new product with department");
//		boolean isRemoved = departmentProductService.detachProduct(departmentid, cloudEnvId, productId);
//		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(isRemoved));
//	}
//	
//	@GetMapping("/department/{departmentid}/product")
//	public ResponseEntity<List<Product>> getAllProductsOfDepartment(@PathVariable Long departmentid){
//		logger.info("Request to get all products of a department");
//		List<Product> productList = departmentProductService.getAllProductsOfDepartment(departmentid);
//		return ResponseEntity.status(HttpStatus.OK).body(productList);
//	}
//	
//	@GetMapping("/department/{departmentid}/cloudEnvironment/{cloudEnvId}/product")
//	public ResponseEntity<List<Product>> getAllProducts(@PathVariable Long departmentid, @PathVariable Long cloudEnvId){
//		logger.info("Request to get all products of a department and cloud environment");
//		List<Product> productList = departmentProductService.getAllProducts(departmentid, cloudEnvId);
//		return ResponseEntity.status(HttpStatus.OK).body(productList);
//	}
}
