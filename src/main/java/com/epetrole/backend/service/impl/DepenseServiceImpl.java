package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.DepenseService;
import com.epetrole.backend.domain.Depense;
import com.epetrole.backend.repository.DepenseRepository;
import com.epetrole.backend.service.dto.DepenseDTO;
import com.epetrole.backend.service.mapper.DepenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Depense.
 */
@Service
@Transactional
public class DepenseServiceImpl implements DepenseService {

    private final Logger log = LoggerFactory.getLogger(DepenseServiceImpl.class);

    private final DepenseRepository depenseRepository;

    private final DepenseMapper depenseMapper;

    public DepenseServiceImpl(DepenseRepository depenseRepository, DepenseMapper depenseMapper) {
        this.depenseRepository = depenseRepository;
        this.depenseMapper = depenseMapper;
    }

    /**
     * Save a depense.
     *
     * @param depenseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DepenseDTO save(DepenseDTO depenseDTO) {
        log.debug("Request to save Depense : {}", depenseDTO);
        Depense depense = depenseMapper.toEntity(depenseDTO);
        depense = depenseRepository.save(depense);
        return depenseMapper.toDto(depense);
    }

    /**
     * Get all the depenses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DepenseDTO> findAll() {
        log.debug("Request to get all Depenses");
        return depenseRepository.findAll().stream()
            .map(depenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one depense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DepenseDTO findOne(Long id) {
        log.debug("Request to get Depense : {}", id);
        Depense depense = depenseRepository.findOne(id);
        return depenseMapper.toDto(depense);
    }

    /**
     * Delete the depense by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Depense : {}", id);
        depenseRepository.delete(id);
    }
}
