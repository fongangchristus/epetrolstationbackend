package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.TypeTresorerieService;
import com.epetrole.backend.domain.TypeTresorerie;
import com.epetrole.backend.repository.TypeTresorerieRepository;
import com.epetrole.backend.service.dto.TypeTresorerieDTO;
import com.epetrole.backend.service.mapper.TypeTresorerieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TypeTresorerie.
 */
@Service
@Transactional
public class TypeTresorerieServiceImpl implements TypeTresorerieService {

    private final Logger log = LoggerFactory.getLogger(TypeTresorerieServiceImpl.class);

    private final TypeTresorerieRepository typeTresorerieRepository;

    private final TypeTresorerieMapper typeTresorerieMapper;

    public TypeTresorerieServiceImpl(TypeTresorerieRepository typeTresorerieRepository, TypeTresorerieMapper typeTresorerieMapper) {
        this.typeTresorerieRepository = typeTresorerieRepository;
        this.typeTresorerieMapper = typeTresorerieMapper;
    }

    /**
     * Save a typeTresorerie.
     *
     * @param typeTresorerieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeTresorerieDTO save(TypeTresorerieDTO typeTresorerieDTO) {
        log.debug("Request to save TypeTresorerie : {}", typeTresorerieDTO);
        TypeTresorerie typeTresorerie = typeTresorerieMapper.toEntity(typeTresorerieDTO);
        typeTresorerie = typeTresorerieRepository.save(typeTresorerie);
        return typeTresorerieMapper.toDto(typeTresorerie);
    }

    /**
     * Get all the typeTresoreries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeTresorerieDTO> findAll() {
        log.debug("Request to get all TypeTresoreries");
        return typeTresorerieRepository.findAll().stream()
            .map(typeTresorerieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one typeTresorerie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeTresorerieDTO findOne(Long id) {
        log.debug("Request to get TypeTresorerie : {}", id);
        TypeTresorerie typeTresorerie = typeTresorerieRepository.findOne(id);
        return typeTresorerieMapper.toDto(typeTresorerie);
    }

    /**
     * Delete the typeTresorerie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeTresorerie : {}", id);
        typeTresorerieRepository.delete(id);
    }
}
