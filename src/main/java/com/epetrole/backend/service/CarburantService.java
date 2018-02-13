package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.CarburantDTO;
import java.util.List;

/**
 * Service Interface for managing Carburant.
 */
public interface CarburantService {

    /**
     * Save a carburant.
     *
     * @param carburantDTO the entity to save
     * @return the persisted entity
     */
    CarburantDTO save(CarburantDTO carburantDTO);

    /**
     * Get all the carburants.
     *
     * @return the list of entities
     */
    List<CarburantDTO> findAll();

    /**
     * Get the "id" carburant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CarburantDTO findOne(Long id);

    /**
     * Delete the "id" carburant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
