package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.CatCarburantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.CatCarburantDTO;
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
 * REST controller for managing CatCarburant.
 */
@RestController
@RequestMapping("/api")
public class CatCarburantResource {

    private final Logger log = LoggerFactory.getLogger(CatCarburantResource.class);

    private static final String ENTITY_NAME = "catCarburant";

    private final CatCarburantService catCarburantService;

    public CatCarburantResource(CatCarburantService catCarburantService) {
        this.catCarburantService = catCarburantService;
    }

    /**
     * POST  /cat-carburants : Create a new catCarburant.
     *
     * @param catCarburantDTO the catCarburantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new catCarburantDTO, or with status 400 (Bad Request) if the catCarburant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cat-carburants")
    @Timed
    public ResponseEntity<CatCarburantDTO> createCatCarburant(@RequestBody CatCarburantDTO catCarburantDTO) throws URISyntaxException {
        log.debug("REST request to save CatCarburant : {}", catCarburantDTO);
        if (catCarburantDTO.getId() != null) {
            throw new BadRequestAlertException("A new catCarburant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatCarburantDTO result = catCarburantService.save(catCarburantDTO);
        return ResponseEntity.created(new URI("/api/cat-carburants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cat-carburants : Updates an existing catCarburant.
     *
     * @param catCarburantDTO the catCarburantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated catCarburantDTO,
     * or with status 400 (Bad Request) if the catCarburantDTO is not valid,
     * or with status 500 (Internal Server Error) if the catCarburantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cat-carburants")
    @Timed
    public ResponseEntity<CatCarburantDTO> updateCatCarburant(@RequestBody CatCarburantDTO catCarburantDTO) throws URISyntaxException {
        log.debug("REST request to update CatCarburant : {}", catCarburantDTO);
        if (catCarburantDTO.getId() == null) {
            return createCatCarburant(catCarburantDTO);
        }
        CatCarburantDTO result = catCarburantService.save(catCarburantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, catCarburantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cat-carburants : get all the catCarburants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of catCarburants in body
     */
    @GetMapping("/cat-carburants")
    @Timed
    public List<CatCarburantDTO> getAllCatCarburants() {
        log.debug("REST request to get all CatCarburants");
        return catCarburantService.findAll();
        }

    /**
     * GET  /cat-carburants/:id : get the "id" catCarburant.
     *
     * @param id the id of the catCarburantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the catCarburantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cat-carburants/{id}")
    @Timed
    public ResponseEntity<CatCarburantDTO> getCatCarburant(@PathVariable Long id) {
        log.debug("REST request to get CatCarburant : {}", id);
        CatCarburantDTO catCarburantDTO = catCarburantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(catCarburantDTO));
    }

    /**
     * DELETE  /cat-carburants/:id : delete the "id" catCarburant.
     *
     * @param id the id of the catCarburantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cat-carburants/{id}")
    @Timed
    public ResponseEntity<Void> deleteCatCarburant(@PathVariable Long id) {
        log.debug("REST request to delete CatCarburant : {}", id);
        catCarburantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
