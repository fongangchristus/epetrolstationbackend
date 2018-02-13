package com.epetrole.backend.service;

import com.epetrole.backend.service.dto.MyserviceDTO;
import java.util.List;

/**
 * Service Interface for managing Myservice.
 */
public interface MyserviceService {

    /**
     * Save a myservice.
     *
     * @param myserviceDTO the entity to save
     * @return the persisted entity
     */
    MyserviceDTO save(MyserviceDTO myserviceDTO);

    /**
     * Get all the myservices.
     *
     * @return the list of entities
     */
    List<MyserviceDTO> findAll();

    /**
     * Get the "id" myservice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MyserviceDTO findOne(Long id);

    /**
     * Delete the "id" myservice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
