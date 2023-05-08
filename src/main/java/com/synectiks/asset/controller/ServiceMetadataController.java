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

import com.synectiks.asset.business.domain.ServiceMetadata;
import com.synectiks.asset.business.service.ServiceMetadataService;
import com.synectiks.asset.repository.ServiceMetadataRepository;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ServiceMetadataController {

	private final Logger logger = LoggerFactory.getLogger(ServiceMetadataController.class);

    private static final String ENTITY_NAME = "ServiceMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private ServiceMetadataService serviceMetadataService;

    @Autowired
    private ServiceMetadataRepository serviceMetadataRepository;

    /**
     * {@code POST  /service-metadata} : Create a new service_metadata.
     *
     * @param service_metadata the service_metadata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new service_metadata, or with status {@code 400 (Bad Request)} if the service_metadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-metadata")
    public ResponseEntity<ServiceMetadata> createServiceMetadata(@Valid @RequestBody ServiceMetadata serviceMetadata) throws URISyntaxException {
        logger.debug("REST request to save ServiceMetadata : {}", serviceMetadata);
        if (serviceMetadata.getId() != null) {
            throw new BadRequestAlertException("A new serviceMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceMetadata result = serviceMetadataService.save(serviceMetadata);
        return ResponseEntity
            .created(new URI("/api/service-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-metadata/:id} : Updates an existing ServiceMetadata.
     *
     * @param id the id of the ServiceMetadata to save.
     * @param ServiceMetadata the ServiceMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ServiceMetadata,
     * or with status {@code 400 (Bad Request)} if the ServiceMetadata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ServiceMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-metadata/{id}")
    public ResponseEntity<ServiceMetadata> updateServiceMetadatan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceMetadata serviceMetadata
    ) throws URISyntaxException {
        logger.debug("REST request to update ServiceMetadata : {}, {}", id, serviceMetadata);
        if (serviceMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceMetadata result = serviceMetadataService.save(serviceMetadata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceMetadata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /service-metadata/:id} : Partial updates given fields of an existing ServiceMetadata, field will ignore if it is null
     *
     * @param id the id of the ServiceMetadata to save.
     * @param ServiceMetadata the ServiceMetadata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ServiceMetadata,
     * or with status {@code 400 (Bad Request)} if the ServiceMetadata is not valid,
     * or with status {@code 404 (Not Found)} if the ServiceMetadata is not found,
     * or with status {@code 500 (Internal Server Error)} if the ServiceMetadata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceMetadata> partialUpdateServiceMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceMetadata serviceMetadata
    ) throws URISyntaxException {
        logger.debug("REST request to partial update ServiceMetadata partially : {}, {}", id, serviceMetadata);
        if (serviceMetadata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceMetadata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceMetadata> result = serviceMetadataService.partialUpdate(serviceMetadata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceMetadata.getId().toString())
        );
    }

    /**
     * {@code GET  /service-metadata} : get all the ServiceMetadatas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ServiceMetadatas in body.
     * @throws IOException 
     */
    @GetMapping("/service-metadata")
    public List<ServiceMetadata> getAllServiceMetadatas() throws IOException {
        logger.debug("REST request to get all ServiceMetadatas");
        return serviceMetadataService.findAll();
    }

    /**
     * {@code GET  /service-metadata/:id} : get the "id" ServiceMetadata.
     *
     * @param id the id of the ServiceMetadata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ServiceMetadata, or with status {@code 404 (Not Found)}.
     * @throws IOException 
     */
    @GetMapping("/service-metadata/{id}")
    public ResponseEntity<ServiceMetadata> getServiceMetadata(@PathVariable Long id) throws IOException {
        logger.debug("REST request to get ServiceMetadata : {}", id);
        Optional<ServiceMetadata> organization = serviceMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organization);
    }

    /**
     * {@code DELETE  /service-metadata/:id} : delete the "id" ServiceMetadata.
     *
     * @param id the id of the ServiceMetadata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-metadata/{id}")
    public ResponseEntity<Void> deleteServiceMetadata(@PathVariable Long id) {
        logger.debug("REST request to delete ServiceMetadata : {}", id);
        serviceMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /service-metadata/search} : get all the ServiceMetadatas on given filters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ServiceMetadatas in body.
     */
    @GetMapping("/service-metadata/search")
    public List<ServiceMetadata> search(@RequestParam Map<String, String> filter) throws IOException {
        logger.debug("REST request to get all ServiceMetadatas on given filters");
        return serviceMetadataService.search(filter);
    }

}
