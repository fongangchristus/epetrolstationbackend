package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.ReservoirService;
import com.epetrole.backend.domain.Reservoir;
import com.epetrole.backend.repository.ReservoirRepository;
import com.epetrole.backend.service.dto.ReservoirDTO;
import com.epetrole.backend.service.mapper.ReservoirMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Reservoir.
 */
@Service
@Transactional
public class ReservoirServiceImpl implements ReservoirService {

    private final Logger log = LoggerFactory.getLogger(ReservoirServiceImpl.class);

    private final ReservoirRepository reservoirRepository;

    private final ReservoirMapper reservoirMapper;

    public ReservoirServiceImpl(ReservoirRepository reservoirRepository, ReservoirMapper reservoirMapper) {
        this.reservoirRepository = reservoirRepository;
        this.reservoirMapper = reservoirMapper;
    }

    /**
     * Save a reservoir.
     *
     * @param reservoirDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReservoirDTO save(ReservoirDTO reservoirDTO) {
        log.debug("Request to save Reservoir : {}", reservoirDTO);
        Reservoir reservoir = reservoirMapper.toEntity(reservoirDTO);
        reservoir = reservoirRepository.save(reservoir);
        return reservoirMapper.toDto(reservoir);
    }

    /**
     * Get all the reservoirs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservoirDTO> findAll() {
        log.debug("Request to get all Reservoirs");
        return reservoirRepository.findAll().stream()
            .map(reservoirMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reservoir by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReservoirDTO findOne(Long id) {
        log.debug("Request to get Reservoir : {}", id);
        Reservoir reservoir = reservoirRepository.findOne(id);
        return reservoirMapper.toDto(reservoir);
    }

    /**
     * Delete the reservoir by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reservoir : {}", id);
        reservoirRepository.delete(id);
    }
}
