package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.CatCarburantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CatCarburant and its DTO CatCarburantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatCarburantMapper extends EntityMapper<CatCarburantDTO, CatCarburant> {


    @Mapping(target = "have", ignore = true)
    CatCarburant toEntity(CatCarburantDTO catCarburantDTO);

    default CatCarburant fromId(Long id) {
        if (id == null) {
            return null;
        }
        CatCarburant catCarburant = new CatCarburant();
        catCarburant.setId(id);
        return catCarburant;
    }
}
