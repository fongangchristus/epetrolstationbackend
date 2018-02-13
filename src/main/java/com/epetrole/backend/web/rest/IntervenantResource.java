package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.IntervenantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.web.rest.util.PaginationUtil;
import com.epetrole.backend.service.dto.IntervenantDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Intervenant.
 */
@RestController
@RequestMapping("/api")
public class IntervenantResource {

    private final Logger log = LoggerFactory.getLogger(IntervenantResource.class);

    private static final String ENTITY_NAME = "intervenant";

    private final IntervenantService intervenantService;

    public IntervenantResource(IntervenantService intervenantService) {
        this.intervenantService = intervenantService;
    }

    /**
     * POST  /intervenants : Create a new intervenant.
     *
     * @param intervenantDTO the intervenantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intervenantDTO, or with status 400 (Bad Request) if the intervenant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intervenants")
    @Timed
    public ResponseEntity<IntervenantDTO> createIntervenant(@RequestBody IntervenantDTO intervenantDTO) throws URISyntaxException {
        log.debug("REST request to save Intervenant : {}", intervenantDTO);
        if (intervenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new intervenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntervenantDTO result = intervenantService.save(intervenantDTO);
        return ResponseEntity.created(new URI("/api/intervenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intervenants : Updates an existing intervenant.
     *
     * @param intervenantDTO the intervenantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intervenantDTO,
     * or with status 400 (Bad Request) if the intervenantDTO is not valid,
     * or with status 500 (Internal Server Error) if the intervenantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intervenants")
    @Timed
    public ResponseEntity<IntervenantDTO> updateIntervenant(@RequestBody IntervenantDTO intervenantDTO) throws URISyntaxException {
        log.debug("REST request to update Intervenant : {}", intervenantDTO);
        if (intervenantDTO.getId() == null) {
            return createIntervenant(intervenantDTO);
        }
        IntervenantDTO result = intervenantService.save(intervenantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intervenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intervenants : get all the intervenants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of intervenants in body
     */
    @GetMapping("/intervenants")
    @Timed
    public ResponseEntity<List<IntervenantDTO>> getAllIntervenants(Pageable pageable) {
        log.debug("REST request to get a page of Intervenants");
        Page<IntervenantDTO> page = intervenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/intervenants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /intervenants/:id : get the "id" intervenant.
     *
     * @param id the id of the intervenantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intervenantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/intervenants/{id}")
    @Timed
    public ResponseEntity<IntervenantDTO> getIntervenant(@PathVariable Long id) {
        log.debug("REST request to get Intervenant : {}", id);
        IntervenantDTO intervenantDTO = intervenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(intervenantDTO));
    }

    /**
     * DELETE  /intervenants/:id : delete the "id" intervenant.
     *
     * @param id the id of the intervenantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intervenants/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntervenant(@PathVariable Long id) {
        log.debug("REST request to delete Intervenant : {}", id);
        intervenantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
