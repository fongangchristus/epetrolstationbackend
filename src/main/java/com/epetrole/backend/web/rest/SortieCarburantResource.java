package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.SortieCarburantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.SortieCarburantDTO;
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
 * REST controller for managing SortieCarburant.
 */
@RestController
@RequestMapping("/api")
public class SortieCarburantResource {

    private final Logger log = LoggerFactory.getLogger(SortieCarburantResource.class);

    private static final String ENTITY_NAME = "sortieCarburant";

    private final SortieCarburantService sortieCarburantService;

    public SortieCarburantResource(SortieCarburantService sortieCarburantService) {
        this.sortieCarburantService = sortieCarburantService;
    }

    /**
     * POST  /sortie-carburants : Create a new sortieCarburant.
     *
     * @param sortieCarburantDTO the sortieCarburantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sortieCarburantDTO, or with status 400 (Bad Request) if the sortieCarburant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sortie-carburants")
    @Timed
    public ResponseEntity<SortieCarburantDTO> createSortieCarburant(@RequestBody SortieCarburantDTO sortieCarburantDTO) throws URISyntaxException {
        log.debug("REST request to save SortieCarburant : {}", sortieCarburantDTO);
        if (sortieCarburantDTO.getId() != null) {
            throw new BadRequestAlertException("A new sortieCarburant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SortieCarburantDTO result = sortieCarburantService.save(sortieCarburantDTO);
        return ResponseEntity.created(new URI("/api/sortie-carburants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sortie-carburants : Updates an existing sortieCarburant.
     *
     * @param sortieCarburantDTO the sortieCarburantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sortieCarburantDTO,
     * or with status 400 (Bad Request) if the sortieCarburantDTO is not valid,
     * or with status 500 (Internal Server Error) if the sortieCarburantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sortie-carburants")
    @Timed
    public ResponseEntity<SortieCarburantDTO> updateSortieCarburant(@RequestBody SortieCarburantDTO sortieCarburantDTO) throws URISyntaxException {
        log.debug("REST request to update SortieCarburant : {}", sortieCarburantDTO);
        if (sortieCarburantDTO.getId() == null) {
            return createSortieCarburant(sortieCarburantDTO);
        }
        SortieCarburantDTO result = sortieCarburantService.save(sortieCarburantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sortieCarburantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sortie-carburants : get all the sortieCarburants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sortieCarburants in body
     */
    @GetMapping("/sortie-carburants")
    @Timed
    public List<SortieCarburantDTO> getAllSortieCarburants() {
        log.debug("REST request to get all SortieCarburants");
        return sortieCarburantService.findAll();
        }

    /**
     * GET  /sortie-carburants/:id : get the "id" sortieCarburant.
     *
     * @param id the id of the sortieCarburantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sortieCarburantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sortie-carburants/{id}")
    @Timed
    public ResponseEntity<SortieCarburantDTO> getSortieCarburant(@PathVariable Long id) {
        log.debug("REST request to get SortieCarburant : {}", id);
        SortieCarburantDTO sortieCarburantDTO = sortieCarburantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sortieCarburantDTO));
    }

    /**
     * DELETE  /sortie-carburants/:id : delete the "id" sortieCarburant.
     *
     * @param id the id of the sortieCarburantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sortie-carburants/{id}")
    @Timed
    public ResponseEntity<Void> deleteSortieCarburant(@PathVariable Long id) {
        log.debug("REST request to delete SortieCarburant : {}", id);
        sortieCarburantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
