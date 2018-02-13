package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.TvaService;
import com.epetrole.backend.domain.Tva;
import com.epetrole.backend.repository.TvaRepository;
import com.epetrole.backend.service.dto.TvaDTO;
import com.epetrole.backend.service.mapper.TvaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tva.
 */
@Service
@Transactional
public class TvaServiceImpl implements TvaService {

    private final Logger log = LoggerFactory.getLogger(TvaServiceImpl.class);

    private final TvaRepository tvaRepository;

    private final TvaMapper tvaMapper;

    public TvaServiceImpl(TvaRepository tvaRepository, TvaMapper tvaMapper) {
        this.tvaRepository = tvaRepository;
        this.tvaMapper = tvaMapper;
    }

    /**
     * Save a tva.
     *
     * @param tvaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TvaDTO save(TvaDTO tvaDTO) {
        log.debug("Request to save Tva : {}", tvaDTO);
        Tva tva = tvaMapper.toEntity(tvaDTO);
        tva = tvaRepository.save(tva);
        return tvaMapper.toDto(tva);
    }

    /**
     * Get all the tvas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TvaDTO> findAll() {
        log.debug("Request to get all Tvas");
        return tvaRepository.findAll().stream()
            .map(tvaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tva by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TvaDTO findOne(Long id) {
        log.debug("Request to get Tva : {}", id);
        Tva tva = tvaRepository.findOne(id);
        return tvaMapper.toDto(tva);
    }

    /**
     * Delete the tva by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tva : {}", id);
        tvaRepository.delete(id);
    }
}
