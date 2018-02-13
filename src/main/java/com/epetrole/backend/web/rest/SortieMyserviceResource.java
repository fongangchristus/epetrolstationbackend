package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.SortieMyserviceService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.SortieMyserviceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SortieMyservice.
 */
@RestController
@RequestMapping("/api")
public class SortieMyserviceResource {

    private final Logger log = LoggerFactory.getLogger(SortieMyserviceResource.class);

    private static final String ENTITY_NAME = "sortieMyservice";

    private final SortieMyserviceService sortieMyserviceService;

    public SortieMyserviceResource(SortieMyserviceService sortieMyserviceService) {
        this.sortieMyserviceService = sortieMyserviceService;
    }

    /**
     * POST  /sortie-myservices : Create a new sortieMyservice.
     *
     * @param sortieMyserviceDTO the sortieMyserviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sortieMyserviceDTO, or with status 400 (Bad Request) if the sortieMyservice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sortie-myservices")
    @Timed
    public ResponseEntity<SortieMyserviceDTO> createSortieMyservice(@RequestBody SortieMyserviceDTO sortieMyserviceDTO) throws URISyntaxException {
        log.debug("REST request to save SortieMyservice : {}", sortieMyserviceDTO);
        if (sortieMyserviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sortieMyservice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SortieMyserviceDTO result = sortieMyserviceService.save(sortieMyserviceDTO);
        return ResponseEntity.created(new URI("/api/sortie-myservices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sortie-myservices : Updates an existing sortieMyservice.
     *
     * @param sortieMyserviceDTO the sortieMyserviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sortieMyserviceDTO,
     * or with status 400 (Bad Request) if the sortieMyserviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the sortieMyserviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sortie-myservices")
    @Timed
    public ResponseEntity<SortieMyserviceDTO> updateSortieMyservice(@RequestBody SortieMyserviceDTO sortieMyserviceDTO) throws URISyntaxException {
        log.debug("REST request to update SortieMyservice : {}", sortieMyserviceDTO);
        if (sortieMyserviceDTO.getId() == null) {
            return createSortieMyservice(sortieMyserviceDTO);
        }
        SortieMyserviceDTO result = sortieMyserviceService.save(sortieMyserviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sortieMyserviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sortie-myservices : get all the sortieMyservices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sortieMyservices in body
     */
    @GetMapping("/sortie-myservices")
    @Timed
    public List<SortieMyserviceDTO> getAllSortieMyservices() {
        log.debug("REST request to get all SortieMyservices");
        return sortieMyserviceService.findAll();
        }

    /**
     * GET  /sortie-myservices/:id : get the "id" sortieMyservice.
     *
     * @param id the id of the sortieMyserviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sortieMyserviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sortie-myservices/{id}")
    @Timed
    public ResponseEntity<SortieMyserviceDTO> getSortieMyservice(@PathVariable Long id) {
        log.debug("REST request to get SortieMyservice : {}", id);
        SortieMyserviceDTO sortieMyserviceDTO = sortieMyserviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sortieMyserviceDTO));
    }

    /**
     * DELETE  /sortie-myservices/:id : delete the "id" sortieMyservice.
     *
     * @param id the id of the sortieMyserviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sortie-myservices/{id}")
    @Timed
    public ResponseEntity<Void> deleteSortieMyservice(@PathVariable Long id) {
        log.debug("REST request to delete SortieMyservice : {}", id);
        sortieMyserviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
