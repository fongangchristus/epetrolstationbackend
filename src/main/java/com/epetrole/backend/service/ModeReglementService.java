package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.ModeReglementDTO;
import java.util.List;

/**
 * Service Interface for managing ModeReglement.
 */
public interface ModeReglementService {

    /**
     * Save a modeReglement.
     *
     * @param modeReglementDTO the entity to save
     * @return the persisted entity
     */
    ModeReglementDTO save(ModeReglementDTO modeReglementDTO);

    /**
     * Get all the modeReglements.
     *
     * @return the list of entities
     */
    List<ModeReglementDTO> findAll();

    /**
     * Get the "id" modeReglement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ModeReglementDTO findOne(Long id);

    /**
     * Delete the "id" modeReglement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
