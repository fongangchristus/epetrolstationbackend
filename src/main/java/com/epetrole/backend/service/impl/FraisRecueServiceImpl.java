package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.FraisRecueService;
import com.epetrole.backend.domain.FraisRecue;
import com.epetrole.backend.repository.FraisRecueRepository;
import com.epetrole.backend.service.dto.FraisRecueDTO;
import com.epetrole.backend.service.mapper.FraisRecueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FraisRecue.
 */
@Service
@Transactional
public class FraisRecueServiceImpl implements FraisRecueService {

    private final Logger log = LoggerFactory.getLogger(FraisRecueServiceImpl.class);

    private final FraisRecueRepository fraisRecueRepository;

    private final FraisRecueMapper fraisRecueMapper;

    public FraisRecueServiceImpl(FraisRecueRepository fraisRecueRepository, FraisRecueMapper fraisRecueMapper) {
        this.fraisRecueRepository = fraisRecueRepository;
        this.fraisRecueMapper = fraisRecueMapper;
    }

    /**
     * Save a fraisRecue.
     *
     * @param fraisRecueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FraisRecueDTO save(FraisRecueDTO fraisRecueDTO) {
        log.debug("Request to save FraisRecue : {}", fraisRecueDTO);
        FraisRecue fraisRecue = fraisRecueMapper.toEntity(fraisRecueDTO);
        fraisRecue = fraisRecueRepository.save(fraisRecue);
        return fraisRecueMapper.toDto(fraisRecue);
    }

    /**
     * Get all the fraisRecues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FraisRecueDTO> findAll() {
        log.debug("Request to get all FraisRecues");
        return fraisRecueRepository.findAll().stream()
            .map(fraisRecueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fraisRecue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FraisRecueDTO findOne(Long id) {
        log.debug("Request to get FraisRecue : {}", id);
        FraisRecue fraisRecue = fraisRecueRepository.findOne(id);
        return fraisRecueMapper.toDto(fraisRecue);
    }

    /**
     * Delete the fraisRecue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FraisRecue : {}", id);
        fraisRecueRepository.delete(id);
    }
}
