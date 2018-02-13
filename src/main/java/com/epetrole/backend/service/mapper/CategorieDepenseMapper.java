package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.CategorieDepenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CategorieDepense and its DTO CategorieDepenseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieDepenseMapper extends EntityMapper<CategorieDepenseDTO, CategorieDepense> {



    default CategorieDepense fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategorieDepense categorieDepense = new CategorieDepense();
        categorieDepense.setId(id);
        return categorieDepense;
    }
}
