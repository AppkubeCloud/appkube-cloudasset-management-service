package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.domain.Department;
import com.synectiks.asset.business.domain.Organization;
import com.synectiks.asset.business.service.OrganizationService;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class QueryController {

	private final Logger logger = LoggerFactory.getLogger(QueryController.class);

	private static final String ENTITY_NAME = "Query";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private OrganizationService organizationService;

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
	 * {@code GET /organizations/{orgId}/departments/{depId}/landing-zone} : get
	 * landing zone list of an departments an organization.
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

	/**
	 * {@code GET /organizations/{orgId}/environments/{env}/products} : get product
	 * list of an env an organization.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product list of an associatedEnv an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/environments/{env}/products")
	public ResponseEntity<List<String>> getOrganizationEnvProduct(@PathVariable Long orgId, @PathVariable String env)
			throws IOException, URISyntaxException {
		logger.debug("REST request to get list of  product given Organization and associatedEnv. Organization id :{}",
				env);
		List<String> result = organizationService.getOrganizationEnvProduct(orgId, env);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + env))
				.body(result);
	}

	/**
	 * {@code GET
	 * /organizations/{orgId}/departments/{depId}/environments/{env}/products} : get
	 * product list of an departments an env an organization.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         product list of an department an associatedEnv an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/environments/{env}/products")
	public ResponseEntity<List<String>> getOrganizationDepartmentEnvProduct(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String env) throws IOException, URISyntaxException {
		logger.debug(
				"REST request to get list of  product given Organization and associatedEnv and department. Organization id :{}",
				env);
		List<String> result = organizationService.getOrganizationDepartmentEnvProduct(orgId, depId, env);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
				"orgId: " + orgId + depId + env)).body(result);
	}

	// micro - serivces

	/**
	 * {@code GET /organizations/{orgId}/services} : get services list of an
	 * organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/services")
	public ResponseEntity<List<Organization>> getOrganizationMicroServices(@PathVariable Long orgId)
			throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services of given Organization. Organization id :{}", orgId);
		List<Organization> result = organizationService.getOrganizationMicroServices(orgId);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/products/{product}/services : get services
	 * list of an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 * services list of an organization an products.
	 * 
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/products/{product}/services")
	public ResponseEntity<List<String>> getOrganizationproductsMicroServices(@PathVariable Long orgId,
			@PathVariable String product) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services of given Organization and products. Organization id :{}",
				orgId, product);
		List<String> result = organizationService.getOrganizationproductsMicroServices(orgId, product);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + product))
				.body(result);

	}

	/**
	 * {@code GET /organizations/{orgId}/environments/{env}/services: get services
	 * list of an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 * services list of an organization an env.
	 * 
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/environments/{env}/services")
	public ResponseEntity<List<String>> getOrganizationEnvMicroServices(@PathVariable Long orgId,
			@PathVariable Long env) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services of given Organization and env. Organization id :{}", orgId,
				env);
		List<String> result = organizationService.getOrganizationEnvMicroServices(orgId, env);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + env))
				.body(result);

	}
	/**
	 * {@code GET /organizations/{orgId}/products/{product}/environments/{env}/services} : get services
	 * list of organization an  an env an products.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an  an env an products.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/products/{product}/environments/{env}/services")
	public ResponseEntity<List<Organization>> getOrganizationProductEnvMicroServices(@PathVariable Long orgId,
			 @PathVariable String product,@PathVariable Long env) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services  an env an products of given Organization. Organization id :{}",
				orgId,product,env);
		List<Organization> result = organizationService.getOrganizationProductEnvMicroServices(orgId,product ,env);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + product+env))
				.body(result);

	}
	/**
	 * {@code GET
	 * /organizations/organizations/{orgId}/service-type/{serviceType}/services: get
	 * services list of an organization.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 * services list of an organization  an serviceType.
	 * 
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/service-type/{serviceType}/services")
	public ResponseEntity<List<String>> getOrganizationServiceTypeMicroServices(@PathVariable Long orgId,
			 @PathVariable String serviceType) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services of given Organization and serviceType. Organization id :{}", orgId,
				serviceType);
		List<String> result = organizationService.getOrganizationServiceTypeMicroServices(orgId ,serviceType);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + serviceType))
				.body(result);

	}
	
	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/services} : get services
	 * list of an organization an department.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an department.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/services")
	public ResponseEntity<List<Organization>> getOrganizationDepartmentsMicroServices(@PathVariable Long orgId,
			@PathVariable Long depId) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services an department of given Organization. Organization id :{}",
				orgId, depId);
		List<Organization> result = organizationService.getOrganizationDepartmentsMicroServices(orgId, depId);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId))
				.body(result);

	}
	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/products/{product}/services} : get services
	 * list of an organization an departments an products.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an departments an products.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/products/{product}/services")
	public ResponseEntity<List<String>> getOrganizationDepartmentsProductMicroServices(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String product) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services an department an product of given Organization. Organization id :{}",
				orgId, depId,product);
		List<String> result = organizationService.getOrganizationDepartmentsProductMicroServices(orgId, depId,product);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId+product))
				.body(result);

	}
	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/environments/{env}/services} : get services
	 * list of organization an departments an env .
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an departments an env.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/environments/{env}/services")
	public ResponseEntity<List<String>> getOrganizationDepartmentsEnvMicroServices(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable Long env) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services an department an env of given Organization. Organization id :{}",
				orgId, depId,env);
		List<String> result = organizationService.getOrganizationDepartmentsEnvMicroServices(orgId, depId,env);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + depId+env))
				.body(result);

	}
	
	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/products/{product}/environments/{env}/services} : get services
	 * list of organization an departments an env an products.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an departments an env an products.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/products/{product}/environments/{env}/services")
	public ResponseEntity<List<Organization>> getOrganizationDepartmentsProductEnvMicroServices(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String product,@PathVariable Long env) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services an department an env an products of given Organization. Organization id :{}",
				orgId, depId,product,env);
		List<Organization> result = organizationService.getOrganizationDepartmentsProductEnvMicroServices(orgId,product ,depId,env);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId + product+depId+env))
				.body(result);

	}
	
	/**
	 * {@code GET /organizations/{orgId}/departments/{depId}/products/{product}/environments/{env}/services} : get services
	 * list of organization an departments an serviceType.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and get
	 *         services list of an organization an departments an serviceType.
	 * @throws URISyntaxException
	 */

	@GetMapping("/organizations/{orgId}/departments/{depId}/service-type/{serviceType}/services")
	public ResponseEntity<List<String>> getOrganizationDepartmentsServiceTypeMicroServices(@PathVariable Long orgId,
			@PathVariable Long depId, @PathVariable String serviceType) throws IOException, URISyntaxException {
		logger.debug("REST request to get list of services an department an serviceType of given Organization. Organization id :{}",
				orgId, depId,serviceType);
		List<String> result = organizationService.getOrganizationDepartmentsServiceTypeMicroServices(orgId ,depId,serviceType);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "orgId: " + orgId+depId+serviceType))
				.body(result);

	}

}