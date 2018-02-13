package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.SortieMyserviceDTO;
import java.util.List;

/**
 * Service Interface for managing SortieMyservice.
 */
public interface SortieMyserviceService {

    /**
     * Save a sortieMyservice.
     *
     * @param sortieMyserviceDTO the entity to save
     * @return the persisted entity
     */
    SortieMyserviceDTO save(SortieMyserviceDTO sortieMyserviceDTO);

    /**
     * Get all the sortieMyservices.
     *
     * @return the list of entities
     */
    List<SortieMyserviceDTO> findAll();

    /**
     * Get the "id" sortieMyservice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SortieMyserviceDTO findOne(Long id);

    /**
     * Delete the "id" sortieMyservice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
