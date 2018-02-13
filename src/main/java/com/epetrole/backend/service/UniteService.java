package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.UniteDTO;
import java.util.List;

/**
 * Service Interface for managing Unite.
 */
public interface UniteService {

    /**
     * Save a unite.
     *
     * @param uniteDTO the entity to save
     * @return the persisted entity
     */
    UniteDTO save(UniteDTO uniteDTO);

    /**
     * Get all the unites.
     *
     * @return the list of entities
     */
    List<UniteDTO> findAll();

    /**
     * Get the "id" unite.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UniteDTO findOne(Long id);

    /**
     * Delete the "id" unite.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
