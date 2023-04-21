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
import com.synectiks.asset.business.domain.Product;
import com.synectiks.asset.business.service.CloudEnvironmentService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.business.service.DepartmentService;
import com.synectiks.asset.business.service.DeploymentEnvironmentService;
import com.synectiks.asset.business.service.ProductService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.ProductRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ProductController {

	private final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private static final String ENTITY_NAME = "Product";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private ProductService productService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private CloudEnvironmentService cloudEnvironmentService;

	@Autowired
	private ServiceAllocationService departmentProductEnvService;

	@Autowired
	private DeploymentEnvironmentService deploymentEnvironmentService;
	
	@Autowired
	private ProductRepository productRepository;

	/**
	 * {@code POST  /products} : Create a new product.
	 *
	 * @param product the product to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new product, or with status {@code 400 (Bad Request)} if the
	 *         product has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws URISyntaxException {
		logger.debug("REST request to save Product : {}", product);
		if (product.getId() != null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Product result = productService.save(product);
		return ResponseEntity
				.created(new URI("/api/products/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /products/:id} : Updates an existing product.
	 *
	 * @param id      the id of the product to save.
	 * @param product the product to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated product, or with status {@code 400 (Bad Request)} if the
	 *         product is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the product couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Product product) throws URISyntaxException {
		logger.debug("REST request to update Product : {}, {}", id, product);
		if (product.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, product.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!productRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Product result = productService.save(product);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, product.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /products/:id} : Partial updates given fields of an existing
	 * product, field will ignore if it is null
	 *
	 * @param id      the id of the product to save.
	 * @param product the product to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated product, or with status {@code 400 (Bad Request)} if the
	 *         product is not valid, or with status {@code 404 (Not Found)} if the
	 *         product is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the product couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/products/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Product> partialUpdateProduct(@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody Product product) throws URISyntaxException {
		logger.debug("REST request to partial update Product partially : {}, {}", id, product);
		if (product.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, product.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!productRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<Product> result = productService.partialUpdate(product);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, product.getId().toString()));
	}

	/**
	 * {@code GET  /products} : get all the products.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		logger.debug("REST request to get all Products");
		return productService.findAll();
	}

	/**
	 * {@code GET  /products/:id} : get the "id" product.
	 *
	 * @param id the id of the product to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the product, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		logger.debug("REST request to get Product : {}", id);
		Optional<Product> product = productService.findOne(id);
		return ResponseUtil.wrapOrNotFound(product);
	}

	/**
	 * {@code DELETE  /products/:id} : delete the "id" product.
	 *
	 * @param id the id of the product to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		logger.debug("REST request to delete Product : {}", id);
		productService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /products/search} : get all the products on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	@GetMapping("/products/search")
	public List<Product> search(@RequestParam Map<String, String> filter) throws IOException {
		logger.debug("REST request to get all products on given filters");
		return productService.search(filter);
	}

	/**
	 * {@code POST  /products/add-dep-env} : Add a deployment environment in a product.
	 *
	 * @param departmentProductEnv the departmentProductEnv to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new departmentProductEnv, or with status
	 *         {@code 400 (Bad Request)} if the departmentProductEnv has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException
	 */
	@PostMapping("/products/add-dep-env")
	public ResponseEntity<Product> addDeploymentEnvironment(@RequestBody ServiceAllocation departmentProductEnv)
			throws URISyntaxException, IOException {
		logger.debug("REST request to add a deployment environment in a product: {}", departmentProductEnv);

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
		if (!productRepository.existsById(departmentProductEnv.getProductId())) {
			throw new BadRequestAlertException("Product not found", ENTITY_NAME, "idnotfound");
		}

		if (departmentProductEnv.getDeploymentEnvironmentId() == null) {
			throw new BadRequestAlertException("Invalid deployment environment id", ENTITY_NAME, "idnull");
		}
		Optional<DeploymentEnvironment> ode = deploymentEnvironmentService.findOne(departmentProductEnv.getDeploymentEnvironmentId());
		if (!ode.isPresent()) {
			throw new BadRequestAlertException("Deployment environment not found", ENTITY_NAME, "idnotfound");
		}
		
		Map<String, String> filter = new HashMap<>();
		filter.put(Constants.LANDING_ZONE, departmentProductEnv.getLandingZone());
		filter.put(Constants.DEPARTMENT_ID, String.valueOf(departmentProductEnv.getDepartmentId()));
		filter.put(Constants.PRODUCT_ID, String.valueOf(departmentProductEnv.getProductId()));
		filter.put(Constants.DEPLOYMENT_ENVIRONMENT_ID, String.valueOf(departmentProductEnv.getDeploymentEnvironmentId()));
		List<ServiceAllocation> dpeList = departmentProductEnvService.search(filter);
		if(dpeList.size() > 0) {
			throw new BadRequestAlertException("Deployment environment is already associated with product", ENTITY_NAME, "idexists");
		}
		filter.remove(Constants.DEPLOYMENT_ENVIRONMENT_ID);
		dpeList = departmentProductEnvService.search(filter);
		ServiceAllocation existingDpe = null;
		for(ServiceAllocation dpe: dpeList) {
			if(dpe.getLandingZone().equals(departmentProductEnv.getLandingZone())
					&& dpe.getDepartmentId() == departmentProductEnv.getDepartmentId()
					&& dpe.getProductId() == departmentProductEnv.getProductId()
					&& dpe.getDeploymentEnvironmentId() == null) {
				existingDpe = dpe;
				break;
			}
		}
		if(existingDpe != null) {
			existingDpe.setDeploymentEnvironmentId(departmentProductEnv.getDeploymentEnvironmentId());
			departmentProductEnvService.save(existingDpe);
		}else {
			departmentProductEnvService.save(departmentProductEnv);
		}
		
		Optional<Product> op = productService.findOne(departmentProductEnv.getProductId());

		return ResponseEntity
				.created(new URI("products/add-dep-env/" + op.get().getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, op.get().getId().toString()))
				.body(op.get());
	}
}
