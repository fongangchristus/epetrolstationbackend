package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.PompeDTO;
import java.util.List;

/**
 * Service Interface for managing Pompe.
 */
public interface PompeService {

    /**
     * Save a pompe.
     *
     * @param pompeDTO the entity to save
     * @return the persisted entity
     */
    PompeDTO save(PompeDTO pompeDTO);

    /**
     * Get all the pompes.
     *
     * @return the list of entities
     */
    List<PompeDTO> findAll();

    /**
     * Get the "id" pompe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PompeDTO findOne(Long id);

    /**
     * Delete the "id" pompe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
