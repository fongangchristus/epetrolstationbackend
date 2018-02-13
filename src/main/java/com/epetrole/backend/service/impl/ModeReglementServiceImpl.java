package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.ModeReglementService;
import com.epetrole.backend.domain.ModeReglement;
import com.epetrole.backend.repository.ModeReglementRepository;
import com.epetrole.backend.service.dto.ModeReglementDTO;
import com.epetrole.backend.service.mapper.ModeReglementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ModeReglement.
 */
@Service
@Transactional
public class ModeReglementServiceImpl implements ModeReglementService {

    private final Logger log = LoggerFactory.getLogger(ModeReglementServiceImpl.class);

    private final ModeReglementRepository modeReglementRepository;

    private final ModeReglementMapper modeReglementMapper;

    public ModeReglementServiceImpl(ModeReglementRepository modeReglementRepository, ModeReglementMapper modeReglementMapper) {
        this.modeReglementRepository = modeReglementRepository;
        this.modeReglementMapper = modeReglementMapper;
    }

    /**
     * Save a modeReglement.
     *
     * @param modeReglementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeReglementDTO save(ModeReglementDTO modeReglementDTO) {
        log.debug("Request to save ModeReglement : {}", modeReglementDTO);
        ModeReglement modeReglement = modeReglementMapper.toEntity(modeReglementDTO);
        modeReglement = modeReglementRepository.save(modeReglement);
        return modeReglementMapper.toDto(modeReglement);
    }

    /**
     * Get all the modeReglements.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModeReglementDTO> findAll() {
        log.debug("Request to get all ModeReglements");
        return modeReglementRepository.findAll().stream()
            .map(modeReglementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one modeReglement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModeReglementDTO findOne(Long id) {
        log.debug("Request to get ModeReglement : {}", id);
        ModeReglement modeReglement = modeReglementRepository.findOne(id);
        return modeReglementMapper.toDto(modeReglement);
    }

    /**
     * Delete the modeReglement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeReglement : {}", id);
        modeReglementRepository.delete(id);
    }
}
