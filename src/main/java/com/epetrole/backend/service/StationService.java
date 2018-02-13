package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.StationDTO;
import java.util.List;

/**
 * Service Interface for managing Station.
 */
public interface StationService {

    /**
     * Save a station.
     *
     * @param stationDTO the entity to save
     * @return the persisted entity
     */
    StationDTO save(StationDTO stationDTO);

    /**
     * Get all the stations.
     *
     * @return the list of entities
     */
    List<StationDTO> findAll();

    /**
     * Get the "id" station.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StationDTO findOne(Long id);

    /**
     * Delete the "id" station.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
