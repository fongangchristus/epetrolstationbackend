package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.PompeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pompe and its DTO PompeDTO.
 */
@Mapper(componentModel = "spring", uses = {CiterneMapper.class, ReservoirMapper.class, TauxMelangeMapper.class, CatCarburantMapper.class})
public interface PompeMapper extends EntityMapper<PompeDTO, Pompe> {

    @Mapping(source = "hasci.id", target = "hasciId")
    @Mapping(source = "hasre.id", target = "hasreId")
    @Mapping(source = "hasta.id", target = "hastaId")
    @Mapping(source = "hasca.id", target = "hascaId")
    PompeDTO toDto(Pompe pompe);

    @Mapping(source = "hasciId", target = "hasci")
    @Mapping(source = "hasreId", target = "hasre")
    @Mapping(source = "hastaId", target = "hasta")
    @Mapping(source = "hascaId", target = "hasca")
    Pompe toEntity(PompeDTO pompeDTO);

    default Pompe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pompe pompe = new Pompe();
        pompe.setId(id);
        return pompe;
    }
}
