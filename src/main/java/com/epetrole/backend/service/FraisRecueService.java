package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.FraisRecueDTO;
import java.util.List;

/**
 * Service Interface for managing FraisRecue.
 */
public interface FraisRecueService {

    /**
     * Save a fraisRecue.
     *
     * @param fraisRecueDTO the entity to save
     * @return the persisted entity
     */
    FraisRecueDTO save(FraisRecueDTO fraisRecueDTO);

    /**
     * Get all the fraisRecues.
     *
     * @return the list of entities
     */
    List<FraisRecueDTO> findAll();

    /**
     * Get the "id" fraisRecue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FraisRecueDTO findOne(Long id);

    /**
     * Delete the "id" fraisRecue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
