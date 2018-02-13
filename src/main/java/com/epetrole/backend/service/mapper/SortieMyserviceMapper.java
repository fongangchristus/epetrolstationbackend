package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.SortieMyserviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SortieMyservice and its DTO SortieMyserviceDTO.
 */
@Mapper(componentModel = "spring", uses = {MyserviceMapper.class, IntervenantMapper.class, ModeReglementMapper.class, TresorerieMapper.class})
public interface SortieMyserviceMapper extends EntityMapper<SortieMyserviceDTO, SortieMyservice> {

    @Mapping(source = "serv.id", target = "servId")
    @Mapping(source = "hasinter.id", target = "hasinterId")
    @Mapping(source = "hasModeR.id", target = "hasModeRId")
    @Mapping(source = "hastresor.id", target = "hastresorId")
    @Mapping(source = "hastre.id", target = "hastreId")
    SortieMyserviceDTO toDto(SortieMyservice sortieMyservice);

    @Mapping(source = "servId", target = "serv")
    @Mapping(source = "hasinterId", target = "hasinter")
    @Mapping(source = "hasModeRId", target = "hasModeR")
    @Mapping(source = "hastresorId", target = "hastresor")
    @Mapping(source = "hastreId", target = "hastre")
    SortieMyservice toEntity(SortieMyserviceDTO sortieMyserviceDTO);

    default SortieMyservice fromId(Long id) {
        if (id == null) {
            return null;
        }
        SortieMyservice sortieMyservice = new SortieMyservice();
        sortieMyservice.setId(id);
        return sortieMyservice;
    }
}
