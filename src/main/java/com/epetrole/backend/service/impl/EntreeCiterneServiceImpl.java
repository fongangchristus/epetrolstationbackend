package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.EntreeCiterneService;
import com.epetrole.backend.domain.EntreeCiterne;
import com.epetrole.backend.repository.EntreeCiterneRepository;
import com.epetrole.backend.service.dto.EntreeCiterneDTO;
import com.epetrole.backend.service.mapper.EntreeCiterneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EntreeCiterne.
 */
@Service
@Transactional
public class EntreeCiterneServiceImpl implements EntreeCiterneService {

    private final Logger log = LoggerFactory.getLogger(EntreeCiterneServiceImpl.class);

    private final EntreeCiterneRepository entreeCiterneRepository;

    private final EntreeCiterneMapper entreeCiterneMapper;

    public EntreeCiterneServiceImpl(EntreeCiterneRepository entreeCiterneRepository, EntreeCiterneMapper entreeCiterneMapper) {
        this.entreeCiterneRepository = entreeCiterneRepository;
        this.entreeCiterneMapper = entreeCiterneMapper;
    }

    /**
     * Save a entreeCiterne.
     *
     * @param entreeCiterneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntreeCiterneDTO save(EntreeCiterneDTO entreeCiterneDTO) {
        log.debug("Request to save EntreeCiterne : {}", entreeCiterneDTO);
        EntreeCiterne entreeCiterne = entreeCiterneMapper.toEntity(entreeCiterneDTO);
        entreeCiterne = entreeCiterneRepository.save(entreeCiterne);
        return entreeCiterneMapper.toDto(entreeCiterne);
    }

    /**
     * Get all the entreeCiternes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EntreeCiterneDTO> findAll() {
        log.debug("Request to get all EntreeCiternes");
        return entreeCiterneRepository.findAll().stream()
            .map(entreeCiterneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entreeCiterne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntreeCiterneDTO findOne(Long id) {
        log.debug("Request to get EntreeCiterne : {}", id);
        EntreeCiterne entreeCiterne = entreeCiterneRepository.findOne(id);
        return entreeCiterneMapper.toDto(entreeCiterne);
    }

    /**
     * Delete the entreeCiterne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntreeCiterne : {}", id);
        entreeCiterneRepository.delete(id);
    }
}
