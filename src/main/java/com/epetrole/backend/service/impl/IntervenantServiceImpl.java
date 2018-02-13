package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.IntervenantService;
import com.epetrole.backend.domain.Intervenant;
import com.epetrole.backend.repository.IntervenantRepository;
import com.epetrole.backend.service.dto.IntervenantDTO;
import com.epetrole.backend.service.mapper.IntervenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Intervenant.
 */
@Service
@Transactional
public class IntervenantServiceImpl implements IntervenantService {

    private final Logger log = LoggerFactory.getLogger(IntervenantServiceImpl.class);

    private final IntervenantRepository intervenantRepository;

    private final IntervenantMapper intervenantMapper;

    public IntervenantServiceImpl(IntervenantRepository intervenantRepository, IntervenantMapper intervenantMapper) {
        this.intervenantRepository = intervenantRepository;
        this.intervenantMapper = intervenantMapper;
    }

    /**
     * Save a intervenant.
     *
     * @param intervenantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IntervenantDTO save(IntervenantDTO intervenantDTO) {
        log.debug("Request to save Intervenant : {}", intervenantDTO);
        Intervenant intervenant = intervenantMapper.toEntity(intervenantDTO);
        intervenant = intervenantRepository.save(intervenant);
        return intervenantMapper.toDto(intervenant);
    }

    /**
     * Get all the intervenants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IntervenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Intervenants");
        return intervenantRepository.findAll(pageable)
            .map(intervenantMapper::toDto);
    }

    /**
     * Get one intervenant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IntervenantDTO findOne(Long id) {
        log.debug("Request to get Intervenant : {}", id);
        Intervenant intervenant = intervenantRepository.findOne(id);
        return intervenantMapper.toDto(intervenant);
    }

    /**
     * Delete the intervenant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intervenant : {}", id);
        intervenantRepository.delete(id);
    }
}
