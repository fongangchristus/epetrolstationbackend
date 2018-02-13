package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.CiterneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Citerne and its DTO CiterneDTO.
 */
@Mapper(componentModel = "spring", uses = {CatCarburantMapper.class, UniteMapper.class, StationMapper.class})
public interface CiterneMapper extends EntityMapper<CiterneDTO, Citerne> {

    @Mapping(source = "hasc.id", target = "hascId")
    @Mapping(source = "hasu.id", target = "hasuId")
    @Mapping(source = "hai.id", target = "haiId")
    CiterneDTO toDto(Citerne citerne);

    @Mapping(source = "hascId", target = "hasc")
    @Mapping(source = "hasuId", target = "hasu")
    @Mapping(source = "haiId", target = "hai")
    Citerne toEntity(CiterneDTO citerneDTO);

    default Citerne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Citerne citerne = new Citerne();
        citerne.setId(id);
        return citerne;
    }
}
