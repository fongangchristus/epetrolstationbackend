package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.DepenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Depense and its DTO DepenseDTO.
 */
@Mapper(componentModel = "spring", uses = {TresorerieMapper.class, IntervenantMapper.class, CategorieDepenseMapper.class, ModeReglementMapper.class})
public interface DepenseMapper extends EntityMapper<DepenseDTO, Depense> {

    @Mapping(source = "hastresro.id", target = "hastresroId")
    @Mapping(source = "hasinterv.id", target = "hasintervId")
    @Mapping(source = "use.id", target = "useId")
    @Mapping(source = "has.id", target = "hasId")
    DepenseDTO toDto(Depense depense);

    @Mapping(source = "hastresroId", target = "hastresro")
    @Mapping(source = "hasintervId", target = "hasinterv")
    @Mapping(source = "useId", target = "use")
    @Mapping(source = "hasId", target = "has")
    Depense toEntity(DepenseDTO depenseDTO);

    default Depense fromId(Long id) {
        if (id == null) {
            return null;
        }
        Depense depense = new Depense();
        depense.setId(id);
        return depense;
    }
}
