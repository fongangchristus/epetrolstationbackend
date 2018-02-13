package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.CiterneService;
import com.epetrole.backend.domain.Citerne;
import com.epetrole.backend.repository.CiterneRepository;
import com.epetrole.backend.service.dto.CiterneDTO;
import com.epetrole.backend.service.mapper.CiterneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Citerne.
 */
@Service
@Transactional
public class CiterneServiceImpl implements CiterneService {

    private final Logger log = LoggerFactory.getLogger(CiterneServiceImpl.class);

    private final CiterneRepository citerneRepository;

    private final CiterneMapper citerneMapper;

    public CiterneServiceImpl(CiterneRepository citerneRepository, CiterneMapper citerneMapper) {
        this.citerneRepository = citerneRepository;
        this.citerneMapper = citerneMapper;
    }

    /**
     * Save a citerne.
     *
     * @param citerneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CiterneDTO save(CiterneDTO citerneDTO) {
        log.debug("Request to save Citerne : {}", citerneDTO);
        Citerne citerne = citerneMapper.toEntity(citerneDTO);
        citerne = citerneRepository.save(citerne);
        return citerneMapper.toDto(citerne);
    }

    /**
     * Get all the citernes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiterneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Citernes");
        return citerneRepository.findAll(pageable)
            .map(citerneMapper::toDto);
    }

    /**
     * Get one citerne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CiterneDTO findOne(Long id) {
        log.debug("Request to get Citerne : {}", id);
        Citerne citerne = citerneRepository.findOne(id);
        return citerneMapper.toDto(citerne);
    }

    /**
     * Delete the citerne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Citerne : {}", id);
        citerneRepository.delete(id);
    }
}
