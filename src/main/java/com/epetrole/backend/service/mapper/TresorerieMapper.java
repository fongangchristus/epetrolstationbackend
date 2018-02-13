package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.TresorerieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tresorerie and its DTO TresorerieDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeTresorerieMapper.class})
public interface TresorerieMapper extends EntityMapper<TresorerieDTO, Tresorerie> {

    @Mapping(source = "hastypet.id", target = "hastypetId")
    TresorerieDTO toDto(Tresorerie tresorerie);

    @Mapping(source = "hastypetId", target = "hastypet")
    Tresorerie toEntity(TresorerieDTO tresorerieDTO);

    default Tresorerie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tresorerie tresorerie = new Tresorerie();
        tresorerie.setId(id);
        return tresorerie;
    }
}
