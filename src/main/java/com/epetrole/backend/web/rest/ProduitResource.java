package com.epetrole.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.epetrole.backend.service.ProduitService;
import com.epetrole.backend.web.rest.errors.BadRequestAlertException;
import com.epetrole.backend.web.rest.util.HeaderUtil;
import com.epetrole.backend.web.rest.util.PaginationUtil;
import com.epetrole.backend.service.dto.ProduitDTO;
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
 * REST controller for managing Produit.
 */
@RestController
@RequestMapping("/api")
public class ProduitResource {

    private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "produit";

    private final ProduitService produitService;

    public ProduitResource(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * POST  /produits : Create a new produit.
     *
     * @param produitDTO the produitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produitDTO, or with status 400 (Bad Request) if the produit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produits")
    @Timed
    public ResponseEntity<ProduitDTO> createProduit(@RequestBody ProduitDTO produitDTO) throws URISyntaxException {
        log.debug("REST request to save Produit : {}", produitDTO);
        if (produitDTO.getId() != null) {
            throw new BadRequestAlertException("A new produit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity.created(new URI("/api/produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produits : Updates an existing produit.
     *
     * @param produitDTO the produitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produitDTO,
     * or with status 400 (Bad Request) if the produitDTO is not valid,
     * or with status 500 (Internal Server Error) if the produitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produits")
    @Timed
    public ResponseEntity<ProduitDTO> updateProduit(@RequestBody ProduitDTO produitDTO) throws URISyntaxException {
        log.debug("REST request to update Produit : {}", produitDTO);
        if (produitDTO.getId() == null) {
            return createProduit(produitDTO);
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produits : get all the produits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produits in body
     */
    @GetMapping("/produits")
    @Timed
    public ResponseEntity<List<ProduitDTO>> getAllProduits(Pageable pageable) {
        log.debug("REST request to get a page of Produits");
        Page<ProduitDTO> page = produitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produits/:id : get the "id" produit.
     *
     * @param id the id of the produitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produits/{id}")
    @Timed
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable Long id) {
        log.debug("REST request to get Produit : {}", id);
        ProduitDTO produitDTO = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produitDTO));
    }

    /**
     * DELETE  /produits/:id : delete the "id" produit.
     *
     * @param id the id of the produitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        log.debug("REST request to delete Produit : {}", id);
        produitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
