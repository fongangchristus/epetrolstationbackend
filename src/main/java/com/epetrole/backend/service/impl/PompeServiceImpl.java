package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.PompeService;
import com.epetrole.backend.domain.Pompe;
import com.epetrole.backend.repository.PompeRepository;
import com.epetrole.backend.service.dto.PompeDTO;
import com.epetrole.backend.service.mapper.PompeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pompe.
 */
@Service
@Transactional
public class PompeServiceImpl implements PompeService {

    private final Logger log = LoggerFactory.getLogger(PompeServiceImpl.class);

    private final PompeRepository pompeRepository;

    private final PompeMapper pompeMapper;

    public PompeServiceImpl(PompeRepository pompeRepository, PompeMapper pompeMapper) {
        this.pompeRepository = pompeRepository;
        this.pompeMapper = pompeMapper;
    }

    /**
     * Save a pompe.
     *
     * @param pompeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PompeDTO save(PompeDTO pompeDTO) {
        log.debug("Request to save Pompe : {}", pompeDTO);
        Pompe pompe = pompeMapper.toEntity(pompeDTO);
        pompe = pompeRepository.save(pompe);
        return pompeMapper.toDto(pompe);
    }

    /**
     * Get all the pompes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PompeDTO> findAll() {
        log.debug("Request to get all Pompes");
        return pompeRepository.findAll().stream()
            .map(pompeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pompe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PompeDTO findOne(Long id) {
        log.debug("Request to get Pompe : {}", id);
        Pompe pompe = pompeRepository.findOne(id);
        return pompeMapper.toDto(pompe);
    }

    /**
     * Delete the pompe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pompe : {}", id);
        pompeRepository.delete(id);
    }
}
