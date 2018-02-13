package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.SortieMyserviceService;
import com.epetrole.backend.domain.SortieMyservice;
import com.epetrole.backend.repository.SortieMyserviceRepository;
import com.epetrole.backend.service.dto.SortieMyserviceDTO;
import com.epetrole.backend.service.mapper.SortieMyserviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SortieMyservice.
 */
@Service
@Transactional
public class SortieMyserviceServiceImpl implements SortieMyserviceService {

    private final Logger log = LoggerFactory.getLogger(SortieMyserviceServiceImpl.class);

    private final SortieMyserviceRepository sortieMyserviceRepository;

    private final SortieMyserviceMapper sortieMyserviceMapper;

    public SortieMyserviceServiceImpl(SortieMyserviceRepository sortieMyserviceRepository, SortieMyserviceMapper sortieMyserviceMapper) {
        this.sortieMyserviceRepository = sortieMyserviceRepository;
        this.sortieMyserviceMapper = sortieMyserviceMapper;
    }

    /**
     * Save a sortieMyservice.
     *
     * @param sortieMyserviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SortieMyserviceDTO save(SortieMyserviceDTO sortieMyserviceDTO) {
        log.debug("Request to save SortieMyservice : {}", sortieMyserviceDTO);
        SortieMyservice sortieMyservice = sortieMyserviceMapper.toEntity(sortieMyserviceDTO);
        sortieMyservice = sortieMyserviceRepository.save(sortieMyservice);
        return sortieMyserviceMapper.toDto(sortieMyservice);
    }

    /**
     * Get all the sortieMyservices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SortieMyserviceDTO> findAll() {
        log.debug("Request to get all SortieMyservices");
        return sortieMyserviceRepository.findAll().stream()
            .map(sortieMyserviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sortieMyservice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SortieMyserviceDTO findOne(Long id) {
        log.debug("Request to get SortieMyservice : {}", id);
        SortieMyservice sortieMyservice = sortieMyserviceRepository.findOne(id);
        return sortieMyserviceMapper.toDto(sortieMyservice);
    }

    /**
     * Delete the sortieMyservice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SortieMyservice : {}", id);
        sortieMyserviceRepository.delete(id);
    }
}
