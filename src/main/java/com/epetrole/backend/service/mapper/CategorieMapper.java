package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.CategorieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Categorie and its DTO CategorieDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {


    @Mapping(target = "hasmanies", ignore = true)
    Categorie toEntity(CategorieDTO categorieDTO);

    default Categorie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categorie categorie = new Categorie();
        categorie.setId(id);
        return categorie;
    }
}
