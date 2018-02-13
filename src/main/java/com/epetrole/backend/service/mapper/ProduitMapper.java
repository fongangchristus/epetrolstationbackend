package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.ProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produit and its DTO ProduitDTO.
 */
@Mapper(componentModel = "spring", uses = {TvaMapper.class, CategorieMapper.class})
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {

    @Mapping(source = "tva.id", target = "tvaId")
    @Mapping(source = "categorie.id", target = "categorieId")
    ProduitDTO toDto(Produit produit);

    @Mapping(source = "tvaId", target = "tva")
    @Mapping(source = "categorieId", target = "categorie")
    Produit toEntity(ProduitDTO produitDTO);

    default Produit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }
}
