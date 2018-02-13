package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.TresorerieDTO;
import java.util.List;

/**
 * Service Interface for managing Tresorerie.
 */
public interface TresorerieService {

    /**
     * Save a tresorerie.
     *
     * @param tresorerieDTO the entity to save
     * @return the persisted entity
     */
    TresorerieDTO save(TresorerieDTO tresorerieDTO);

    /**
     * Get all the tresoreries.
     *
     * @return the list of entities
     */
    List<TresorerieDTO> findAll();

    /**
     * Get the "id" tresorerie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TresorerieDTO findOne(Long id);

    /**
     * Delete the "id" tresorerie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
