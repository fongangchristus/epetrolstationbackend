package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.TvaDTO;
import java.util.List;

/**
 * Service Interface for managing Tva.
 */
public interface TvaService {

    /**
     * Save a tva.
     *
     * @param tvaDTO the entity to save
     * @return the persisted entity
     */
    TvaDTO save(TvaDTO tvaDTO);

    /**
     * Get all the tvas.
     *
     * @return the list of entities
     */
    List<TvaDTO> findAll();

    /**
     * Get the "id" tva.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TvaDTO findOne(Long id);

    /**
     * Delete the "id" tva.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
