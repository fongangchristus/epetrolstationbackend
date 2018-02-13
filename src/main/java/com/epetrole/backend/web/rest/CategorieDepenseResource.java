package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.CategorieDepenseService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.service.dto.CategorieDepenseDTO;
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
 * REST controller for managing CategorieDepense.
 */
@RestController
@RequestMapping("/api")
public class CategorieDepenseResource {

    private final Logger log = LoggerFactory.getLogger(CategorieDepenseResource.class);

    private static final String ENTITY_NAME = "categorieDepense";

    private final CategorieDepenseService categorieDepenseService;

    public CategorieDepenseResource(CategorieDepenseService categorieDepenseService) {
        this.categorieDepenseService = categorieDepenseService;
    }

    /**
     * POST  /categorie-depenses : Create a new categorieDepense.
     *
     * @param categorieDepenseDTO the categorieDepenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorieDepenseDTO, or with status 400 (Bad Request) if the categorieDepense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorie-depenses")
    @Timed
    public ResponseEntity<CategorieDepenseDTO> createCategorieDepense(@RequestBody CategorieDepenseDTO categorieDepenseDTO) throws URISyntaxException {
        log.debug("REST request to save CategorieDepense : {}", categorieDepenseDTO);
        if (categorieDepenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieDepense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieDepenseDTO result = categorieDepenseService.save(categorieDepenseDTO);
        return ResponseEntity.created(new URI("/api/categorie-depenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorie-depenses : Updates an existing categorieDepense.
     *
     * @param categorieDepenseDTO the categorieDepenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorieDepenseDTO,
     * or with status 400 (Bad Request) if the categorieDepenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the categorieDepenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorie-depenses")
    @Timed
    public ResponseEntity<CategorieDepenseDTO> updateCategorieDepense(@RequestBody CategorieDepenseDTO categorieDepenseDTO) throws URISyntaxException {
        log.debug("REST request to update CategorieDepense : {}", categorieDepenseDTO);
        if (categorieDepenseDTO.getId() == null) {
            return createCategorieDepense(categorieDepenseDTO);
        }
        CategorieDepenseDTO result = categorieDepenseService.save(categorieDepenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorieDepenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorie-depenses : get all the categorieDepenses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categorieDepenses in body
     */
    @GetMapping("/categorie-depenses")
    @Timed
    public List<CategorieDepenseDTO> getAllCategorieDepenses() {
        log.debug("REST request to get all CategorieDepenses");
        return categorieDepenseService.findAll();
        }

    /**
     * GET  /categorie-depenses/:id : get the "id" categorieDepense.
     *
     * @param id the id of the categorieDepenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorieDepenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorie-depenses/{id}")
    @Timed
    public ResponseEntity<CategorieDepenseDTO> getCategorieDepense(@PathVariable Long id) {
        log.debug("REST request to get CategorieDepense : {}", id);
        CategorieDepenseDTO categorieDepenseDTO = categorieDepenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categorieDepenseDTO));
    }

    /**
     * DELETE  /categorie-depenses/:id : delete the "id" categorieDepense.
     *
     * @param id the id of the categorieDepenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorie-depenses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorieDepense(@PathVariable Long id) {
        log.debug("REST request to delete CategorieDepense : {}", id);
        categorieDepenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
