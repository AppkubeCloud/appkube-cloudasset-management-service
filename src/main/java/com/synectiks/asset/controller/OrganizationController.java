package com.synectiks.asset.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.service.OrganizationService;
import com.synectiks.asset.domain.Organization;

@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
//	private static final String ENTITY_NAME = "Organization";
	
//	@Value("jhipster.clientApp.name")
//	private String applicationName;
//	
//	@Autowired
//    private OrganizationRepository organizationRepository;
//	
	@Autowired
	private OrganizationService organizationService;
//
//	@PostMapping("/organization")
//	public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization)
//			throws URISyntaxException {
//		logger.debug("REST request to save Organization : {}", organization);
//		if (organization.getId() != null) {
//			throw new BadRequestAlertException("A new organization cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		Organization result = organizationService.save(organization);
//		return ResponseEntity
//				.created(new URI("/api/organizations/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	@PutMapping("/organization/{id}")
//	public ResponseEntity<Organization> updateOrganization(@PathVariable(value = "id", required = false) final Long id,
//			@Valid @RequestBody Organization organization) throws URISyntaxException {
//		logger.debug("REST request to update Organization : {}, {}", id, organization);
//		if (organization.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		if (!Objects.equals(id, organization.getId())) {
//			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//		}
//
//		if (!organizationRepository.existsById(id)) {
//			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//		}
//
//		Organization result = organizationService.update(organization);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
//				organization.getId().toString())).body(result);
//	}
//
//	@PatchMapping(value = "/organization/{id}", consumes = {"application/json", "application/merge-patch+json"})
//	public ResponseEntity<Organization> partialUpdateOrganization(
//			@PathVariable(value = "id", required = false) final Long id,
//			@NotNull @RequestBody Organization organization) throws URISyntaxException {
//		logger.debug("REST request to partial update Organization partially : {}, {}", id, organization);
//		if (organization.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		if (!Objects.equals(id, organization.getId())) {
//			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//		}
//
//		if (!organizationRepository.existsById(id)) {
//			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//		}
//
//		Optional<Organization> result = organizationService.partialUpdate(organization);
//
//		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false,
//				ENTITY_NAME, organization.getId().toString()));
//	}
//
//	@GetMapping("/organization")
//	public ResponseEntity<List<Organization>> getAllOrganizations() {
//		logger.debug("REST request to get all Organizations");
//		return ResponseEntity.status(HttpStatus.OK).body(organizationService.findAll());
//	}
//
//	@GetMapping("/organization/{id}")
//	public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
//		logger.debug("REST request to get Organization : {}", id);
//		Optional<Organization> organization = organizationService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(organization);
//	}
//
//	@DeleteMapping("/organization/{id}")
//	public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
//		logger.debug("REST request to delete Organization : {}", id);
//		organizationService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@GetMapping("/org/org-id/{id}")
	private Organization getOrgById(@PathVariable Long id) {
		Organization org = organizationService.getOrgById(id);
		return org;
	}

	@GetMapping("/org/user-name/{userName}")
	private Organization getAllOrgUnitsByUserName(@PathVariable String userName) {
		Organization org = organizationService.getOrgByUserName(userName);
		return org;
	}

}
