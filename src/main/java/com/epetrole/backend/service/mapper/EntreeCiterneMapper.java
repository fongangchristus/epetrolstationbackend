package com.epetrole.backend.service.mapper;

import com.epetrole.backend.domain.*;
import com.epetrole.backend.service.dto.EntreeCiterneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntreeCiterne and its DTO EntreeCiterneDTO.
 */
@Mapper(componentModel = "spring", uses = {CiterneMapper.class, UniteMapper.class, IntervenantMapper.class})
public interface EntreeCiterneMapper extends EntityMapper<EntreeCiterneDTO, EntreeCiterne> {

    @Mapping(source = "hasciterne.id", target = "hasciterneId")
    @Mapping(source = "hasunite.id", target = "hasuniteId")
    @Mapping(source = "hasi.id", target = "hasiId")
    EntreeCiterneDTO toDto(EntreeCiterne entreeCiterne);

    @Mapping(source = "hasciterneId", target = "hasciterne")
    @Mapping(source = "hasuniteId", target = "hasunite")
    @Mapping(source = "hasiId", target = "hasi")
    EntreeCiterne toEntity(EntreeCiterneDTO entreeCiterneDTO);

    default EntreeCiterne fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntreeCiterne entreeCiterne = new EntreeCiterne();
        entreeCiterne.setId(id);
        return entreeCiterne;
    }
}
