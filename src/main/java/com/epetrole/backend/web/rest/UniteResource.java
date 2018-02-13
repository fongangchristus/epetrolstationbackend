package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.UniteService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.UniteDTO;
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
 * REST controller for managing Unite.
 */
@RestController
@RequestMapping("/api")
public class UniteResource {

    private final Logger log = LoggerFactory.getLogger(UniteResource.class);

    private static final String ENTITY_NAME = "unite";

    private final UniteService uniteService;

    public UniteResource(UniteService uniteService) {
        this.uniteService = uniteService;
    }

    /**
     * POST  /unites : Create a new unite.
     *
     * @param uniteDTO the uniteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uniteDTO, or with status 400 (Bad Request) if the unite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unites")
    @Timed
    public ResponseEntity<UniteDTO> createUnite(@RequestBody UniteDTO uniteDTO) throws URISyntaxException {
        log.debug("REST request to save Unite : {}", uniteDTO);
        if (uniteDTO.getId() != null) {
            throw new BadRequestAlertException("A new unite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniteDTO result = uniteService.save(uniteDTO);
        return ResponseEntity.created(new URI("/api/unites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unites : Updates an existing unite.
     *
     * @param uniteDTO the uniteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uniteDTO,
     * or with status 400 (Bad Request) if the uniteDTO is not valid,
     * or with status 500 (Internal Server Error) if the uniteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unites")
    @Timed
    public ResponseEntity<UniteDTO> updateUnite(@RequestBody UniteDTO uniteDTO) throws URISyntaxException {
        log.debug("REST request to update Unite : {}", uniteDTO);
        if (uniteDTO.getId() == null) {
            return createUnite(uniteDTO);
        }
        UniteDTO result = uniteService.save(uniteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uniteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unites : get all the unites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unites in body
     */
    @GetMapping("/unites")
    @Timed
    public List<UniteDTO> getAllUnites() {
        log.debug("REST request to get all Unites");
        return uniteService.findAll();
        }

    /**
     * GET  /unites/:id : get the "id" unite.
     *
     * @param id the id of the uniteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uniteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/unites/{id}")
    @Timed
    public ResponseEntity<UniteDTO> getUnite(@PathVariable Long id) {
        log.debug("REST request to get Unite : {}", id);
        UniteDTO uniteDTO = uniteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uniteDTO));
    }

    /**
     * DELETE  /unites/:id : delete the "id" unite.
     *
     * @param id the id of the uniteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unites/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnite(@PathVariable Long id) {
        log.debug("REST request to delete Unite : {}", id);
        uniteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
