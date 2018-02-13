package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.TresorerieService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.TresorerieDTO;
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
 * REST controller for managing Tresorerie.
 */
@RestController
@RequestMapping("/api")
public class TresorerieResource {

    private final Logger log = LoggerFactory.getLogger(TresorerieResource.class);

    private static final String ENTITY_NAME = "tresorerie";

    private final TresorerieService tresorerieService;

    public TresorerieResource(TresorerieService tresorerieService) {
        this.tresorerieService = tresorerieService;
    }

    /**
     * POST  /tresoreries : Create a new tresorerie.
     *
     * @param tresorerieDTO the tresorerieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tresorerieDTO, or with status 400 (Bad Request) if the tresorerie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tresoreries")
    @Timed
    public ResponseEntity<TresorerieDTO> createTresorerie(@RequestBody TresorerieDTO tresorerieDTO) throws URISyntaxException {
        log.debug("REST request to save Tresorerie : {}", tresorerieDTO);
        if (tresorerieDTO.getId() != null) {
            throw new BadRequestAlertException("A new tresorerie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TresorerieDTO result = tresorerieService.save(tresorerieDTO);
        return ResponseEntity.created(new URI("/api/tresoreries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tresoreries : Updates an existing tresorerie.
     *
     * @param tresorerieDTO the tresorerieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tresorerieDTO,
     * or with status 400 (Bad Request) if the tresorerieDTO is not valid,
     * or with status 500 (Internal Server Error) if the tresorerieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tresoreries")
    @Timed
    public ResponseEntity<TresorerieDTO> updateTresorerie(@RequestBody TresorerieDTO tresorerieDTO) throws URISyntaxException {
        log.debug("REST request to update Tresorerie : {}", tresorerieDTO);
        if (tresorerieDTO.getId() == null) {
            return createTresorerie(tresorerieDTO);
        }
        TresorerieDTO result = tresorerieService.save(tresorerieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tresorerieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tresoreries : get all the tresoreries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tresoreries in body
     */
    @GetMapping("/tresoreries")
    @Timed
    public List<TresorerieDTO> getAllTresoreries() {
        log.debug("REST request to get all Tresoreries");
        return tresorerieService.findAll();
        }

    /**
     * GET  /tresoreries/:id : get the "id" tresorerie.
     *
     * @param id the id of the tresorerieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tresorerieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tresoreries/{id}")
    @Timed
    public ResponseEntity<TresorerieDTO> getTresorerie(@PathVariable Long id) {
        log.debug("REST request to get Tresorerie : {}", id);
        TresorerieDTO tresorerieDTO = tresorerieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tresorerieDTO));
    }

    /**
     * DELETE  /tresoreries/:id : delete the "id" tresorerie.
     *
     * @param id the id of the tresorerieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tresoreries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTresorerie(@PathVariable Long id) {
        log.debug("REST request to delete Tresorerie : {}", id);
        tresorerieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
