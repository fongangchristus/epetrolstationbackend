package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.TresorerieService;
import com.epetrole.backend.domain.Tresorerie;
import com.epetrole.backend.repository.TresorerieRepository;
import com.epetrole.backend.service.dto.TresorerieDTO;
import com.epetrole.backend.service.mapper.TresorerieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tresorerie.
 */
@Service
@Transactional
public class TresorerieServiceImpl implements TresorerieService {

    private final Logger log = LoggerFactory.getLogger(TresorerieServiceImpl.class);

    private final TresorerieRepository tresorerieRepository;

    private final TresorerieMapper tresorerieMapper;

    public TresorerieServiceImpl(TresorerieRepository tresorerieRepository, TresorerieMapper tresorerieMapper) {
        this.tresorerieRepository = tresorerieRepository;
        this.tresorerieMapper = tresorerieMapper;
    }

    /**
     * Save a tresorerie.
     *
     * @param tresorerieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TresorerieDTO save(TresorerieDTO tresorerieDTO) {
        log.debug("Request to save Tresorerie : {}", tresorerieDTO);
        Tresorerie tresorerie = tresorerieMapper.toEntity(tresorerieDTO);
        tresorerie = tresorerieRepository.save(tresorerie);
        return tresorerieMapper.toDto(tresorerie);
    }

    /**
     * Get all the tresoreries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TresorerieDTO> findAll() {
        log.debug("Request to get all Tresoreries");
        return tresorerieRepository.findAll().stream()
            .map(tresorerieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tresorerie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TresorerieDTO findOne(Long id) {
        log.debug("Request to get Tresorerie : {}", id);
        Tresorerie tresorerie = tresorerieRepository.findOne(id);
        return tresorerieMapper.toDto(tresorerie);
    }

    /**
     * Delete the tresorerie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tresorerie : {}", id);
        tresorerieRepository.delete(id);
    }
}
