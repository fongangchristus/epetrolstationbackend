package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.EntreeProduitService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.EntreeProduitDTO;
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
 * REST controller for managing EntreeProduit.
 */
@RestController
@RequestMapping("/api")
public class EntreeProduitResource {

    private final Logger log = LoggerFactory.getLogger(EntreeProduitResource.class);

    private static final String ENTITY_NAME = "entreeProduit";

    private final EntreeProduitService entreeProduitService;

    public EntreeProduitResource(EntreeProduitService entreeProduitService) {
        this.entreeProduitService = entreeProduitService;
    }

    /**
     * POST  /entree-produits : Create a new entreeProduit.
     *
     * @param entreeProduitDTO the entreeProduitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreeProduitDTO, or with status 400 (Bad Request) if the entreeProduit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entree-produits")
    @Timed
    public ResponseEntity<EntreeProduitDTO> createEntreeProduit(@RequestBody EntreeProduitDTO entreeProduitDTO) throws URISyntaxException {
        log.debug("REST request to save EntreeProduit : {}", entreeProduitDTO);
        if (entreeProduitDTO.getId() != null) {
            throw new BadRequestAlertException("A new entreeProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntreeProduitDTO result = entreeProduitService.save(entreeProduitDTO);
        return ResponseEntity.created(new URI("/api/entree-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entree-produits : Updates an existing entreeProduit.
     *
     * @param entreeProduitDTO the entreeProduitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreeProduitDTO,
     * or with status 400 (Bad Request) if the entreeProduitDTO is not valid,
     * or with status 500 (Internal Server Error) if the entreeProduitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entree-produits")
    @Timed
    public ResponseEntity<EntreeProduitDTO> updateEntreeProduit(@RequestBody EntreeProduitDTO entreeProduitDTO) throws URISyntaxException {
        log.debug("REST request to update EntreeProduit : {}", entreeProduitDTO);
        if (entreeProduitDTO.getId() == null) {
            return createEntreeProduit(entreeProduitDTO);
        }
        EntreeProduitDTO result = entreeProduitService.save(entreeProduitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entreeProduitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entree-produits : get all the entreeProduits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entreeProduits in body
     */
    @GetMapping("/entree-produits")
    @Timed
    public List<EntreeProduitDTO> getAllEntreeProduits() {
        log.debug("REST request to get all EntreeProduits");
        return entreeProduitService.findAll();
        }

    /**
     * GET  /entree-produits/:id : get the "id" entreeProduit.
     *
     * @param id the id of the entreeProduitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreeProduitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entree-produits/{id}")
    @Timed
    public ResponseEntity<EntreeProduitDTO> getEntreeProduit(@PathVariable Long id) {
        log.debug("REST request to get EntreeProduit : {}", id);
        EntreeProduitDTO entreeProduitDTO = entreeProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entreeProduitDTO));
    }

    /**
     * DELETE  /entree-produits/:id : delete the "id" entreeProduit.
     *
     * @param id the id of the entreeProduitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entree-produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntreeProduit(@PathVariable Long id) {
        log.debug("REST request to delete EntreeProduit : {}", id);
        entreeProduitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
