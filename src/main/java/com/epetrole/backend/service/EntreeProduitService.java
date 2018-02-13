package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.EntreeProduitDTO;
import java.util.List;

/**
 * Service Interface for managing EntreeProduit.
 */
public interface EntreeProduitService {

    /**
     * Save a entreeProduit.
     *
     * @param entreeProduitDTO the entity to save
     * @return the persisted entity
     */
    EntreeProduitDTO save(EntreeProduitDTO entreeProduitDTO);

    /**
     * Get all the entreeProduits.
     *
     * @return the list of entities
     */
    List<EntreeProduitDTO> findAll();

    /**
     * Get the "id" entreeProduit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntreeProduitDTO findOne(Long id);

    /**
     * Delete the "id" entreeProduit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
