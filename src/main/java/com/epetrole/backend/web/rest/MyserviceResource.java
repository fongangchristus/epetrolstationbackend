package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.MyserviceService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.MyserviceDTO;
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
 * REST controller for managing Myservice.
 */
@RestController
@RequestMapping("/api")
public class MyserviceResource {

    private final Logger log = LoggerFactory.getLogger(MyserviceResource.class);

    private static final String ENTITY_NAME = "myservice";

    private final MyserviceService myserviceService;

    public MyserviceResource(MyserviceService myserviceService) {
        this.myserviceService = myserviceService;
    }

    /**
     * POST  /myservices : Create a new myservice.
     *
     * @param myserviceDTO the myserviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myserviceDTO, or with status 400 (Bad Request) if the myservice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/myservices")
    @Timed
    public ResponseEntity<MyserviceDTO> createMyservice(@RequestBody MyserviceDTO myserviceDTO) throws URISyntaxException {
        log.debug("REST request to save Myservice : {}", myserviceDTO);
        if (myserviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new myservice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyserviceDTO result = myserviceService.save(myserviceDTO);
        return ResponseEntity.created(new URI("/api/myservices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /myservices : Updates an existing myservice.
     *
     * @param myserviceDTO the myserviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myserviceDTO,
     * or with status 400 (Bad Request) if the myserviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the myserviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/myservices")
    @Timed
    public ResponseEntity<MyserviceDTO> updateMyservice(@RequestBody MyserviceDTO myserviceDTO) throws URISyntaxException {
        log.debug("REST request to update Myservice : {}", myserviceDTO);
        if (myserviceDTO.getId() == null) {
            return createMyservice(myserviceDTO);
        }
        MyserviceDTO result = myserviceService.save(myserviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myserviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /myservices : get all the myservices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myservices in body
     */
    @GetMapping("/myservices")
    @Timed
    public List<MyserviceDTO> getAllMyservices() {
        log.debug("REST request to get all Myservices");
        return myserviceService.findAll();
        }

    /**
     * GET  /myservices/:id : get the "id" myservice.
     *
     * @param id the id of the myserviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myserviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/myservices/{id}")
    @Timed
    public ResponseEntity<MyserviceDTO> getMyservice(@PathVariable Long id) {
        log.debug("REST request to get Myservice : {}", id);
        MyserviceDTO myserviceDTO = myserviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myserviceDTO));
    }

    /**
     * DELETE  /myservices/:id : delete the "id" myservice.
     *
     * @param id the id of the myserviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/myservices/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyservice(@PathVariable Long id) {
        log.debug("REST request to delete Myservice : {}", id);
        myserviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
