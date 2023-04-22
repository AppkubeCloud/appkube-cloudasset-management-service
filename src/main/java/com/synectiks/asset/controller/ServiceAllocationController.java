package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import com.synectiks.asset.business.domain.DeploymentEnvironment;
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.domain.ServiceAllocation;
import com.synectiks.asset.business.domain.Services;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.business.service.ServicesService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ServiceAllocationRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ServiceAllocationController {

	private final Logger log = LoggerFactory.getLogger(ServiceAllocationController.class);
	private static final String ENTITY_NAME = "ServiceAllocation";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;
	
	@Autowired
	private ServiceAllocationService serviceAllocationService;

	@Autowired
	private DeploymentEnvironmentService deploymentEnvironmentService;

	@Autowired
	private ServicesService servicesService;
	
	@Autowired
	private ServiceAllocationRepository serviceAllocationRepository;

	/**
	 * {@code POST  /service-allocations} : Create a new serviceAllocation.
	 *
	 * @param serviceAllocation the serviceAllocation to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new serviceAllocation, or with status
	 *         {@code 400 (Bad Request)} if the serviceAllocation has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException 
	 */
	@PostMapping("/service-allocations")
	public ResponseEntity<ServiceAllocation> createServiceAllocation(@RequestBody ServiceAllocation serviceAllocation)
			throws URISyntaxException, IOException {
		log.debug("REST request to save ServiceAllocation : {}", serviceAllocation);
		if (serviceAllocation.getId() != null) {
			throw new BadRequestAlertException("A new serviceAllocation cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		
		log.debug("validating department");
		if (serviceAllocation.getDepartmentId() == null) {
			throw new BadRequestAlertException("Invalid department id", ENTITY_NAME, "idnull");
		}
		Optional<Department> od = departmentService.findOne(serviceAllocation.getDepartmentId());
		if (!od.isPresent()) {
			throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
		}

		log.debug("validating product");
		if (serviceAllocation.getProductId() == null) {
			throw new BadRequestAlertException("Invalid product id", ENTITY_NAME, "idnull");
		}
		Optional<Product> op = productService.findOne(serviceAllocation.getProductId());
		if (!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating landing zone");
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(serviceAllocation.getDepartmentId()));
		filter.put(Constants.ACCOUNT_ID, serviceAllocation.getLandingZone());
		List<CloudEnvironment> ceList = cloudEnvironmentService.search(filter);
		if(ceList == null || ( ceList != null && ceList.size() == 0)) {
			throw new BadRequestAlertException("Landing zone(Account id) not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating deployment environment");
		if (serviceAllocation.getDeploymentEnvironmentId() == null) {
			throw new BadRequestAlertException("Invalid deployment environment id", ENTITY_NAME, "idnull");
		}
		Optional<DeploymentEnvironment> de = deploymentEnvironmentService.findOne(serviceAllocation.getDeploymentEnvironmentId());
		if (!de.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", ENTITY_NAME, "idnotfound");
		}
		
		log.debug("validating service");
		if (serviceAllocation.getServicesId() == null) {
			throw new BadRequestAlertException("Invalid service id", ENTITY_NAME, "idnull");
		}
		Optional<Services> oss = servicesService.findOne(serviceAllocation.getServicesId());
		if (!oss.isPresent()) {
			throw new BadRequestAlertException("Service not found", ENTITY_NAME, "idnotfound");
		}
		serviceAllocation.setServiceType(oss.get().getType());
		serviceAllocation.setServiceNature(oss.get().getServiceNature());
		
		ServiceAllocation result = serviceAllocationService.save(serviceAllocation);
		
		return ResponseEntity
				.created(new URI("/api/service-allocations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /service-allocations/:id} : Updates an existing
	 * serviceAllocation.
	 *
	 * @param id                the id of the serviceAllocation to save.
	 * @param serviceAllocation the serviceAllocation to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated serviceAllocation, or with status
	 *         {@code 400 (Bad Request)} if the serviceAllocation is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         serviceAllocation couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/service-allocations/{id}")
	public ResponseEntity<ServiceAllocation> updateServiceAllocation(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ServiceAllocation serviceAllocation) throws URISyntaxException {
		log.debug("REST request to update ServiceAllocation : {}, {}", id, serviceAllocation);
		if (serviceAllocation.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, serviceAllocation.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!serviceAllocationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		
		if (serviceAllocation.getDepartmentId() == null) {
			throw new BadRequestAlertException("Invalid department id", ENTITY_NAME, "idnull");
		}
		
		Optional<Department> od = departmentService.findOne(serviceAllocation.getDepartmentId());
		if (!od.isPresent()) {
			throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
		}

		if (serviceAllocation.getProductId() == null) {
			throw new BadRequestAlertException("Invalid product id", ENTITY_NAME, "idnull");
		}
		
		Optional<Product> op = productService.findOne(serviceAllocation.getProductId());
		if (!op.isPresent()) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}

		
		ServiceAllocation result = serviceAllocationService.save(serviceAllocation);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				serviceAllocation.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /service-allocations/:id} : Partial updates given fields of an
	 * existing serviceAllocation, field will ignore if it is null
	 *
	 * @param id                the id of the serviceAllocation to save.
	 * @param serviceAllocation the serviceAllocation to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated serviceAllocation, or with status
	 *         {@code 400 (Bad Request)} if the serviceAllocation is not valid, or
	 *         with status {@code 404 (Not Found)} if the serviceAllocation is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         serviceAllocation couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException 
	 */
	@PatchMapping(value = "/service-allocations/{id}", consumes = { "application/json",
			"application/merge-patch+json" })
	public ResponseEntity<ServiceAllocation> partialUpdateServiceAllocation(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ServiceAllocation serviceAllocation) throws URISyntaxException, IOException {
		log.debug("REST request to partial update ServiceAllocation partially : {}, {}", id, serviceAllocation);
		if (serviceAllocation.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, serviceAllocation.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!serviceAllocationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
//
//		if (serviceAllocation.getDepartmentId() == null) {
//			throw new BadRequestAlertException("Invalid department id", ENTITY_NAME, "idnull");
//		}
//		Optional<Department> od = departmentService.findOne(serviceAllocation.getDepartmentId());
//		if (!od.isPresent()) {
//			throw new BadRequestAlertException("Department not found", ENTITY_NAME, "idnotfound");
//		}
//
//		if (serviceAllocation.getProductId() == null) {
//			throw new BadRequestAlertException("Invalid product id", ENTITY_NAME, "idnull");
//		}
//		Optional<Product> op = productService.findOne(serviceAllocation.getProductId());
//		if (!op.isPresent()) {
//			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
//		}
//		
//		Map<String, String> filter = new HashMap<>();
//		filter.put(Constants.DEPARTMENT_ID, String.valueOf(serviceAllocation.getDepartmentId()));
//		filter.put(Constants.ACCOUNT_ID, serviceAllocation.getLandingZone());
//		List<CloudEnvironment> ceList = cloudEnvironmentService.search(filter);
//		if(ceList == null || ( ceList != null && ceList.size() == 0)) {
//			throw new BadRequestAlertException("Landing zone(Account id) not found", ENTITY_NAME, "idnotfound");
//		}
//
//		if (serviceAllocation.getServicesId() == null) {
//			throw new BadRequestAlertException("Invalid service id", ENTITY_NAME, "idnull");
//		}
//		Optional<Services> oss = servicesService.findOne(serviceAllocation.getServicesId());
//		if (!oss.isPresent()) {
//			throw new BadRequestAlertException("Service not found", ENTITY_NAME, "idnotfound");
//		}
		
		Optional<ServiceAllocation> result = serviceAllocationService.partialUpdate(serviceAllocation);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false,
				ENTITY_NAME, serviceAllocation.getId().toString()));
	}

	/**
	 * {@code GET  /service-allocations} : get all the serviceAllocation.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of serviceAllocation in body.
	 */
	@GetMapping("/service-allocations")
	public List<ServiceAllocation> getAllServiceAllocation() {
		log.debug("REST request to get all ServiceAllocation");
		return serviceAllocationService.findAll();
	}

	/**
	 * {@code GET  /service-allocations/:id} : get the "id" serviceAllocation.
	 *
	 * @param id the id of the serviceAllocation to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the serviceAllocation, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/service-allocations/{id}")
	public ResponseEntity<ServiceAllocation> getServiceAllocation(@PathVariable Long id) {
		log.debug("REST request to get ServiceAllocation : {}", id);
		Optional<ServiceAllocation> serviceAllocation = serviceAllocationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(serviceAllocation);
	}

	/**
	 * {@code DELETE  /service-allocations/:id} : delete the "id" serviceAllocation.
	 *
	 * @param id the id of the serviceAllocation to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/service-allocations/{id}")
	public ResponseEntity<Void> deleteServiceAllocation(@PathVariable Long id) {
		log.debug("REST request to delete ServiceAllocation : {}", id);
		serviceAllocationService.delete(id);
		return ResponseEntity.noContent()
				
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /service-allocations/search} : get all the serviceAllocation on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of serviceAllocation in body.
	 */
	@GetMapping("/service-allocations/search")
	public List<ServiceAllocation> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all serviceAllocation on given filters");
		return serviceAllocationService.search(filter);
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
