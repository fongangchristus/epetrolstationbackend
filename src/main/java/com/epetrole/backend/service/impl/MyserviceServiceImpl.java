package com.epetrole.backend.service.impl;

import com.epetrole.backend.service.MyserviceService;
import com.epetrole.backend.domain.Myservice;
import com.epetrole.backend.repository.MyserviceRepository;
import com.epetrole.backend.service.dto.MyserviceDTO;
import com.epetrole.backend.service.mapper.MyserviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Myservice.
 */
@Service
@Transactional
public class MyserviceServiceImpl implements MyserviceService {

    private final Logger log = LoggerFactory.getLogger(MyserviceServiceImpl.class);

    private final MyserviceRepository myserviceRepository;

    private final MyserviceMapper myserviceMapper;

    public MyserviceServiceImpl(MyserviceRepository myserviceRepository, MyserviceMapper myserviceMapper) {
        this.myserviceRepository = myserviceRepository;
        this.myserviceMapper = myserviceMapper;
    }

    /**
     * Save a myservice.
     *
     * @param myserviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MyserviceDTO save(MyserviceDTO myserviceDTO) {
        log.debug("Request to save Myservice : {}", myserviceDTO);
        Myservice myservice = myserviceMapper.toEntity(myserviceDTO);
        myservice = myserviceRepository.save(myservice);
        return myserviceMapper.toDto(myservice);
    }

    /**
     * Get all the myservices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MyserviceDTO> findAll() {
        log.debug("Request to get all Myservices");
        return myserviceRepository.findAll().stream()
            .map(myserviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one myservice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MyserviceDTO findOne(Long id) {
        log.debug("Request to get Myservice : {}", id);
        Myservice myservice = myserviceRepository.findOne(id);
        return myserviceMapper.toDto(myservice);
    }

    /**
     * Delete the myservice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Myservice : {}", id);
        myserviceRepository.delete(id);
    }
}
