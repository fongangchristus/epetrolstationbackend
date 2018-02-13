package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.SortieCarburantDTO;
import java.util.List;

/**
 * Service Interface for managing SortieCarburant.
 */
public interface SortieCarburantService {

    /**
     * Save a sortieCarburant.
     *
     * @param sortieCarburantDTO the entity to save
     * @return the persisted entity
     */
    SortieCarburantDTO save(SortieCarburantDTO sortieCarburantDTO);

    /**
     * Get all the sortieCarburants.
     *
     * @return the list of entities
     */
    List<SortieCarburantDTO> findAll();

    /**
     * Get the "id" sortieCarburant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SortieCarburantDTO findOne(Long id);

    /**
     * Delete the "id" sortieCarburant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
