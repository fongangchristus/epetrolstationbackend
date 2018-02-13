package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.TypeIntervenantDTO;
import java.util.List;

/**
 * Service Interface for managing TypeIntervenant.
 */
public interface TypeIntervenantService {

    /**
     * Save a typeIntervenant.
     *
     * @param typeIntervenantDTO the entity to save
     * @return the persisted entity
     */
    TypeIntervenantDTO save(TypeIntervenantDTO typeIntervenantDTO);

    /**
     * Get all the typeIntervenants.
     *
     * @return the list of entities
     */
    List<TypeIntervenantDTO> findAll();

    /**
     * Get the "id" typeIntervenant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TypeIntervenantDTO findOne(Long id);

    /**
     * Delete the "id" typeIntervenant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
