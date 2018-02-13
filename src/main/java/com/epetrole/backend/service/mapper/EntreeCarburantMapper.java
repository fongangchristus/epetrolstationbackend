package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.EntreeCarburantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntreeCarburant and its DTO EntreeCarburantDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeReglementMapper.class, CarburantMapper.class, TresorerieMapper.class})
public interface EntreeCarburantMapper extends EntityMapper<EntreeCarburantDTO, EntreeCarburant> {

    @Mapping(source = "modeReglement.id", target = "modeReglementId")
    @Mapping(source = "carburant.id", target = "carburantId")
    @Mapping(source = "tresorerie.id", target = "tresorerieId")
    EntreeCarburantDTO toDto(EntreeCarburant entreeCarburant);

    @Mapping(source = "modeReglementId", target = "modeReglement")
    @Mapping(source = "carburantId", target = "carburant")
    @Mapping(source = "tresorerieId", target = "tresorerie")
    @Mapping(target = "pompistes", ignore = true)
    EntreeCarburant toEntity(EntreeCarburantDTO entreeCarburantDTO);

    default EntreeCarburant fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntreeCarburant entreeCarburant = new EntreeCarburant();
        entreeCarburant.setId(id);
        return entreeCarburant;
    }
}
