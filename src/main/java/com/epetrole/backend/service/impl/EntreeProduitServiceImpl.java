package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.EntreeProduitService;
import com.epetrole.backend.domain.EntreeProduit;
import com.epetrole.backend.repository.EntreeProduitRepository;
import com.epetrole.backend.service.dto.EntreeProduitDTO;
import com.epetrole.backend.service.mapper.EntreeProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EntreeProduit.
 */
@Service
@Transactional
public class EntreeProduitServiceImpl implements EntreeProduitService {

    private final Logger log = LoggerFactory.getLogger(EntreeProduitServiceImpl.class);

    private final EntreeProduitRepository entreeProduitRepository;

    private final EntreeProduitMapper entreeProduitMapper;

    public EntreeProduitServiceImpl(EntreeProduitRepository entreeProduitRepository, EntreeProduitMapper entreeProduitMapper) {
        this.entreeProduitRepository = entreeProduitRepository;
        this.entreeProduitMapper = entreeProduitMapper;
    }

    /**
     * Save a entreeProduit.
     *
     * @param entreeProduitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntreeProduitDTO save(EntreeProduitDTO entreeProduitDTO) {
        log.debug("Request to save EntreeProduit : {}", entreeProduitDTO);
        EntreeProduit entreeProduit = entreeProduitMapper.toEntity(entreeProduitDTO);
        entreeProduit = entreeProduitRepository.save(entreeProduit);
        return entreeProduitMapper.toDto(entreeProduit);
    }

    /**
     * Get all the entreeProduits.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EntreeProduitDTO> findAll() {
        log.debug("Request to get all EntreeProduits");
        return entreeProduitRepository.findAll().stream()
            .map(entreeProduitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entreeProduit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntreeProduitDTO findOne(Long id) {
        log.debug("Request to get EntreeProduit : {}", id);
        EntreeProduit entreeProduit = entreeProduitRepository.findOne(id);
        return entreeProduitMapper.toDto(entreeProduit);
    }

    /**
     * Delete the entreeProduit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntreeProduit : {}", id);
        entreeProduitRepository.delete(id);
    }
}
