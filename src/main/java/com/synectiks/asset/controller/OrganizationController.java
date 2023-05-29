package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
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

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.business.service.OrganizationService;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class OrganizationController {

	private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	private static final String ENTITY_NAME = "Organization";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OrganizationRepository organizationRepository;

	/**
	 * {@code POST  /organizations} : Create a new organization.
	 *
	 * @param organization the organization to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new organization, or with status {@code 400 (Bad Request)}
	 *         if the organization has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/organizations")
	public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization)
			throws URISyntaxException {
		logger.debug("REST request to save Organization : {}", organization);
		if (organization.getId() != null) {
			throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Organization result = organizationService.save(organization);
		return ResponseEntity
				.created(new URI("/api/organizations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /organizations/:id} : Updates an existing organization.
	 *
	 * @param id           the id of the organization to save.
	 * @param organization the organization to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated organization, or with status {@code 400 (Bad Request)} if
	 *         the organization is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the organization couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/organizations/{id}")
	public ResponseEntity<Organization> updateOrganization(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Organization organization) throws URISyntaxException {
		logger.debug("REST request to update Organization : {}, {}", id, organization);
		if (organization.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, organization.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!organizationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Organization result = organizationService.save(organization);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				organization.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /organizations/:id} : Partial updates given fields of an
	 * existing organization, field will ignore if it is null
	 *
	 * @param id           the id of the organization to save.
	 * @param organization the organization to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated organization, or with status {@code 400 (Bad Request)} if
	 *         the organization is not valid, or with status {@code 404 (Not Found)}
	 *         if the organization is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the organization couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Organization> partialUpdateOrganization(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody Organization organization) throws URISyntaxException {
		logger.debug("REST request to partial update Organization partially : {}, {}", id, organization);
		if (organization.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, organization.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!organizationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<Organization> result = organizationService.partialUpdate(organization);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false,
				ENTITY_NAME, organization.getId().toString()));
	}

	/**
	 * {@code GET  /organizations} : get all the organizations.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of organizations in body.
	 * @throws IOException
	 */
	@GetMapping("/organizations")
	public List<Organization> getAllOrganizations() throws IOException {
		logger.debug("REST request to get all Organizations");
		return organizationService.findAll();
	}

	/**
	 * {@code GET  /organizations/:id} : get the "id" organization.
	 *
	 * @param id the id of the organization to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the organization, or with status {@code 404 (Not Found)}.
	 * @throws IOException
	 */
	@GetMapping("/organizations/{id}")
	public ResponseEntity<Organization> getOrganization(@PathVariable Long id) throws IOException {
		logger.debug("REST request to get Organization : {}", id);
		Optional<Organization> organization = organizationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(organization);
	}

	/**
	 * {@code DELETE  /organizations/:id} : delete the "id" organization.
	 *
	 * @param id the id of the organization to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/organizations/{id}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
		logger.debug("REST request to delete Organization : {}", id);
		organizationService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /organizations/search} : get all the organizations on given
	 * filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of organizations in body.
	 */
	@GetMapping("/organizations/search")
	public List<Organization> search(@RequestParam Map<String, String> filter) throws IOException {
		logger.debug("REST request to get all organizations on given filters");
		return organizationService.search(filter);
	}

	/**
	 * {@code GET /organizations/{orgId}/departments} : get department list of an
	 * organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         department list of an organization.
	 * @throws URISyntaxException
	 */
	@GetMapping("/organizations/{orgId}/departments")
	public ResponseEntity<Set<Department>> getOrganizationDepartment(@PathVariable Long orgId) throws IOException {
		logger.debug("REST request to get list of department of given Organization. Organization id :{}", orgId);
		Optional<Organization> oOrg = organizationService.findOne(orgId);
		if (oOrg.isPresent()) {
			Set<Department> result = oOrg.get().getDepartments();
			return ResponseEntity.ok().headers(
					HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
					.body(result);
		}
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
				.body(Collections.emptySet());
	}

	/**
	 * {@code GET /organizations/{orgId}/products} : get product list of an
	 * organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         department list of an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/products")
	public ResponseEntity<List<String>> getOrganizationProducts(@PathVariable Long orgId)
			throws IOException, URISyntaxException {
		logger.debug("REST request to get list of products of given Organization. Organization id :{}", orgId);
		List<String> result = organizationService.getProducts(orgId);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/products} : get product
	 * list of an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         department list of an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/products")
	public ResponseEntity<List<String>> getOrganizationDepartmentsProducts(@PathVariable Long orgId,
			@PathVariable Long depId) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of products an department of given Organization. Organization id :{}",
				orgId, depId);
		List<String> result = organizationService.getDepartmentProducts(orgId, depId);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/landing-zone} : get landing zone list of an
	 * organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         department list of an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/landing-zone")
	public ResponseEntity<List<String>> getOrganizationLandingZone(@PathVariable Long orgId)
			throws IOException, URISyntaxException {
		logger.debug("REST request to get list of  landing Zones of given Organization. Organization id :{}", orgId);
		List<String> result = organizationService.getLandingZones(orgId);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/landing-zone} : get landing zone list of an
	 * departments an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         landing-zone list of an departments an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/landing-zone")
	public ResponseEntity<List<String>> getOrganizationDepartmentLandingZone(@PathVariable Long orgId,
			@PathVariable Long depId) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  landing Zones of given Departments and Organization. Organization,Departments id :{}",
				orgId, depId);
		List<String> result = organizationService.getDepartmentLandingZones(orgId, depId);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/cloud/{cloudName}/landing-zone} : get
	 * landing zone list of an cloudName an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         landing-zone list of an cloudName an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/cloud/{cloudName}/landing-zone")
	public ResponseEntity<List<String>> getOrganizationCloudnameLandingZone(@PathVariable Long orgId,
			@PathVariable String cloudName) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  landing Zones of given CloudName and Organization. Organization,cloudName id :{}",
				orgId, cloudName);
		List<String> result = organizationService.getCloudnameLandingZones(orgId, cloudName);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
				"orgId: " + orgId + cloudName)).body(result);

	}

	/**
	 * {@code GET
	 * /organizations/{orgId}/departments/{depId}/cloud/{cloudName}/landing-zone} :
	 * get landing zone list of an departments an cloudName an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         landing-zone list of departments an cloudName an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/cloud/{cloudName}/landing-zone")
	public ResponseEntity<List<String>> getOrganizationDepartmentCloudnameLandingZone(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String cloudName) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  landing Zones of given  departments and CloudName and Organization. Organization,Departments id,cloudName :{}",
				orgId, depId, cloudName);
		List<String> result = organizationService.getDepartmentCloudnameLandingZones(orgId, depId, cloudName);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
				"orgId: " + orgId + depId + cloudName)).body(result);

	}

//		product-enclave query started

	/**
	 * {@code GET /organizations/{orgId}/product-enclave} : get product-enclave list
	 * of an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product-enclave list of an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/product-enclave")
	public ResponseEntity<List<String>> getOrganizationProductEnclave(@PathVariable Long orgId)
			throws IOException, URISyntaxException {
		logger.debug("REST request to get list of  product-enclaveof given Organization. Organization id :{}", orgId);
		List<String> result = organizationService.getOrganizationProductEnclave(orgId);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/product-enclave} : get
	 * product-enclave list of an departments an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product-enclave list of an departments an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/product-enclave")
	public ResponseEntity<List<String>> getOrganizationDepartmentsProductEnclave(@PathVariable Long orgId,
			@PathVariable Long depId) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  product-enclaveof given Organization and departments. Organization id :{}",
				orgId, depId);
		List<String> result = organizationService.getOrganizationDepartmentsProductEnclave(orgId, depId);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/landing-zone/{landingZone}/product-enclave}
	 * : get product-enclave list of an landingZone an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product-enclave list of an landingZone an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/landing-zone/{landingZone}/product-enclave")
	public ResponseEntity<List<String>> getOrganizationLandingzoneProductEnclave(@PathVariable Long orgId,
			@PathVariable String landingZone) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  product-enclaveof given Organization and landingZone. Organization id :{}",
				orgId, landingZone);
		List<String> result = organizationService.getOrganizationLandingzoneProductEnclave(orgId, landingZone);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
				"orgId: " + orgId + landingZone)).body(result);

	}

	/**
	 * {@code GET
	 * /organizations/{orgId}/departments/{depId}/landing-zone/{landingZone}/product-enclave}
	 * : get product-enclave list of an departments an landingZone an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product-enclave list of an departments an landingZone an
	 *         organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/landing-zone/{landingZone}/product-enclave")
	public ResponseEntity<List<String>> getOrganizationDepartmentLandingzoneProductEnclave(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String landingZone) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  product-enclaveof given Organization and departments and landingZone. Organization id :{}",
				orgId, depId, landingZone);
		List<String> result = organizationService.getOrganizationDepartmentLandingzoneProductEnclave(orgId, depId,
				landingZone);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
				"orgId: " + orgId + depId + landingZone)).body(result);

	}

}
