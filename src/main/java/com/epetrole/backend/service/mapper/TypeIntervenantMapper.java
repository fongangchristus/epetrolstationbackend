package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.TypeIntervenantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeIntervenant and its DTO TypeIntervenantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeIntervenantMapper extends EntityMapper<TypeIntervenantDTO, TypeIntervenant> {



    default TypeIntervenant fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeIntervenant typeIntervenant = new TypeIntervenant();
        typeIntervenant.setId(id);
        return typeIntervenant;
    }
}
