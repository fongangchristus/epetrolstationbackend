package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.IntervenantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Intervenant.
 */
public interface IntervenantService {

    /**
     * Save a intervenant.
     *
     * @param intervenantDTO the entity to save
     * @return the persisted entity
     */
    IntervenantDTO save(IntervenantDTO intervenantDTO);

    /**
     * Get all the intervenants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IntervenantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" intervenant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IntervenantDTO findOne(Long id);

    /**
     * Delete the "id" intervenant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
