package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.SortieProduitService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.SortieProduitDTO;
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
 * REST controller for managing SortieProduit.
 */
@RestController
@RequestMapping("/api")
public class SortieProduitResource {

    private final Logger log = LoggerFactory.getLogger(SortieProduitResource.class);

    private static final String ENTITY_NAME = "sortieProduit";

    private final SortieProduitService sortieProduitService;

    public SortieProduitResource(SortieProduitService sortieProduitService) {
        this.sortieProduitService = sortieProduitService;
    }

    /**
     * POST  /sortie-produits : Create a new sortieProduit.
     *
     * @param sortieProduitDTO the sortieProduitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sortieProduitDTO, or with status 400 (Bad Request) if the sortieProduit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sortie-produits")
    @Timed
    public ResponseEntity<SortieProduitDTO> createSortieProduit(@RequestBody SortieProduitDTO sortieProduitDTO) throws URISyntaxException {
        log.debug("REST request to save SortieProduit : {}", sortieProduitDTO);
        if (sortieProduitDTO.getId() != null) {
            throw new BadRequestAlertException("A new sortieProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SortieProduitDTO result = sortieProduitService.save(sortieProduitDTO);
        return ResponseEntity.created(new URI("/api/sortie-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sortie-produits : Updates an existing sortieProduit.
     *
     * @param sortieProduitDTO the sortieProduitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sortieProduitDTO,
     * or with status 400 (Bad Request) if the sortieProduitDTO is not valid,
     * or with status 500 (Internal Server Error) if the sortieProduitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sortie-produits")
    @Timed
    public ResponseEntity<SortieProduitDTO> updateSortieProduit(@RequestBody SortieProduitDTO sortieProduitDTO) throws URISyntaxException {
        log.debug("REST request to update SortieProduit : {}", sortieProduitDTO);
        if (sortieProduitDTO.getId() == null) {
            return createSortieProduit(sortieProduitDTO);
        }
        SortieProduitDTO result = sortieProduitService.save(sortieProduitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sortieProduitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sortie-produits : get all the sortieProduits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sortieProduits in body
     */
    @GetMapping("/sortie-produits")
    @Timed
    public List<SortieProduitDTO> getAllSortieProduits() {
        log.debug("REST request to get all SortieProduits");
        return sortieProduitService.findAll();
        }

    /**
     * GET  /sortie-produits/:id : get the "id" sortieProduit.
     *
     * @param id the id of the sortieProduitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sortieProduitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sortie-produits/{id}")
    @Timed
    public ResponseEntity<SortieProduitDTO> getSortieProduit(@PathVariable Long id) {
        log.debug("REST request to get SortieProduit : {}", id);
        SortieProduitDTO sortieProduitDTO = sortieProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sortieProduitDTO));
    }

    /**
     * DELETE  /sortie-produits/:id : delete the "id" sortieProduit.
     *
     * @param id the id of the sortieProduitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sortie-produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteSortieProduit(@PathVariable Long id) {
        log.debug("REST request to delete SortieProduit : {}", id);
        sortieProduitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
