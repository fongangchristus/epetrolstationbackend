package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Citerne entity.
 */
public class CiterneDTO implements Serializable {

    private Long id;

    private String volumeInit;

    private String volumeMax;

    private Long hascId;

    private Long hasuId;

    private Long haiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolumeInit() {
        return volumeInit;
    }

    public void setVolumeInit(String volumeInit) {
        this.volumeInit = volumeInit;
    }

    public String getVolumeMax() {
        return volumeMax;
    }

    public void setVolumeMax(String volumeMax) {
        this.volumeMax = volumeMax;
    }

    public Long getHascId() {
        return hascId;
    }

    public void setHascId(Long catCarburantId) {
        this.hascId = catCarburantId;
    }

    public Long getHasuId() {
        return hasuId;
    }

    public void setHasuId(Long uniteId) {
        this.hasuId = uniteId;
    }

    public Long getHaiId() {
        return haiId;
    }

    public void setHaiId(Long stationId) {
        this.haiId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiterneDTO citerneDTO = (CiterneDTO) o;
        if(citerneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), citerneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiterneDTO{" +
            "id=" + getId() +
            ", volumeInit='" + getVolumeInit() + "'" +
            ", volumeMax='" + getVolumeMax() + "'" +
            "}";
    }
}
