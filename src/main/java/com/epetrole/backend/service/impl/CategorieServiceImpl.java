package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.CategorieService;
import com.epetrole.backend.domain.Categorie;
import com.epetrole.backend.repository.CategorieRepository;
import com.epetrole.backend.service.dto.CategorieDTO;
import com.epetrole.backend.service.mapper.CategorieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Categorie.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieServiceImpl.class);

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    public CategorieServiceImpl(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategorieDTO save(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(categorie);
    }

    /**
     * Get all the categories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategorieDTO> findAll() {
        log.debug("Request to get all Categories");
        return categorieRepository.findAll().stream()
            .map(categorieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categorie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategorieDTO findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        Categorie categorie = categorieRepository.findOne(id);
        return categorieMapper.toDto(categorie);
    }

    /**
     * Delete the categorie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categorie : {}", id);
        categorieRepository.delete(id);
    }
}
