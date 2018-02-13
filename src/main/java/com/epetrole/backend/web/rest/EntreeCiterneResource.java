package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.EntreeCiterneService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.EntreeCiterneDTO;
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
 * REST controller for managing EntreeCiterne.
 */
@RestController
@RequestMapping("/api")
public class EntreeCiterneResource {

    private final Logger log = LoggerFactory.getLogger(EntreeCiterneResource.class);

    private static final String ENTITY_NAME = "entreeCiterne";

    private final EntreeCiterneService entreeCiterneService;

    public EntreeCiterneResource(EntreeCiterneService entreeCiterneService) {
        this.entreeCiterneService = entreeCiterneService;
    }

    /**
     * POST  /entree-citernes : Create a new entreeCiterne.
     *
     * @param entreeCiterneDTO the entreeCiterneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreeCiterneDTO, or with status 400 (Bad Request) if the entreeCiterne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entree-citernes")
    @Timed
    public ResponseEntity<EntreeCiterneDTO> createEntreeCiterne(@RequestBody EntreeCiterneDTO entreeCiterneDTO) throws URISyntaxException {
        log.debug("REST request to save EntreeCiterne : {}", entreeCiterneDTO);
        if (entreeCiterneDTO.getId() != null) {
            throw new BadRequestAlertException("A new entreeCiterne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntreeCiterneDTO result = entreeCiterneService.save(entreeCiterneDTO);
        return ResponseEntity.created(new URI("/api/entree-citernes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entree-citernes : Updates an existing entreeCiterne.
     *
     * @param entreeCiterneDTO the entreeCiterneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreeCiterneDTO,
     * or with status 400 (Bad Request) if the entreeCiterneDTO is not valid,
     * or with status 500 (Internal Server Error) if the entreeCiterneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entree-citernes")
    @Timed
    public ResponseEntity<EntreeCiterneDTO> updateEntreeCiterne(@RequestBody EntreeCiterneDTO entreeCiterneDTO) throws URISyntaxException {
        log.debug("REST request to update EntreeCiterne : {}", entreeCiterneDTO);
        if (entreeCiterneDTO.getId() == null) {
            return createEntreeCiterne(entreeCiterneDTO);
        }
        EntreeCiterneDTO result = entreeCiterneService.save(entreeCiterneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entreeCiterneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entree-citernes : get all the entreeCiternes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entreeCiternes in body
     */
    @GetMapping("/entree-citernes")
    @Timed
    public List<EntreeCiterneDTO> getAllEntreeCiternes() {
        log.debug("REST request to get all EntreeCiternes");
        return entreeCiterneService.findAll();
        }

    /**
     * GET  /entree-citernes/:id : get the "id" entreeCiterne.
     *
     * @param id the id of the entreeCiterneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreeCiterneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entree-citernes/{id}")
    @Timed
    public ResponseEntity<EntreeCiterneDTO> getEntreeCiterne(@PathVariable Long id) {
        log.debug("REST request to get EntreeCiterne : {}", id);
        EntreeCiterneDTO entreeCiterneDTO = entreeCiterneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entreeCiterneDTO));
    }

    /**
     * DELETE  /entree-citernes/:id : delete the "id" entreeCiterne.
     *
     * @param id the id of the entreeCiterneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entree-citernes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntreeCiterne(@PathVariable Long id) {
        log.debug("REST request to delete EntreeCiterne : {}", id);
        entreeCiterneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
