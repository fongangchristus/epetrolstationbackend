package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.TvaService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.TvaDTO;
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
 * REST controller for managing Tva.
 */
@RestController
@RequestMapping("/api")
public class TvaResource {

    private final Logger log = LoggerFactory.getLogger(TvaResource.class);

    private static final String ENTITY_NAME = "tva";

    private final TvaService tvaService;

    public TvaResource(TvaService tvaService) {
        this.tvaService = tvaService;
    }

    /**
     * POST  /tvas : Create a new tva.
     *
     * @param tvaDTO the tvaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tvaDTO, or with status 400 (Bad Request) if the tva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tvas")
    @Timed
    public ResponseEntity<TvaDTO> createTva(@RequestBody TvaDTO tvaDTO) throws URISyntaxException {
        log.debug("REST request to save Tva : {}", tvaDTO);
        if (tvaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity.created(new URI("/api/tvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tvas : Updates an existing tva.
     *
     * @param tvaDTO the tvaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tvaDTO,
     * or with status 400 (Bad Request) if the tvaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tvaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tvas")
    @Timed
    public ResponseEntity<TvaDTO> updateTva(@RequestBody TvaDTO tvaDTO) throws URISyntaxException {
        log.debug("REST request to update Tva : {}", tvaDTO);
        if (tvaDTO.getId() == null) {
            return createTva(tvaDTO);
        }
        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tvas : get all the tvas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tvas in body
     */
    @GetMapping("/tvas")
    @Timed
    public List<TvaDTO> getAllTvas() {
        log.debug("REST request to get all Tvas");
        return tvaService.findAll();
        }

    /**
     * GET  /tvas/:id : get the "id" tva.
     *
     * @param id the id of the tvaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tvaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tvas/{id}")
    @Timed
    public ResponseEntity<TvaDTO> getTva(@PathVariable Long id) {
        log.debug("REST request to get Tva : {}", id);
        TvaDTO tvaDTO = tvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tvaDTO));
    }

    /**
     * DELETE  /tvas/:id : delete the "id" tva.
     *
     * @param id the id of the tvaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tvas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTva(@PathVariable Long id) {
        log.debug("REST request to delete Tva : {}", id);
        tvaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
