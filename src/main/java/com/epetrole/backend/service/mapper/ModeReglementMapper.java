package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.ModeReglementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModeReglement and its DTO ModeReglementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModeReglementMapper extends EntityMapper<ModeReglementDTO, ModeReglement> {



    default ModeReglement fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModeReglement modeReglement = new ModeReglement();
        modeReglement.setId(id);
        return modeReglement;
    }
}
