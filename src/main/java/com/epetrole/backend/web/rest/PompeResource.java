package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.PompeService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.PompeDTO;
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
 * REST controller for managing Pompe.
 */
@RestController
@RequestMapping("/api")
public class PompeResource {

    private final Logger log = LoggerFactory.getLogger(PompeResource.class);

    private static final String ENTITY_NAME = "pompe";

    private final PompeService pompeService;

    public PompeResource(PompeService pompeService) {
        this.pompeService = pompeService;
    }

    /**
     * POST  /pompes : Create a new pompe.
     *
     * @param pompeDTO the pompeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pompeDTO, or with status 400 (Bad Request) if the pompe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pompes")
    @Timed
    public ResponseEntity<PompeDTO> createPompe(@RequestBody PompeDTO pompeDTO) throws URISyntaxException {
        log.debug("REST request to save Pompe : {}", pompeDTO);
        if (pompeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pompe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PompeDTO result = pompeService.save(pompeDTO);
        return ResponseEntity.created(new URI("/api/pompes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pompes : Updates an existing pompe.
     *
     * @param pompeDTO the pompeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pompeDTO,
     * or with status 400 (Bad Request) if the pompeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pompeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pompes")
    @Timed
    public ResponseEntity<PompeDTO> updatePompe(@RequestBody PompeDTO pompeDTO) throws URISyntaxException {
        log.debug("REST request to update Pompe : {}", pompeDTO);
        if (pompeDTO.getId() == null) {
            return createPompe(pompeDTO);
        }
        PompeDTO result = pompeService.save(pompeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pompeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pompes : get all the pompes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pompes in body
     */
    @GetMapping("/pompes")
    @Timed
    public List<PompeDTO> getAllPompes() {
        log.debug("REST request to get all Pompes");
        return pompeService.findAll();
        }

    /**
     * GET  /pompes/:id : get the "id" pompe.
     *
     * @param id the id of the pompeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pompeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pompes/{id}")
    @Timed
    public ResponseEntity<PompeDTO> getPompe(@PathVariable Long id) {
        log.debug("REST request to get Pompe : {}", id);
        PompeDTO pompeDTO = pompeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pompeDTO));
    }

    /**
     * DELETE  /pompes/:id : delete the "id" pompe.
     *
     * @param id the id of the pompeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pompes/{id}")
    @Timed
    public ResponseEntity<Void> deletePompe(@PathVariable Long id) {
        log.debug("REST request to delete Pompe : {}", id);
        pompeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
