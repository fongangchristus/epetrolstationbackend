package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.StationService;
import com.epetrole.backend.domain.Station;
import com.epetrole.backend.repository.StationRepository;
import com.epetrole.backend.service.dto.StationDTO;
import com.epetrole.backend.service.mapper.StationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Station.
 */
@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    /**
     * Save a station.
     *
     * @param stationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StationDTO save(StationDTO stationDTO) {
        log.debug("Request to save Station : {}", stationDTO);
        Station station = stationMapper.toEntity(stationDTO);
        station = stationRepository.save(station);
        return stationMapper.toDto(station);
    }

    /**
     * Get all the stations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StationDTO> findAll() {
        log.debug("Request to get all Stations");
        return stationRepository.findAll().stream()
            .map(stationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one station by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StationDTO findOne(Long id) {
        log.debug("Request to get Station : {}", id);
        Station station = stationRepository.findOne(id);
        return stationMapper.toDto(station);
    }

    /**
     * Delete the station by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Station : {}", id);
        stationRepository.delete(id);
    }
}
