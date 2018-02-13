package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.SortieProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SortieProduit and its DTO SortieProduitDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeReglementMapper.class, IntervenantMapper.class, TresorerieMapper.class, ProduitMapper.class})
public interface SortieProduitMapper extends EntityMapper<SortieProduitDTO, SortieProduit> {

    @Mapping(source = "modeReg.id", target = "modeRegId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "tresor.id", target = "tresorId")
    @Mapping(source = "prod.id", target = "prodId")
    SortieProduitDTO toDto(SortieProduit sortieProduit);

    @Mapping(source = "modeRegId", target = "modeReg")
    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "tresorId", target = "tresor")
    @Mapping(source = "prodId", target = "prod")
    SortieProduit toEntity(SortieProduitDTO sortieProduitDTO);

    default SortieProduit fromId(Long id) {
        if (id == null) {
            return null;
        }
        SortieProduit sortieProduit = new SortieProduit();
        sortieProduit.setId(id);
        return sortieProduit;
    }
}
