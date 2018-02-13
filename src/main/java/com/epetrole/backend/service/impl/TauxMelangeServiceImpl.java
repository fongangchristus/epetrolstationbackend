package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.TauxMelangeService;
import com.epetrole.backend.domain.TauxMelange;
import com.epetrole.backend.repository.TauxMelangeRepository;
import com.epetrole.backend.service.dto.TauxMelangeDTO;
import com.epetrole.backend.service.mapper.TauxMelangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TauxMelange.
 */
@Service
@Transactional
public class TauxMelangeServiceImpl implements TauxMelangeService {

    private final Logger log = LoggerFactory.getLogger(TauxMelangeServiceImpl.class);

    private final TauxMelangeRepository tauxMelangeRepository;

    private final TauxMelangeMapper tauxMelangeMapper;

    public TauxMelangeServiceImpl(TauxMelangeRepository tauxMelangeRepository, TauxMelangeMapper tauxMelangeMapper) {
        this.tauxMelangeRepository = tauxMelangeRepository;
        this.tauxMelangeMapper = tauxMelangeMapper;
    }

    /**
     * Save a tauxMelange.
     *
     * @param tauxMelangeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TauxMelangeDTO save(TauxMelangeDTO tauxMelangeDTO) {
        log.debug("Request to save TauxMelange : {}", tauxMelangeDTO);
        TauxMelange tauxMelange = tauxMelangeMapper.toEntity(tauxMelangeDTO);
        tauxMelange = tauxMelangeRepository.save(tauxMelange);
        return tauxMelangeMapper.toDto(tauxMelange);
    }

    /**
     * Get all the tauxMelanges.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TauxMelangeDTO> findAll() {
        log.debug("Request to get all TauxMelanges");
        return tauxMelangeRepository.findAll().stream()
            .map(tauxMelangeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tauxMelange by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TauxMelangeDTO findOne(Long id) {
        log.debug("Request to get TauxMelange : {}", id);
        TauxMelange tauxMelange = tauxMelangeRepository.findOne(id);
        return tauxMelangeMapper.toDto(tauxMelange);
    }

    /**
     * Delete the tauxMelange by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TauxMelange : {}", id);
        tauxMelangeRepository.delete(id);
    }
}
