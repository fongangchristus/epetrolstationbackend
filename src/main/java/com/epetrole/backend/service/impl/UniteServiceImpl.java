package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.UniteService;
import com.epetrole.backend.domain.Unite;
import com.epetrole.backend.repository.UniteRepository;
import com.epetrole.backend.service.dto.UniteDTO;
import com.epetrole.backend.service.mapper.UniteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Unite.
 */
@Service
@Transactional
public class UniteServiceImpl implements UniteService {

    private final Logger log = LoggerFactory.getLogger(UniteServiceImpl.class);

    private final UniteRepository uniteRepository;

    private final UniteMapper uniteMapper;

    public UniteServiceImpl(UniteRepository uniteRepository, UniteMapper uniteMapper) {
        this.uniteRepository = uniteRepository;
        this.uniteMapper = uniteMapper;
    }

    /**
     * Save a unite.
     *
     * @param uniteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UniteDTO save(UniteDTO uniteDTO) {
        log.debug("Request to save Unite : {}", uniteDTO);
        Unite unite = uniteMapper.toEntity(uniteDTO);
        unite = uniteRepository.save(unite);
        return uniteMapper.toDto(unite);
    }

    /**
     * Get all the unites.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UniteDTO> findAll() {
        log.debug("Request to get all Unites");
        return uniteRepository.findAll().stream()
            .map(uniteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one unite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UniteDTO findOne(Long id) {
        log.debug("Request to get Unite : {}", id);
        Unite unite = uniteRepository.findOne(id);
        return uniteMapper.toDto(unite);
    }

    /**
     * Delete the unite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unite : {}", id);
        uniteRepository.delete(id);
    }
}
