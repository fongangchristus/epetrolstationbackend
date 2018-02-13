package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.CiterneService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.web.rest.util.PaginationUtil;
import com.epetrole.backend.service.dto.CiterneDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Citerne.
 */
@RestController
@RequestMapping("/api")
public class CiterneResource {

    private final Logger log = LoggerFactory.getLogger(CiterneResource.class);

    private static final String ENTITY_NAME = "citerne";

    private final CiterneService citerneService;

    public CiterneResource(CiterneService citerneService) {
        this.citerneService = citerneService;
    }

    /**
     * POST  /citernes : Create a new citerne.
     *
     * @param citerneDTO the citerneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new citerneDTO, or with status 400 (Bad Request) if the citerne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/citernes")
    @Timed
    public ResponseEntity<CiterneDTO> createCiterne(@RequestBody CiterneDTO citerneDTO) throws URISyntaxException {
        log.debug("REST request to save Citerne : {}", citerneDTO);
        if (citerneDTO.getId() != null) {
            throw new BadRequestAlertException("A new citerne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiterneDTO result = citerneService.save(citerneDTO);
        return ResponseEntity.created(new URI("/api/citernes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /citernes : Updates an existing citerne.
     *
     * @param citerneDTO the citerneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated citerneDTO,
     * or with status 400 (Bad Request) if the citerneDTO is not valid,
     * or with status 500 (Internal Server Error) if the citerneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/citernes")
    @Timed
    public ResponseEntity<CiterneDTO> updateCiterne(@RequestBody CiterneDTO citerneDTO) throws URISyntaxException {
        log.debug("REST request to update Citerne : {}", citerneDTO);
        if (citerneDTO.getId() == null) {
            return createCiterne(citerneDTO);
        }
        CiterneDTO result = citerneService.save(citerneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, citerneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /citernes : get all the citernes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of citernes in body
     */
    @GetMapping("/citernes")
    @Timed
    public ResponseEntity<List<CiterneDTO>> getAllCiternes(Pageable pageable) {
        log.debug("REST request to get a page of Citernes");
        Page<CiterneDTO> page = citerneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/citernes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /citernes/:id : get the "id" citerne.
     *
     * @param id the id of the citerneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the citerneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/citernes/{id}")
    @Timed
    public ResponseEntity<CiterneDTO> getCiterne(@PathVariable Long id) {
        log.debug("REST request to get Citerne : {}", id);
        CiterneDTO citerneDTO = citerneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(citerneDTO));
    }

    /**
     * DELETE  /citernes/:id : delete the "id" citerne.
     *
     * @param id the id of the citerneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/citernes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCiterne(@PathVariable Long id) {
        log.debug("REST request to delete Citerne : {}", id);
        citerneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
