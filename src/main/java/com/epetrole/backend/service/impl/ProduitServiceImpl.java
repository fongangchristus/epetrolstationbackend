package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.ProduitService;
import com.epetrole.backend.domain.Produit;
import com.epetrole.backend.repository.ProduitRepository;
import com.epetrole.backend.service.dto.ProduitDTO;
import com.epetrole.backend.service.mapper.ProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Produit.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitServiceImpl(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduitDTO save(ProduitDTO produitDTO) {
        log.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable)
            .map(produitMapper::toDto);
    }

    /**
     * Get one produit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduitDTO findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        Produit produit = produitRepository.findOne(id);
        return produitMapper.toDto(produit);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.delete(id);
    }
}
