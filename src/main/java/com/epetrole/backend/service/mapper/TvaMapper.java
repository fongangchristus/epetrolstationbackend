package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.TvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tva and its DTO TvaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TvaMapper extends EntityMapper<TvaDTO, Tva> {



    default Tva fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tva tva = new Tva();
        tva.setId(id);
        return tva;
    }
}
