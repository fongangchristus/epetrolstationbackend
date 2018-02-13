package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.UniteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Unite and its DTO UniteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UniteMapper extends EntityMapper<UniteDTO, Unite> {



    default Unite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Unite unite = new Unite();
        unite.setId(id);
        return unite;
    }
}
