package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.EntreeCarburantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.EntreeCarburantDTO;
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
 * REST controller for managing EntreeCarburant.
 */
@RestController
@RequestMapping("/api")
public class EntreeCarburantResource {

    private final Logger log = LoggerFactory.getLogger(EntreeCarburantResource.class);

    private static final String ENTITY_NAME = "entreeCarburant";

    private final EntreeCarburantService entreeCarburantService;

    public EntreeCarburantResource(EntreeCarburantService entreeCarburantService) {
        this.entreeCarburantService = entreeCarburantService;
    }

    /**
     * POST  /entree-carburants : Create a new entreeCarburant.
     *
     * @param entreeCarburantDTO the entreeCarburantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreeCarburantDTO, or with status 400 (Bad Request) if the entreeCarburant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entree-carburants")
    @Timed
    public ResponseEntity<EntreeCarburantDTO> createEntreeCarburant(@RequestBody EntreeCarburantDTO entreeCarburantDTO) throws URISyntaxException {
        log.debug("REST request to save EntreeCarburant : {}", entreeCarburantDTO);
        if (entreeCarburantDTO.getId() != null) {
            throw new BadRequestAlertException("A new entreeCarburant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntreeCarburantDTO result = entreeCarburantService.save(entreeCarburantDTO);
        return ResponseEntity.created(new URI("/api/entree-carburants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entree-carburants : Updates an existing entreeCarburant.
     *
     * @param entreeCarburantDTO the entreeCarburantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreeCarburantDTO,
     * or with status 400 (Bad Request) if the entreeCarburantDTO is not valid,
     * or with status 500 (Internal Server Error) if the entreeCarburantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entree-carburants")
    @Timed
    public ResponseEntity<EntreeCarburantDTO> updateEntreeCarburant(@RequestBody EntreeCarburantDTO entreeCarburantDTO) throws URISyntaxException {
        log.debug("REST request to update EntreeCarburant : {}", entreeCarburantDTO);
        if (entreeCarburantDTO.getId() == null) {
            return createEntreeCarburant(entreeCarburantDTO);
        }
        EntreeCarburantDTO result = entreeCarburantService.save(entreeCarburantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entreeCarburantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entree-carburants : get all the entreeCarburants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entreeCarburants in body
     */
    @GetMapping("/entree-carburants")
    @Timed
    public List<EntreeCarburantDTO> getAllEntreeCarburants() {
        log.debug("REST request to get all EntreeCarburants");
        return entreeCarburantService.findAll();
        }

    /**
     * GET  /entree-carburants/:id : get the "id" entreeCarburant.
     *
     * @param id the id of the entreeCarburantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreeCarburantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entree-carburants/{id}")
    @Timed
    public ResponseEntity<EntreeCarburantDTO> getEntreeCarburant(@PathVariable Long id) {
        log.debug("REST request to get EntreeCarburant : {}", id);
        EntreeCarburantDTO entreeCarburantDTO = entreeCarburantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entreeCarburantDTO));
    }

    /**
     * DELETE  /entree-carburants/:id : delete the "id" entreeCarburant.
     *
     * @param id the id of the entreeCarburantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entree-carburants/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntreeCarburant(@PathVariable Long id) {
        log.debug("REST request to delete EntreeCarburant : {}", id);
        entreeCarburantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
