package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.CiterneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Citerne.
 */
public interface CiterneService {

    /**
     * Save a citerne.
     *
     * @param citerneDTO the entity to save
     * @return the persisted entity
     */
    CiterneDTO save(CiterneDTO citerneDTO);

    /**
     * Get all the citernes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CiterneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" citerne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CiterneDTO findOne(Long id);

    /**
     * Delete the "id" citerne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
