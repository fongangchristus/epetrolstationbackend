package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.CategorieDepenseService;
import com.epetrole.backend.domain.CategorieDepense;
import com.epetrole.backend.repository.CategorieDepenseRepository;
import com.epetrole.backend.service.dto.CategorieDepenseDTO;
import com.epetrole.backend.service.mapper.CategorieDepenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CategorieDepense.
 */
@Service
@Transactional
public class CategorieDepenseServiceImpl implements CategorieDepenseService {

    private final Logger log = LoggerFactory.getLogger(CategorieDepenseServiceImpl.class);

    private final CategorieDepenseRepository categorieDepenseRepository;

    private final CategorieDepenseMapper categorieDepenseMapper;

    public CategorieDepenseServiceImpl(CategorieDepenseRepository categorieDepenseRepository, CategorieDepenseMapper categorieDepenseMapper) {
        this.categorieDepenseRepository = categorieDepenseRepository;
        this.categorieDepenseMapper = categorieDepenseMapper;
    }

    /**
     * Save a categorieDepense.
     *
     * @param categorieDepenseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategorieDepenseDTO save(CategorieDepenseDTO categorieDepenseDTO) {
        log.debug("Request to save CategorieDepense : {}", categorieDepenseDTO);
        CategorieDepense categorieDepense = categorieDepenseMapper.toEntity(categorieDepenseDTO);
        categorieDepense = categorieDepenseRepository.save(categorieDepense);
        return categorieDepenseMapper.toDto(categorieDepense);
    }

    /**
     * Get all the categorieDepenses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategorieDepenseDTO> findAll() {
        log.debug("Request to get all CategorieDepenses");
        return categorieDepenseRepository.findAll().stream()
            .map(categorieDepenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categorieDepense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategorieDepenseDTO findOne(Long id) {
        log.debug("Request to get CategorieDepense : {}", id);
        CategorieDepense categorieDepense = categorieDepenseRepository.findOne(id);
        return categorieDepenseMapper.toDto(categorieDepense);
    }

    /**
     * Delete the categorieDepense by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieDepense : {}", id);
        categorieDepenseRepository.delete(id);
    }
}
