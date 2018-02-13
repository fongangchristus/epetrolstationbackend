package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.SortieProduitDTO;
import java.util.List;

/**
 * Service Interface for managing SortieProduit.
 */
public interface SortieProduitService {

    /**
     * Save a sortieProduit.
     *
     * @param sortieProduitDTO the entity to save
     * @return the persisted entity
     */
    SortieProduitDTO save(SortieProduitDTO sortieProduitDTO);

    /**
     * Get all the sortieProduits.
     *
     * @return the list of entities
     */
    List<SortieProduitDTO> findAll();

    /**
     * Get the "id" sortieProduit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SortieProduitDTO findOne(Long id);

    /**
     * Delete the "id" sortieProduit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
