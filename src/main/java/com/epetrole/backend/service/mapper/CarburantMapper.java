package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.CarburantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Carburant and its DTO CarburantDTO.
 */
@Mapper(componentModel = "spring", uses = {CatCarburantMapper.class})
public interface CarburantMapper extends EntityMapper<CarburantDTO, Carburant> {

    @Mapping(source = "catCarburant.id", target = "catCarburantId")
    CarburantDTO toDto(Carburant carburant);

    @Mapping(source = "catCarburantId", target = "catCarburant")
    Carburant toEntity(CarburantDTO carburantDTO);

    default Carburant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carburant carburant = new Carburant();
        carburant.setId(id);
        return carburant;
    }
}
