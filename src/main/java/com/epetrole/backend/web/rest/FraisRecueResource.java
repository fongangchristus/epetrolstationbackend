package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.FraisRecueService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.FraisRecueDTO;
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
 * REST controller for managing FraisRecue.
 */
@RestController
@RequestMapping("/api")
public class FraisRecueResource {

    private final Logger log = LoggerFactory.getLogger(FraisRecueResource.class);

    private static final String ENTITY_NAME = "fraisRecue";

    private final FraisRecueService fraisRecueService;

    public FraisRecueResource(FraisRecueService fraisRecueService) {
        this.fraisRecueService = fraisRecueService;
    }

    /**
     * POST  /frais-recues : Create a new fraisRecue.
     *
     * @param fraisRecueDTO the fraisRecueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fraisRecueDTO, or with status 400 (Bad Request) if the fraisRecue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/frais-recues")
    @Timed
    public ResponseEntity<FraisRecueDTO> createFraisRecue(@RequestBody FraisRecueDTO fraisRecueDTO) throws URISyntaxException {
        log.debug("REST request to save FraisRecue : {}", fraisRecueDTO);
        if (fraisRecueDTO.getId() != null) {
            throw new BadRequestAlertException("A new fraisRecue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraisRecueDTO result = fraisRecueService.save(fraisRecueDTO);
        return ResponseEntity.created(new URI("/api/frais-recues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /frais-recues : Updates an existing fraisRecue.
     *
     * @param fraisRecueDTO the fraisRecueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fraisRecueDTO,
     * or with status 400 (Bad Request) if the fraisRecueDTO is not valid,
     * or with status 500 (Internal Server Error) if the fraisRecueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/frais-recues")
    @Timed
    public ResponseEntity<FraisRecueDTO> updateFraisRecue(@RequestBody FraisRecueDTO fraisRecueDTO) throws URISyntaxException {
        log.debug("REST request to update FraisRecue : {}", fraisRecueDTO);
        if (fraisRecueDTO.getId() == null) {
            return createFraisRecue(fraisRecueDTO);
        }
        FraisRecueDTO result = fraisRecueService.save(fraisRecueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fraisRecueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /frais-recues : get all the fraisRecues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fraisRecues in body
     */
    @GetMapping("/frais-recues")
    @Timed
    public List<FraisRecueDTO> getAllFraisRecues() {
        log.debug("REST request to get all FraisRecues");
        return fraisRecueService.findAll();
        }

    /**
     * GET  /frais-recues/:id : get the "id" fraisRecue.
     *
     * @param id the id of the fraisRecueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fraisRecueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/frais-recues/{id}")
    @Timed
    public ResponseEntity<FraisRecueDTO> getFraisRecue(@PathVariable Long id) {
        log.debug("REST request to get FraisRecue : {}", id);
        FraisRecueDTO fraisRecueDTO = fraisRecueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fraisRecueDTO));
    }

    /**
     * DELETE  /frais-recues/:id : delete the "id" fraisRecue.
     *
     * @param id the id of the fraisRecueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/frais-recues/{id}")
    @Timed
    public ResponseEntity<Void> deleteFraisRecue(@PathVariable Long id) {
        log.debug("REST request to delete FraisRecue : {}", id);
        fraisRecueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
