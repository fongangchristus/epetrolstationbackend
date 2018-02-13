package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.StationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Station and its DTO StationDTO.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class})
public interface StationMapper extends EntityMapper<StationDTO, Station> {

    @Mapping(source = "adresse.id", target = "adresseId")
    StationDTO toDto(Station station);

    @Mapping(source = "adresseId", target = "adresse")
    @Mapping(target = "intervenants", ignore = true)
    Station toEntity(StationDTO stationDTO);

    default Station fromId(Long id) {
        if (id == null) {
            return null;
        }
        Station station = new Station();
        station.setId(id);
        return station;
    }
}
