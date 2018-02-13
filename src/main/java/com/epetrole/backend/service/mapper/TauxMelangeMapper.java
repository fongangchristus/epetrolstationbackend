package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.TauxMelangeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TauxMelange and its DTO TauxMelangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TauxMelangeMapper extends EntityMapper<TauxMelangeDTO, TauxMelange> {



    default TauxMelange fromId(Long id) {
        if (id == null) {
            return null;
        }
        TauxMelange tauxMelange = new TauxMelange();
        tauxMelange.setId(id);
        return tauxMelange;
    }
}
