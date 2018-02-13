package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.TypeIntervenantService;
import com.epetrole.backend.domain.TypeIntervenant;
import com.epetrole.backend.repository.TypeIntervenantRepository;
import com.epetrole.backend.service.dto.TypeIntervenantDTO;
import com.epetrole.backend.service.mapper.TypeIntervenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TypeIntervenant.
 */
@Service
@Transactional
public class TypeIntervenantServiceImpl implements TypeIntervenantService {

    private final Logger log = LoggerFactory.getLogger(TypeIntervenantServiceImpl.class);

    private final TypeIntervenantRepository typeIntervenantRepository;

    private final TypeIntervenantMapper typeIntervenantMapper;

    public TypeIntervenantServiceImpl(TypeIntervenantRepository typeIntervenantRepository, TypeIntervenantMapper typeIntervenantMapper) {
        this.typeIntervenantRepository = typeIntervenantRepository;
        this.typeIntervenantMapper = typeIntervenantMapper;
    }

    /**
     * Save a typeIntervenant.
     *
     * @param typeIntervenantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeIntervenantDTO save(TypeIntervenantDTO typeIntervenantDTO) {
        log.debug("Request to save TypeIntervenant : {}", typeIntervenantDTO);
        TypeIntervenant typeIntervenant = typeIntervenantMapper.toEntity(typeIntervenantDTO);
        typeIntervenant = typeIntervenantRepository.save(typeIntervenant);
        return typeIntervenantMapper.toDto(typeIntervenant);
    }

    /**
     * Get all the typeIntervenants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeIntervenantDTO> findAll() {
        log.debug("Request to get all TypeIntervenants");
        return typeIntervenantRepository.findAll().stream()
            .map(typeIntervenantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one typeIntervenant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeIntervenantDTO findOne(Long id) {
        log.debug("Request to get TypeIntervenant : {}", id);
        TypeIntervenant typeIntervenant = typeIntervenantRepository.findOne(id);
        return typeIntervenantMapper.toDto(typeIntervenant);
    }

    /**
     * Delete the typeIntervenant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeIntervenant : {}", id);
        typeIntervenantRepository.delete(id);
    }
}
