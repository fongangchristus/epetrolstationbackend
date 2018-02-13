package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.EntreeCarburantDTO;
import java.util.List;

/**
 * Service Interface for managing EntreeCarburant.
 */
public interface EntreeCarburantService {

    /**
     * Save a entreeCarburant.
     *
     * @param entreeCarburantDTO the entity to save
     * @return the persisted entity
     */
    EntreeCarburantDTO save(EntreeCarburantDTO entreeCarburantDTO);

    /**
     * Get all the entreeCarburants.
     *
     * @return the list of entities
     */
    List<EntreeCarburantDTO> findAll();

    /**
     * Get the "id" entreeCarburant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntreeCarburantDTO findOne(Long id);

    /**
     * Delete the "id" entreeCarburant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
