package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.ReservoirService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.ReservoirDTO;
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
 * REST controller for managing Reservoir.
 */
@RestController
@RequestMapping("/api")
public class ReservoirResource {

    private final Logger log = LoggerFactory.getLogger(ReservoirResource.class);

    private static final String ENTITY_NAME = "reservoir";

    private final ReservoirService reservoirService;

    public ReservoirResource(ReservoirService reservoirService) {
        this.reservoirService = reservoirService;
    }

    /**
     * POST  /reservoirs : Create a new reservoir.
     *
     * @param reservoirDTO the reservoirDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservoirDTO, or with status 400 (Bad Request) if the reservoir has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservoirs")
    @Timed
    public ResponseEntity<ReservoirDTO> createReservoir(@RequestBody ReservoirDTO reservoirDTO) throws URISyntaxException {
        log.debug("REST request to save Reservoir : {}", reservoirDTO);
        if (reservoirDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservoir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservoirDTO result = reservoirService.save(reservoirDTO);
        return ResponseEntity.created(new URI("/api/reservoirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservoirs : Updates an existing reservoir.
     *
     * @param reservoirDTO the reservoirDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservoirDTO,
     * or with status 400 (Bad Request) if the reservoirDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservoirDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservoirs")
    @Timed
    public ResponseEntity<ReservoirDTO> updateReservoir(@RequestBody ReservoirDTO reservoirDTO) throws URISyntaxException {
        log.debug("REST request to update Reservoir : {}", reservoirDTO);
        if (reservoirDTO.getId() == null) {
            return createReservoir(reservoirDTO);
        }
        ReservoirDTO result = reservoirService.save(reservoirDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reservoirDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reservoirs : get all the reservoirs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reservoirs in body
     */
    @GetMapping("/reservoirs")
    @Timed
    public List<ReservoirDTO> getAllReservoirs() {
        log.debug("REST request to get all Reservoirs");
        return reservoirService.findAll();
        }

    /**
     * GET  /reservoirs/:id : get the "id" reservoir.
     *
     * @param id the id of the reservoirDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservoirDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reservoirs/{id}")
    @Timed
    public ResponseEntity<ReservoirDTO> getReservoir(@PathVariable Long id) {
        log.debug("REST request to get Reservoir : {}", id);
        ReservoirDTO reservoirDTO = reservoirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reservoirDTO));
    }

    /**
     * DELETE  /reservoirs/:id : delete the "id" reservoir.
     *
     * @param id the id of the reservoirDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservoirs/{id}")
    @Timed
    public ResponseEntity<Void> deleteReservoir(@PathVariable Long id) {
        log.debug("REST request to delete Reservoir : {}", id);
        reservoirService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
