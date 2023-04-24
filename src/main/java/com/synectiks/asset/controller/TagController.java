package com.synectiks.asset.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

import com.synectiks.asset.business.domain.DiscoveredAssets;
import com.synectiks.asset.business.domain.ServiceAllocation;
import com.synectiks.asset.business.domain.Tag;
import com.synectiks.asset.business.service.TagService;
import com.synectiks.asset.business.service.DiscoveredAssetsService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.repository.TagRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class TagController {

	private final Logger log = LoggerFactory.getLogger(TagController.class);

	private static final String ENTITY_NAME = "Tag";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private TagService tagService;

	@Autowired
	private ServiceAllocationService serviceAllocationService;

	@Autowired
	private DiscoveredAssetsService discoveredAssetsService;
	
	@Autowired
	private TagRepository tagRepository;

	/**
	 * {@code POST  /tags} : Create a new Tag.
	 *
	 * @param tag the Tag to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tag, or with status {@code 400 (Bad Request)} if
	 *         the tag has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tags")
	public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag)
			throws URISyntaxException {
		log.debug("REST request to save tag : {}", tag);
		if (tag.getId() != null) {
			throw new BadRequestAlertException("A new tag cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Optional<DiscoveredAssets> oda = discoveredAssetsService.findOne(tag.getDiscoveredAsset().getId());
		if(!oda.isPresent()) {
			throw new BadRequestAlertException("Invalid discovered asset id", ENTITY_NAME, "idnull");
		}
		ServiceAllocation serviceAllocation = tag.getServiceAllocation();
		serviceAllocation.setCreatedOn(null);
		serviceAllocation.setUpdatedOn(null);
		Optional<ServiceAllocation> osa = serviceAllocationService.findOne(serviceAllocation);
		if(!osa.isPresent()) {
			throw new BadRequestAlertException("ServiceAllocation not found", ENTITY_NAME, "idnotfound");
		}
		
		tag.setServiceAllocation(osa.get());
		Tag result = tagService.save(tag);
		return ResponseEntity
				.created(new URI("/api/tags/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
		
	}

	/**
	 * {@code PUT  /tags/:id} : Updates an existing tag.
	 *
	 * @param id         the id of the tag to save.
	 * @param tag the tag to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tag, or with status {@code 400 (Bad Request)} if
	 *         the tag is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the tag couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> updateAssetServiceTag(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Tag assetServiceTag) throws URISyntaxException {
		log.debug("REST request to update tag : {}, {}", id, assetServiceTag);
		if (assetServiceTag.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, assetServiceTag.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!tagRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Tag result = tagService.save(assetServiceTag);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetServiceTag.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /tags/:id} : Partial updates given fields of an existing
	 * tag, field will ignore if it is null
	 *
	 * @param id         the id of the Tag to save.
	 * @param tag the tag to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tag, or with status {@code 400 (Bad Request)} if
	 *         the tag is not valid, or with status {@code 404 (Not Found)}
	 *         if the tag is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the tag couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/tags/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Tag> partialUpdateAssetServiceTag(
			@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Tag assetServiceTag)
			throws URISyntaxException {
		log.debug("REST request to update tag partially : {}, {}", id, assetServiceTag);
		if (assetServiceTag.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, assetServiceTag.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!tagRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<Tag> result = tagService.partialUpdate(assetServiceTag);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetServiceTag.getId().toString()));
	}

	/**
	 * {@code GET  /tags} : get all the tags.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tag in body.
	 */
	@GetMapping("/tags")
	public List<Tag> getAllAssetServiceTags() {
		log.debug("REST request to get all AssetServiceTags");
		return tagService.findAll();
	}

	/**
	 * {@code GET  /tag/:id} : get the "id" tags.
	 *
	 * @param id the id of the tags to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the assetServiceTags, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> getAssetServiceTag(@PathVariable Long id) {
		log.debug("REST request to get assetServiceTag : {}", id);
		Optional<Tag> assetServiceTag = tagService.findOne(id);
		return ResponseUtil.wrapOrNotFound(assetServiceTag);
	}

	/**
	 * {@code DELETE  /tags/:id} : delete the "id" tags.
	 *
	 * @param id the id of the tags to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws IOException 
	 */
	@DeleteMapping("/tags/{id}")
	public ResponseEntity<Void> deleteAssetServiceTags(@PathVariable Long id) throws IOException {
		log.debug("REST request to delete assetServiceTags : {}", id);
		tagService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	
//	@GetMapping("/tag/search")
//	public List<AssetServiceTag> search(@RequestParam Map<String, String> filter) throws IOException {
//		log.debug("REST request to get all tags on given filters");
//		return assetServiceTagService.search(filter);
//	}

	/**
	 * {@code GET  /tag/search} : get all the tags on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tags in body.
	 */
	@GetMapping("/tags/search")
	public List<Tag> searchTag(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all tags on given filters");
		return tagService.searchTag(filter);
	}
	
}
