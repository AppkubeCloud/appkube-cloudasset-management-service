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

import com.synectiks.asset.business.domain.AssetServiceTag;
import com.synectiks.asset.business.service.AssetServiceTagService;
import com.synectiks.asset.business.service.ServiceAllocationService;
import com.synectiks.asset.repository.AssetServiceTagRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class AssetServiceTagController {

	private final Logger log = LoggerFactory.getLogger(AssetServiceTagController.class);

	private static final String ENTITY_NAME = "AssetServiceTag";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private AssetServiceTagService assetServiceTagService;

	@Autowired
	private ServiceAllocationService serviceAllocationService;

	@Autowired
	private AssetServiceTagRepository assetServiceTagRepository;

	/**
	 * {@code POST  /tag-asset} : Create a new AssetServiceTag.
	 *
	 * @param assetServiceTag the AssetServiceTag to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new assetServiceTag, or with status {@code 400 (Bad Request)} if
	 *         the assetServiceTag has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tag-asset")
	public ResponseEntity<AssetServiceTag> createTag(@Valid @RequestBody AssetServiceTag assetServiceTag)
			throws URISyntaxException {
		log.debug("REST request to save tag : {}", assetServiceTag);
		if (assetServiceTag.getId() != null) {
			throw new BadRequestAlertException("A new assetServiceTag cannot already have an ID", ENTITY_NAME, "idexists");
		}
		AssetServiceTag result = assetServiceTagService.save(assetServiceTag);
		return ResponseEntity
				.created(new URI("/api/tag-asset/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /tag-asset/:id} : Updates an existing assetServiceTag.
	 *
	 * @param id         the id of the assetServiceTag to save.
	 * @param assetServiceTag the assetServiceTag to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated assetServiceTag, or with status {@code 400 (Bad Request)} if
	 *         the assetServiceTag is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the assetServiceTag couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tag-asset/{id}")
	public ResponseEntity<AssetServiceTag> updateAssetServiceTag(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody AssetServiceTag assetServiceTag) throws URISyntaxException {
		log.debug("REST request to update tag : {}, {}", id, assetServiceTag);
		if (assetServiceTag.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, assetServiceTag.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!assetServiceTagRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		AssetServiceTag result = assetServiceTagService.save(assetServiceTag);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetServiceTag.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /tag-asset/:id} : Partial updates given fields of an existing
	 * AssetServiceTag, field will ignore if it is null
	 *
	 * @param id         the id of the assetServiceTag to save.
	 * @param assetServiceTag the assetServiceTag to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated assetServiceTag, or with status {@code 400 (Bad Request)} if
	 *         the assetServiceTag is not valid, or with status {@code 404 (Not Found)}
	 *         if the assetServiceTag is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the assetServiceTag couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/tag-asset/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<AssetServiceTag> partialUpdateAssetServiceTag(
			@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody AssetServiceTag assetServiceTag)
			throws URISyntaxException {
		log.debug("REST request to update tag partially : {}, {}", id, assetServiceTag);
		if (assetServiceTag.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, assetServiceTag.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!assetServiceTagRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<AssetServiceTag> result = assetServiceTagService.partialUpdate(assetServiceTag);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetServiceTag.getId().toString()));
	}

	/**
	 * {@code GET  /tag-asset} : get all the tags.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of assetServiceTag in body.
	 */
	@GetMapping("/tag-asset")
	public List<AssetServiceTag> getAllAssetServiceTags() {
		log.debug("REST request to get all AssetServiceTags");
		return assetServiceTagService.findAll();
	}

	/**
	 * {@code GET  /tag-asset/:id} : get the "id" assetServiceTags.
	 *
	 * @param id the id of the assetServiceTags to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the assetServiceTags, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tag-asset/{id}")
	public ResponseEntity<AssetServiceTag> getAssetServiceTag(@PathVariable Long id) {
		log.debug("REST request to get assetServiceTag : {}", id);
		Optional<AssetServiceTag> assetServiceTag = assetServiceTagService.findOne(id);
		return ResponseUtil.wrapOrNotFound(assetServiceTag);
	}

	/**
	 * {@code DELETE  /tag-asset/:id} : delete the "id" assetServiceTags.
	 *
	 * @param id the id of the assetServiceTags to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws IOException 
	 */
	@DeleteMapping("/tag-asset/{id}")
	public ResponseEntity<Void> deleteAssetServiceTags(@PathVariable Long id) throws IOException {
		log.debug("REST request to delete assetServiceTags : {}", id);
		assetServiceTagService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /tag-asset/search} : get all the assetServiceTags on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tags in body.
	 */
	@GetMapping("/tag-asset/search")
	public List<AssetServiceTag> search(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all tags on given filters");
		return assetServiceTagService.search(filter);
	}

	/**
	 * {@code GET  /tag-asset/tags/search} : get all the tags on given filters.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tags in body.
	 */
	@GetMapping("/tag-asset/tags/search")
	public List<AssetServiceTag> searchTag(@RequestParam Map<String, String> filter) throws IOException {
		log.debug("REST request to get all tags on given filters");
		return assetServiceTagService.searchTag(filter);
	}
	
}
