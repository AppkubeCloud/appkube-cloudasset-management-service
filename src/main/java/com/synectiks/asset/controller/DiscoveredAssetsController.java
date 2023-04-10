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

import com.synectiks.asset.business.domain.DiscoveredAssets;
import com.synectiks.asset.business.service.DiscoveredAssetsService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.repository.DiscoveredAssetsRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DiscoveredAssets}.
 */
@RestController
@RequestMapping("/api")
public class DiscoveredAssetsController {

    private final Logger log = LoggerFactory.getLogger(DiscoveredAssetsController.class);

    private static final String ENTITY_NAME = "DiscoveredAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private DiscoveredAssetsService discoveredAssetsService;

    @Autowired
    private DiscoveredAssetsRepository discoveredAssetsRepository;


    /**
     * {@code POST  /discovered-assets} : Create a new discoveredAssets.
     *
     * @param discoveredAssets the discoveredAssets to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discoveredAssets, or with status {@code 400 (Bad Request)} if the discoveredAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discovered-assets")
    public ResponseEntity<DiscoveredAssets> createDiscoveredAssets(@Valid @RequestBody DiscoveredAssets discoveredAssets)
        throws URISyntaxException {
        log.debug("REST request to save DiscoveredAssets : {}", discoveredAssets);
        if (discoveredAssets.getId() != null) {
            throw new BadRequestAlertException("A new discoveredAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!StringUtils.isBlank(discoveredAssets.getTag())) {
        	discoveredAssets.setTagStatus(Constants.TAGGED);
        }
        DiscoveredAssets result = discoveredAssetsService.save(discoveredAssets);
        return ResponseEntity
            .created(new URI("/api/discovered-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discovered-assets/:id} : Updates an existing discoveredAssets.
     *
     * @param id the id of the discoveredAssets to save.
     * @param discoveredAssets the discoveredAssets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discoveredAssets,
     * or with status {@code 400 (Bad Request)} if the discoveredAssets is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discoveredAssets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discovered-assets/{id}")
    public ResponseEntity<DiscoveredAssets> updateDiscoveredAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiscoveredAssets discoveredAssets
    ) throws URISyntaxException {
        log.debug("REST request to update DiscoveredAssets : {}, {}", id, discoveredAssets);
        if (discoveredAssets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discoveredAssets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discoveredAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscoveredAssets result = discoveredAssetsService.save(discoveredAssets);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discoveredAssets.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /discovered-assets/:id} : Partial updates given fields of an existing discoveredAssets, field will ignore if it is null
     *
     * @param id the id of the discoveredAssets to save.
     * @param discoveredAssets the discoveredAssets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discoveredAssets,
     * or with status {@code 400 (Bad Request)} if the discoveredAssets is not valid,
     * or with status {@code 404 (Not Found)} if the discoveredAssets is not found,
     * or with status {@code 500 (Internal Server Error)} if the discoveredAssets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/discovered-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiscoveredAssets> partialUpdateDiscoveredAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiscoveredAssets discoveredAssets
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiscoveredAssets partially : {}, {}", id, discoveredAssets);
        if (discoveredAssets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discoveredAssets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discoveredAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if(!StringUtils.isBlank(discoveredAssets.getTag())) {
        	discoveredAssets.setTagStatus(Constants.TAGGED);
        }
        Optional<DiscoveredAssets> result = discoveredAssetsService.partialUpdate(discoveredAssets);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discoveredAssets.getId().toString())
        );
    }

    /**
     * {@code GET  /discovered-assets} : get all the discoveredAssets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discoveredAssets in body.
     */
    @GetMapping("/discovered-assets")
    public List<DiscoveredAssets> getAllDiscoveredAssets() {
        log.debug("REST request to get all DiscoveredAssets");
        return discoveredAssetsService.findAll();
    }

    /**
     * {@code GET  /discovered-assets/:id} : get the "id" discoveredAssets.
     *
     * @param id the id of the discoveredAssets to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discoveredAssets, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discovered-assets/{id}")
    public ResponseEntity<DiscoveredAssets> getDiscoveredAssets(@PathVariable Long id) {
        log.debug("REST request to get DiscoveredAssets : {}", id);
        Optional<DiscoveredAssets> discoveredAssets = discoveredAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discoveredAssets);
    }

    /**
     * {@code DELETE  /discovered-assets/:id} : delete the "id" discoveredAssets.
     *
     * @param id the id of the discoveredAssets to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discovered-assets/{id}")
    public ResponseEntity<Void> deleteDiscoveredAssets(@PathVariable Long id) {
        log.debug("REST request to delete DiscoveredAssets : {}", id);
        discoveredAssetsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
    /**
     * {@code GET  /discovered-assets/search} : get all the discovered-assets on given filters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discovered-assets in body.
     */
    @GetMapping("/discovered-assets/search")
    public List<DiscoveredAssets> search(@RequestParam Map<String, String> filter) throws IOException {
        log.debug("REST request to get all discovered-assets on given filters");
        return discoveredAssetsService.search(filter);
    }
}
