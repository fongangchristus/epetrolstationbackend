package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.IntervenantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Intervenant and its DTO IntervenantDTO.
 */
@Mapper(componentModel = "spring", uses = {EntreeProduitMapper.class, EntreeCarburantMapper.class, TypeIntervenantMapper.class, UserMapper.class, StationMapper.class})
public interface IntervenantMapper extends EntityMapper<IntervenantDTO, Intervenant> {

    @Mapping(source = "entreeProduit.id", target = "entreeProduitId")
    @Mapping(source = "entreeCarburant.id", target = "entreeCarburantId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "typeIntervenant.id", target = "typeIntervenantId")
    @Mapping(source = "is.id", target = "isId")
    @Mapping(source = "station.id", target = "stationId")
    IntervenantDTO toDto(Intervenant intervenant);

    @Mapping(source = "entreeProduitId", target = "entreeProduit")
    @Mapping(source = "entreeCarburantId", target = "entreeCarburant")
    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "typeIntervenantId", target = "typeIntervenant")
    @Mapping(source = "isId", target = "is")
    @Mapping(source = "stationId", target = "station")
    Intervenant toEntity(IntervenantDTO intervenantDTO);

    default Intervenant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intervenant intervenant = new Intervenant();
        intervenant.setId(id);
        return intervenant;
    }
}
