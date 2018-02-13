package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.CarburantService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.CarburantDTO;
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
 * REST controller for managing Carburant.
 */
@RestController
@RequestMapping("/api")
public class CarburantResource {

    private final Logger log = LoggerFactory.getLogger(CarburantResource.class);

    private static final String ENTITY_NAME = "carburant";

    private final CarburantService carburantService;

    public CarburantResource(CarburantService carburantService) {
        this.carburantService = carburantService;
    }

    /**
     * POST  /carburants : Create a new carburant.
     *
     * @param carburantDTO the carburantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carburantDTO, or with status 400 (Bad Request) if the carburant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carburants")
    @Timed
    public ResponseEntity<CarburantDTO> createCarburant(@RequestBody CarburantDTO carburantDTO) throws URISyntaxException {
        log.debug("REST request to save Carburant : {}", carburantDTO);
        if (carburantDTO.getId() != null) {
            throw new BadRequestAlertException("A new carburant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarburantDTO result = carburantService.save(carburantDTO);
        return ResponseEntity.created(new URI("/api/carburants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carburants : Updates an existing carburant.
     *
     * @param carburantDTO the carburantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carburantDTO,
     * or with status 400 (Bad Request) if the carburantDTO is not valid,
     * or with status 500 (Internal Server Error) if the carburantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carburants")
    @Timed
    public ResponseEntity<CarburantDTO> updateCarburant(@RequestBody CarburantDTO carburantDTO) throws URISyntaxException {
        log.debug("REST request to update Carburant : {}", carburantDTO);
        if (carburantDTO.getId() == null) {
            return createCarburant(carburantDTO);
        }
        CarburantDTO result = carburantService.save(carburantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carburantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carburants : get all the carburants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carburants in body
     */
    @GetMapping("/carburants")
    @Timed
    public List<CarburantDTO> getAllCarburants() {
        log.debug("REST request to get all Carburants");
        return carburantService.findAll();
        }

    /**
     * GET  /carburants/:id : get the "id" carburant.
     *
     * @param id the id of the carburantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carburantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carburants/{id}")
    @Timed
    public ResponseEntity<CarburantDTO> getCarburant(@PathVariable Long id) {
        log.debug("REST request to get Carburant : {}", id);
        CarburantDTO carburantDTO = carburantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carburantDTO));
    }

    /**
     * DELETE  /carburants/:id : delete the "id" carburant.
     *
     * @param id the id of the carburantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carburants/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarburant(@PathVariable Long id) {
        log.debug("REST request to delete Carburant : {}", id);
        carburantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
