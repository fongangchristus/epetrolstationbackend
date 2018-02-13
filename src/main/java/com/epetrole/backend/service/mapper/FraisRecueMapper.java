package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.FraisRecueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FraisRecue and its DTO FraisRecueDTO.
 */
@Mapper(componentModel = "spring", uses = {TresorerieMapper.class})
public interface FraisRecueMapper extends EntityMapper<FraisRecueDTO, FraisRecue> {

    @Mapping(source = "hastrr.id", target = "hastrrId")
    FraisRecueDTO toDto(FraisRecue fraisRecue);

    @Mapping(source = "hastrrId", target = "hastrr")
    FraisRecue toEntity(FraisRecueDTO fraisRecueDTO);

    default FraisRecue fromId(Long id) {
        if (id == null) {
            return null;
        }
        FraisRecue fraisRecue = new FraisRecue();
        fraisRecue.setId(id);
        return fraisRecue;
    }
}
