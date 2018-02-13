package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.CatCarburantService;
import com.epetrole.backend.domain.CatCarburant;
import com.epetrole.backend.repository.CatCarburantRepository;
import com.epetrole.backend.service.dto.CatCarburantDTO;
import com.epetrole.backend.service.mapper.CatCarburantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CatCarburant.
 */
@Service
@Transactional
public class CatCarburantServiceImpl implements CatCarburantService {

    private final Logger log = LoggerFactory.getLogger(CatCarburantServiceImpl.class);

    private final CatCarburantRepository catCarburantRepository;

    private final CatCarburantMapper catCarburantMapper;

    public CatCarburantServiceImpl(CatCarburantRepository catCarburantRepository, CatCarburantMapper catCarburantMapper) {
        this.catCarburantRepository = catCarburantRepository;
        this.catCarburantMapper = catCarburantMapper;
    }

    /**
     * Save a catCarburant.
     *
     * @param catCarburantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CatCarburantDTO save(CatCarburantDTO catCarburantDTO) {
        log.debug("Request to save CatCarburant : {}", catCarburantDTO);
        CatCarburant catCarburant = catCarburantMapper.toEntity(catCarburantDTO);
        catCarburant = catCarburantRepository.save(catCarburant);
        return catCarburantMapper.toDto(catCarburant);
    }

    /**
     * Get all the catCarburants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CatCarburantDTO> findAll() {
        log.debug("Request to get all CatCarburants");
        return catCarburantRepository.findAll().stream()
            .map(catCarburantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one catCarburant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CatCarburantDTO findOne(Long id) {
        log.debug("Request to get CatCarburant : {}", id);
        CatCarburant catCarburant = catCarburantRepository.findOne(id);
        return catCarburantMapper.toDto(catCarburant);
    }

    /**
     * Delete the catCarburant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CatCarburant : {}", id);
        catCarburantRepository.delete(id);
    }
}
