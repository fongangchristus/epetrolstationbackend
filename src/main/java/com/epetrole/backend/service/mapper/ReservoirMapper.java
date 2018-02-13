package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.ReservoirDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservoir and its DTO ReservoirDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReservoirMapper extends EntityMapper<ReservoirDTO, Reservoir> {



    default Reservoir fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservoir reservoir = new Reservoir();
        reservoir.setId(id);
        return reservoir;
    }
}
