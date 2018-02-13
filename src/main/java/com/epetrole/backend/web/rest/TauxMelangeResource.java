package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.TauxMelangeService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.TauxMelangeDTO;
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
 * REST controller for managing TauxMelange.
 */
@RestController
@RequestMapping("/api")
public class TauxMelangeResource {

    private final Logger log = LoggerFactory.getLogger(TauxMelangeResource.class);

    private static final String ENTITY_NAME = "tauxMelange";

    private final TauxMelangeService tauxMelangeService;

    public TauxMelangeResource(TauxMelangeService tauxMelangeService) {
        this.tauxMelangeService = tauxMelangeService;
    }

    /**
     * POST  /taux-melanges : Create a new tauxMelange.
     *
     * @param tauxMelangeDTO the tauxMelangeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tauxMelangeDTO, or with status 400 (Bad Request) if the tauxMelange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taux-melanges")
    @Timed
    public ResponseEntity<TauxMelangeDTO> createTauxMelange(@RequestBody TauxMelangeDTO tauxMelangeDTO) throws URISyntaxException {
        log.debug("REST request to save TauxMelange : {}", tauxMelangeDTO);
        if (tauxMelangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tauxMelange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TauxMelangeDTO result = tauxMelangeService.save(tauxMelangeDTO);
        return ResponseEntity.created(new URI("/api/taux-melanges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taux-melanges : Updates an existing tauxMelange.
     *
     * @param tauxMelangeDTO the tauxMelangeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tauxMelangeDTO,
     * or with status 400 (Bad Request) if the tauxMelangeDTO is not valid,
     * or with status 500 (Internal Server Error) if the tauxMelangeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taux-melanges")
    @Timed
    public ResponseEntity<TauxMelangeDTO> updateTauxMelange(@RequestBody TauxMelangeDTO tauxMelangeDTO) throws URISyntaxException {
        log.debug("REST request to update TauxMelange : {}", tauxMelangeDTO);
        if (tauxMelangeDTO.getId() == null) {
            return createTauxMelange(tauxMelangeDTO);
        }
        TauxMelangeDTO result = tauxMelangeService.save(tauxMelangeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tauxMelangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taux-melanges : get all the tauxMelanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tauxMelanges in body
     */
    @GetMapping("/taux-melanges")
    @Timed
    public List<TauxMelangeDTO> getAllTauxMelanges() {
        log.debug("REST request to get all TauxMelanges");
        return tauxMelangeService.findAll();
        }

    /**
     * GET  /taux-melanges/:id : get the "id" tauxMelange.
     *
     * @param id the id of the tauxMelangeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tauxMelangeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/taux-melanges/{id}")
    @Timed
    public ResponseEntity<TauxMelangeDTO> getTauxMelange(@PathVariable Long id) {
        log.debug("REST request to get TauxMelange : {}", id);
        TauxMelangeDTO tauxMelangeDTO = tauxMelangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tauxMelangeDTO));
    }

    /**
     * DELETE  /taux-melanges/:id : delete the "id" tauxMelange.
     *
     * @param id the id of the tauxMelangeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taux-melanges/{id}")
    @Timed
    public ResponseEntity<Void> deleteTauxMelange(@PathVariable Long id) {
        log.debug("REST request to delete TauxMelange : {}", id);
        tauxMelangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
