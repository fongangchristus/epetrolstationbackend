package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.EntreeCarburantService;
import com.epetrole.backend.domain.EntreeCarburant;
import com.epetrole.backend.repository.EntreeCarburantRepository;
import com.epetrole.backend.service.dto.EntreeCarburantDTO;
import com.epetrole.backend.service.mapper.EntreeCarburantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EntreeCarburant.
 */
@Service
@Transactional
public class EntreeCarburantServiceImpl implements EntreeCarburantService {

    private final Logger log = LoggerFactory.getLogger(EntreeCarburantServiceImpl.class);

    private final EntreeCarburantRepository entreeCarburantRepository;

    private final EntreeCarburantMapper entreeCarburantMapper;

    public EntreeCarburantServiceImpl(EntreeCarburantRepository entreeCarburantRepository, EntreeCarburantMapper entreeCarburantMapper) {
        this.entreeCarburantRepository = entreeCarburantRepository;
        this.entreeCarburantMapper = entreeCarburantMapper;
    }

    /**
     * Save a entreeCarburant.
     *
     * @param entreeCarburantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntreeCarburantDTO save(EntreeCarburantDTO entreeCarburantDTO) {
        log.debug("Request to save EntreeCarburant : {}", entreeCarburantDTO);
        EntreeCarburant entreeCarburant = entreeCarburantMapper.toEntity(entreeCarburantDTO);
        entreeCarburant = entreeCarburantRepository.save(entreeCarburant);
        return entreeCarburantMapper.toDto(entreeCarburant);
    }

    /**
     * Get all the entreeCarburants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EntreeCarburantDTO> findAll() {
        log.debug("Request to get all EntreeCarburants");
        return entreeCarburantRepository.findAll().stream()
            .map(entreeCarburantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entreeCarburant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntreeCarburantDTO findOne(Long id) {
        log.debug("Request to get EntreeCarburant : {}", id);
        EntreeCarburant entreeCarburant = entreeCarburantRepository.findOne(id);
        return entreeCarburantMapper.toDto(entreeCarburant);
    }

    /**
     * Delete the entreeCarburant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntreeCarburant : {}", id);
        entreeCarburantRepository.delete(id);
    }
}
