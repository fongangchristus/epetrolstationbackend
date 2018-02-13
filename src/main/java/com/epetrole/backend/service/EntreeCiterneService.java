package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.EntreeCiterneDTO;
import java.util.List;

/**
 * Service Interface for managing EntreeCiterne.
 */
public interface EntreeCiterneService {

    /**
     * Save a entreeCiterne.
     *
     * @param entreeCiterneDTO the entity to save
     * @return the persisted entity
     */
    EntreeCiterneDTO save(EntreeCiterneDTO entreeCiterneDTO);

    /**
     * Get all the entreeCiternes.
     *
     * @return the list of entities
     */
    List<EntreeCiterneDTO> findAll();

    /**
     * Get the "id" entreeCiterne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntreeCiterneDTO findOne(Long id);

    /**
     * Delete the "id" entreeCiterne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
