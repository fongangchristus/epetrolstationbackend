package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.SortieCarburantService;
import com.epetrole.backend.domain.SortieCarburant;
import com.epetrole.backend.repository.SortieCarburantRepository;
import com.epetrole.backend.service.dto.SortieCarburantDTO;
import com.epetrole.backend.service.mapper.SortieCarburantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SortieCarburant.
 */
@Service
@Transactional
public class SortieCarburantServiceImpl implements SortieCarburantService {

    private final Logger log = LoggerFactory.getLogger(SortieCarburantServiceImpl.class);

    private final SortieCarburantRepository sortieCarburantRepository;

    private final SortieCarburantMapper sortieCarburantMapper;

    public SortieCarburantServiceImpl(SortieCarburantRepository sortieCarburantRepository, SortieCarburantMapper sortieCarburantMapper) {
        this.sortieCarburantRepository = sortieCarburantRepository;
        this.sortieCarburantMapper = sortieCarburantMapper;
    }

    /**
     * Save a sortieCarburant.
     *
     * @param sortieCarburantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SortieCarburantDTO save(SortieCarburantDTO sortieCarburantDTO) {
        log.debug("Request to save SortieCarburant : {}", sortieCarburantDTO);
        SortieCarburant sortieCarburant = sortieCarburantMapper.toEntity(sortieCarburantDTO);
        sortieCarburant = sortieCarburantRepository.save(sortieCarburant);
        return sortieCarburantMapper.toDto(sortieCarburant);
    }

    /**
     * Get all the sortieCarburants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SortieCarburantDTO> findAll() {
        log.debug("Request to get all SortieCarburants");
        return sortieCarburantRepository.findAll().stream()
            .map(sortieCarburantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sortieCarburant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SortieCarburantDTO findOne(Long id) {
        log.debug("Request to get SortieCarburant : {}", id);
        SortieCarburant sortieCarburant = sortieCarburantRepository.findOne(id);
        return sortieCarburantMapper.toDto(sortieCarburant);
    }

    /**
     * Delete the sortieCarburant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SortieCarburant : {}", id);
        sortieCarburantRepository.delete(id);
    }
}
