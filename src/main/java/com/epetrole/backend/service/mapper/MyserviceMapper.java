package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.MyserviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Myservice and its DTO MyserviceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyserviceMapper extends EntityMapper<MyserviceDTO, Myservice> {



    default Myservice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Myservice myservice = new Myservice();
        myservice.setId(id);
        return myservice;
    }
}
