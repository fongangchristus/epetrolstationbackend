package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.SortieCarburantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SortieCarburant and its DTO SortieCarburantDTO.
 */
@Mapper(componentModel = "spring", uses = {IntervenantMapper.class, CarburantMapper.class, PompeMapper.class, ModeReglementMapper.class, TresorerieMapper.class})
public interface SortieCarburantMapper extends EntityMapper<SortieCarburantDTO, SortieCarburant> {

    @Mapping(source = "inter.id", target = "interId")
    @Mapping(source = "carb.id", target = "carbId")
    @Mapping(source = "pomp.id", target = "pompId")
    @Mapping(source = "mode.id", target = "modeId")
    @Mapping(source = "tres.id", target = "tresId")
    SortieCarburantDTO toDto(SortieCarburant sortieCarburant);

    @Mapping(source = "interId", target = "inter")
    @Mapping(source = "carbId", target = "carb")
    @Mapping(source = "pompId", target = "pomp")
    @Mapping(source = "modeId", target = "mode")
    @Mapping(source = "tresId", target = "tres")
    SortieCarburant toEntity(SortieCarburantDTO sortieCarburantDTO);

    default SortieCarburant fromId(Long id) {
        if (id == null) {
            return null;
        }
        SortieCarburant sortieCarburant = new SortieCarburant();
        sortieCarburant.setId(id);
        return sortieCarburant;
    }
}
