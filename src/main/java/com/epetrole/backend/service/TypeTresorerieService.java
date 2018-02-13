package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.TypeTresorerieDTO;
import java.util.List;

/**
 * Service Interface for managing TypeTresorerie.
 */
public interface TypeTresorerieService {

    /**
     * Save a typeTresorerie.
     *
     * @param typeTresorerieDTO the entity to save
     * @return the persisted entity
     */
    TypeTresorerieDTO save(TypeTresorerieDTO typeTresorerieDTO);

    /**
     * Get all the typeTresoreries.
     *
     * @return the list of entities
     */
    List<TypeTresorerieDTO> findAll();

    /**
     * Get the "id" typeTresorerie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TypeTresorerieDTO findOne(Long id);

    /**
     * Delete the "id" typeTresorerie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
