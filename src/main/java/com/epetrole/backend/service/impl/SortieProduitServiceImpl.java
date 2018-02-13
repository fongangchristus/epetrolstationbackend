package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.SortieProduitService;
import com.epetrole.backend.domain.SortieProduit;
import com.epetrole.backend.repository.SortieProduitRepository;
import com.epetrole.backend.service.dto.SortieProduitDTO;
import com.epetrole.backend.service.mapper.SortieProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SortieProduit.
 */
@Service
@Transactional
public class SortieProduitServiceImpl implements SortieProduitService {

    private final Logger log = LoggerFactory.getLogger(SortieProduitServiceImpl.class);

    private final SortieProduitRepository sortieProduitRepository;

    private final SortieProduitMapper sortieProduitMapper;

    public SortieProduitServiceImpl(SortieProduitRepository sortieProduitRepository, SortieProduitMapper sortieProduitMapper) {
        this.sortieProduitRepository = sortieProduitRepository;
        this.sortieProduitMapper = sortieProduitMapper;
    }

    /**
     * Save a sortieProduit.
     *
     * @param sortieProduitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SortieProduitDTO save(SortieProduitDTO sortieProduitDTO) {
        log.debug("Request to save SortieProduit : {}", sortieProduitDTO);
        SortieProduit sortieProduit = sortieProduitMapper.toEntity(sortieProduitDTO);
        sortieProduit = sortieProduitRepository.save(sortieProduit);
        return sortieProduitMapper.toDto(sortieProduit);
    }

    /**
     * Get all the sortieProduits.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SortieProduitDTO> findAll() {
        log.debug("Request to get all SortieProduits");
        return sortieProduitRepository.findAll().stream()
            .map(sortieProduitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sortieProduit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SortieProduitDTO findOne(Long id) {
        log.debug("Request to get SortieProduit : {}", id);
        SortieProduit sortieProduit = sortieProduitRepository.findOne(id);
        return sortieProduitMapper.toDto(sortieProduit);
    }

    /**
     * Delete the sortieProduit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SortieProduit : {}", id);
        sortieProduitRepository.delete(id);
    }
}
