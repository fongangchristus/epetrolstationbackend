package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.TypeTresorerieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeTresorerie and its DTO TypeTresorerieDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeTresorerieMapper extends EntityMapper<TypeTresorerieDTO, TypeTresorerie> {



    default TypeTresorerie fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeTresorerie typeTresorerie = new TypeTresorerie();
        typeTresorerie.setId(id);
        return typeTresorerie;
    }
}
