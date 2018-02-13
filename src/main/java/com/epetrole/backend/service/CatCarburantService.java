package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.CatCarburantDTO;
import java.util.List;

/**
 * Service Interface for managing CatCarburant.
 */
public interface CatCarburantService {

    /**
     * Save a catCarburant.
     *
     * @param catCarburantDTO the entity to save
     * @return the persisted entity
     */
    CatCarburantDTO save(CatCarburantDTO catCarburantDTO);

    /**
     * Get all the catCarburants.
     *
     * @return the list of entities
     */
    List<CatCarburantDTO> findAll();

    /**
     * Get the "id" catCarburant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CatCarburantDTO findOne(Long id);

    /**
     * Delete the "id" catCarburant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
