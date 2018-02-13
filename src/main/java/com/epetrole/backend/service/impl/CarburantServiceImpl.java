package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.CarburantService;
import com.epetrole.backend.domain.Carburant;
import com.epetrole.backend.repository.CarburantRepository;
import com.epetrole.backend.service.dto.CarburantDTO;
import com.epetrole.backend.service.mapper.CarburantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Carburant.
 */
@Service
@Transactional
public class CarburantServiceImpl implements CarburantService {

    private final Logger log = LoggerFactory.getLogger(CarburantServiceImpl.class);

    private final CarburantRepository carburantRepository;

    private final CarburantMapper carburantMapper;

    public CarburantServiceImpl(CarburantRepository carburantRepository, CarburantMapper carburantMapper) {
        this.carburantRepository = carburantRepository;
        this.carburantMapper = carburantMapper;
    }

    /**
     * Save a carburant.
     *
     * @param carburantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarburantDTO save(CarburantDTO carburantDTO) {
        log.debug("Request to save Carburant : {}", carburantDTO);
        Carburant carburant = carburantMapper.toEntity(carburantDTO);
        carburant = carburantRepository.save(carburant);
        return carburantMapper.toDto(carburant);
    }

    /**
     * Get all the carburants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarburantDTO> findAll() {
        log.debug("Request to get all Carburants");
        return carburantRepository.findAll().stream()
            .map(carburantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one carburant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarburantDTO findOne(Long id) {
        log.debug("Request to get Carburant : {}", id);
        Carburant carburant = carburantRepository.findOne(id);
        return carburantMapper.toDto(carburant);
    }

    /**
     * Delete the carburant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carburant : {}", id);
        carburantRepository.delete(id);
    }
}
