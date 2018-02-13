package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.TauxMelangeDTO;
import java.util.List;

/**
 * Service Interface for managing TauxMelange.
 */
public interface TauxMelangeService {

    /**
     * Save a tauxMelange.
     *
     * @param tauxMelangeDTO the entity to save
     * @return the persisted entity
     */
    TauxMelangeDTO save(TauxMelangeDTO tauxMelangeDTO);

    /**
     * Get all the tauxMelanges.
     *
     * @return the list of entities
     */
    List<TauxMelangeDTO> findAll();

    /**
     * Get the "id" tauxMelange.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TauxMelangeDTO findOne(Long id);

    /**
     * Delete the "id" tauxMelange.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
