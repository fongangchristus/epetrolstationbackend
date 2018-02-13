package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.CategorieDepenseDTO;
import java.util.List;

/**
 * Service Interface for managing CategorieDepense.
 */
public interface CategorieDepenseService {

    /**
     * Save a categorieDepense.
     *
     * @param categorieDepenseDTO the entity to save
     * @return the persisted entity
     */
    CategorieDepenseDTO save(CategorieDepenseDTO categorieDepenseDTO);

    /**
     * Get all the categorieDepenses.
     *
     * @return the list of entities
     */
    List<CategorieDepenseDTO> findAll();

    /**
     * Get the "id" categorieDepense.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategorieDepenseDTO findOne(Long id);

    /**
     * Delete the "id" categorieDepense.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
