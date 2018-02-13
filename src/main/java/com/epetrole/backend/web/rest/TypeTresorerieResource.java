package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.TypeTresorerieService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.TypeTresorerieDTO;
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
 * REST controller for managing TypeTresorerie.
 */
@RestController
@RequestMapping("/api")
public class TypeTresorerieResource {

    private final Logger log = LoggerFactory.getLogger(TypeTresorerieResource.class);

    private static final String ENTITY_NAME = "typeTresorerie";

    private final TypeTresorerieService typeTresorerieService;

    public TypeTresorerieResource(TypeTresorerieService typeTresorerieService) {
        this.typeTresorerieService = typeTresorerieService;
    }

    /**
     * POST  /type-tresoreries : Create a new typeTresorerie.
     *
     * @param typeTresorerieDTO the typeTresorerieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeTresorerieDTO, or with status 400 (Bad Request) if the typeTresorerie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-tresoreries")
    @Timed
    public ResponseEntity<TypeTresorerieDTO> createTypeTresorerie(@RequestBody TypeTresorerieDTO typeTresorerieDTO) throws URISyntaxException {
        log.debug("REST request to save TypeTresorerie : {}", typeTresorerieDTO);
        if (typeTresorerieDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeTresorerie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTresorerieDTO result = typeTresorerieService.save(typeTresorerieDTO);
        return ResponseEntity.created(new URI("/api/type-tresoreries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-tresoreries : Updates an existing typeTresorerie.
     *
     * @param typeTresorerieDTO the typeTresorerieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeTresorerieDTO,
     * or with status 400 (Bad Request) if the typeTresorerieDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeTresorerieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-tresoreries")
    @Timed
    public ResponseEntity<TypeTresorerieDTO> updateTypeTresorerie(@RequestBody TypeTresorerieDTO typeTresorerieDTO) throws URISyntaxException {
        log.debug("REST request to update TypeTresorerie : {}", typeTresorerieDTO);
        if (typeTresorerieDTO.getId() == null) {
            return createTypeTresorerie(typeTresorerieDTO);
        }
        TypeTresorerieDTO result = typeTresorerieService.save(typeTresorerieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeTresorerieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-tresoreries : get all the typeTresoreries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeTresoreries in body
     */
    @GetMapping("/type-tresoreries")
    @Timed
    public List<TypeTresorerieDTO> getAllTypeTresoreries() {
        log.debug("REST request to get all TypeTresoreries");
        return typeTresorerieService.findAll();
        }

    /**
     * GET  /type-tresoreries/:id : get the "id" typeTresorerie.
     *
     * @param id the id of the typeTresorerieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeTresorerieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-tresoreries/{id}")
    @Timed
    public ResponseEntity<TypeTresorerieDTO> getTypeTresorerie(@PathVariable Long id) {
        log.debug("REST request to get TypeTresorerie : {}", id);
        TypeTresorerieDTO typeTresorerieDTO = typeTresorerieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeTresorerieDTO));
    }

    /**
     * DELETE  /type-tresoreries/:id : delete the "id" typeTresorerie.
     *
     * @param id the id of the typeTresorerieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-tresoreries/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeTresorerie(@PathVariable Long id) {
        log.debug("REST request to delete TypeTresorerie : {}", id);
        typeTresorerieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
