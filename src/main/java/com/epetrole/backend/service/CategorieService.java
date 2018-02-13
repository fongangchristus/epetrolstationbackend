package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.CategorieDTO;
import java.util.List;

/**
 * Service Interface for managing Categorie.
 */
public interface CategorieService {

    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save
     * @return the persisted entity
     */
    CategorieDTO save(CategorieDTO categorieDTO);

    /**
     * Get all the categories.
     *
     * @return the list of entities
     */
    List<CategorieDTO> findAll();

    /**
     * Get the "id" categorie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategorieDTO findOne(Long id);

    /**
     * Delete the "id" categorie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
