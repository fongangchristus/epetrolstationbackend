package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.TypeIntervenantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.TypeIntervenantDTO;
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
 * REST controller for managing TypeIntervenant.
 */
@RestController
@RequestMapping("/api")
public class TypeIntervenantResource {

    private final Logger log = LoggerFactory.getLogger(TypeIntervenantResource.class);

    private static final String ENTITY_NAME = "typeIntervenant";

    private final TypeIntervenantService typeIntervenantService;

    public TypeIntervenantResource(TypeIntervenantService typeIntervenantService) {
        this.typeIntervenantService = typeIntervenantService;
    }

    /**
     * POST  /type-intervenants : Create a new typeIntervenant.
     *
     * @param typeIntervenantDTO the typeIntervenantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeIntervenantDTO, or with status 400 (Bad Request) if the typeIntervenant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-intervenants")
    @Timed
    public ResponseEntity<TypeIntervenantDTO> createTypeIntervenant(@RequestBody TypeIntervenantDTO typeIntervenantDTO) throws URISyntaxException {
        log.debug("REST request to save TypeIntervenant : {}", typeIntervenantDTO);
        if (typeIntervenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeIntervenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeIntervenantDTO result = typeIntervenantService.save(typeIntervenantDTO);
        return ResponseEntity.created(new URI("/api/type-intervenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-intervenants : Updates an existing typeIntervenant.
     *
     * @param typeIntervenantDTO the typeIntervenantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeIntervenantDTO,
     * or with status 400 (Bad Request) if the typeIntervenantDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeIntervenantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-intervenants")
    @Timed
    public ResponseEntity<TypeIntervenantDTO> updateTypeIntervenant(@RequestBody TypeIntervenantDTO typeIntervenantDTO) throws URISyntaxException {
        log.debug("REST request to update TypeIntervenant : {}", typeIntervenantDTO);
        if (typeIntervenantDTO.getId() == null) {
            return createTypeIntervenant(typeIntervenantDTO);
        }
        TypeIntervenantDTO result = typeIntervenantService.save(typeIntervenantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeIntervenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-intervenants : get all the typeIntervenants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeIntervenants in body
     */
    @GetMapping("/type-intervenants")
    @Timed
    public List<TypeIntervenantDTO> getAllTypeIntervenants() {
        log.debug("REST request to get all TypeIntervenants");
        return typeIntervenantService.findAll();
        }

    /**
     * GET  /type-intervenants/:id : get the "id" typeIntervenant.
     *
     * @param id the id of the typeIntervenantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeIntervenantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-intervenants/{id}")
    @Timed
    public ResponseEntity<TypeIntervenantDTO> getTypeIntervenant(@PathVariable Long id) {
        log.debug("REST request to get TypeIntervenant : {}", id);
        TypeIntervenantDTO typeIntervenantDTO = typeIntervenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeIntervenantDTO));
    }

    /**
     * DELETE  /type-intervenants/:id : delete the "id" typeIntervenant.
     *
     * @param id the id of the typeIntervenantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-intervenants/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeIntervenant(@PathVariable Long id) {
        log.debug("REST request to delete TypeIntervenant : {}", id);
        typeIntervenantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
