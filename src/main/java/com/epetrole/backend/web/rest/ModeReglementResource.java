package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.ModeReglementService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.ModeReglementDTO;
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
 * REST controller for managing ModeReglement.
 */
@RestController
@RequestMapping("/api")
public class ModeReglementResource {

    private final Logger log = LoggerFactory.getLogger(ModeReglementResource.class);

    private static final String ENTITY_NAME = "modeReglement";

    private final ModeReglementService modeReglementService;

    public ModeReglementResource(ModeReglementService modeReglementService) {
        this.modeReglementService = modeReglementService;
    }

    /**
     * POST  /mode-reglements : Create a new modeReglement.
     *
     * @param modeReglementDTO the modeReglementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modeReglementDTO, or with status 400 (Bad Request) if the modeReglement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mode-reglements")
    @Timed
    public ResponseEntity<ModeReglementDTO> createModeReglement(@RequestBody ModeReglementDTO modeReglementDTO) throws URISyntaxException {
        log.debug("REST request to save ModeReglement : {}", modeReglementDTO);
        if (modeReglementDTO.getId() != null) {
            throw new BadRequestAlertException("A new modeReglement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeReglementDTO result = modeReglementService.save(modeReglementDTO);
        return ResponseEntity.created(new URI("/api/mode-reglements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mode-reglements : Updates an existing modeReglement.
     *
     * @param modeReglementDTO the modeReglementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modeReglementDTO,
     * or with status 400 (Bad Request) if the modeReglementDTO is not valid,
     * or with status 500 (Internal Server Error) if the modeReglementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mode-reglements")
    @Timed
    public ResponseEntity<ModeReglementDTO> updateModeReglement(@RequestBody ModeReglementDTO modeReglementDTO) throws URISyntaxException {
        log.debug("REST request to update ModeReglement : {}", modeReglementDTO);
        if (modeReglementDTO.getId() == null) {
            return createModeReglement(modeReglementDTO);
        }
        ModeReglementDTO result = modeReglementService.save(modeReglementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modeReglementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mode-reglements : get all the modeReglements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modeReglements in body
     */
    @GetMapping("/mode-reglements")
    @Timed
    public List<ModeReglementDTO> getAllModeReglements() {
        log.debug("REST request to get all ModeReglements");
        return modeReglementService.findAll();
        }

    /**
     * GET  /mode-reglements/:id : get the "id" modeReglement.
     *
     * @param id the id of the modeReglementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modeReglementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mode-reglements/{id}")
    @Timed
    public ResponseEntity<ModeReglementDTO> getModeReglement(@PathVariable Long id) {
        log.debug("REST request to get ModeReglement : {}", id);
        ModeReglementDTO modeReglementDTO = modeReglementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modeReglementDTO));
    }

    /**
     * DELETE  /mode-reglements/:id : delete the "id" modeReglement.
     *
     * @param id the id of the modeReglementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mode-reglements/{id}")
    @Timed
    public ResponseEntity<Void> deleteModeReglement(@PathVariable Long id) {
        log.debug("REST request to delete ModeReglement : {}", id);
        modeReglementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
