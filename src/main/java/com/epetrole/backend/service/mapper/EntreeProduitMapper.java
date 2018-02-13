package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.EntreeProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntreeProduit and its DTO EntreeProduitDTO.
 */
@Mapper(componentModel = "spring", uses = {ModeReglementMapper.class, TresorerieMapper.class, ProduitMapper.class})
public interface EntreeProduitMapper extends EntityMapper<EntreeProduitDTO, EntreeProduit> {

    @Mapping(source = "modeR.id", target = "modeRId")
    @Mapping(source = "tresorerie.id", target = "tresorerieId")
    @Mapping(source = "produit.id", target = "produitId")
    EntreeProduitDTO toDto(EntreeProduit entreeProduit);

    @Mapping(source = "modeRId", target = "modeR")
    @Mapping(source = "tresorerieId", target = "tresorerie")
    @Mapping(source = "produitId", target = "produit")
    @Mapping(target = "commercials", ignore = true)
    EntreeProduit toEntity(EntreeProduitDTO entreeProduitDTO);

    default EntreeProduit fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntreeProduit entreeProduit = new EntreeProduit();
        entreeProduit.setId(id);
        return entreeProduit;
    }
}
