package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.ReservoirDTO;
import java.util.List;

/**
 * Service Interface for managing Reservoir.
 */
public interface ReservoirService {

    /**
     * Save a reservoir.
     *
     * @param reservoirDTO the entity to save
     * @return the persisted entity
     */
    ReservoirDTO save(ReservoirDTO reservoirDTO);

    /**
     * Get all the reservoirs.
     *
     * @return the list of entities
     */
    List<ReservoirDTO> findAll();

    /**
     * Get the "id" reservoir.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReservoirDTO findOne(Long id);

    /**
     * Delete the "id" reservoir.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
