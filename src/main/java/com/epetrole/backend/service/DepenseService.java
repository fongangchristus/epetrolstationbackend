package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.DepenseDTO;
import java.util.List;

/**
 * Service Interface for managing Depense.
 */
public interface DepenseService {

    /**
     * Save a depense.
     *
     * @param depenseDTO the entity to save
     * @return the persisted entity
     */
    DepenseDTO save(DepenseDTO depenseDTO);

    /**
     * Get all the depenses.
     *
     * @return the list of entities
     */
    List<DepenseDTO> findAll();

    /**
     * Get the "id" depense.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DepenseDTO findOne(Long id);

    /**
     * Delete the "id" depense.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
