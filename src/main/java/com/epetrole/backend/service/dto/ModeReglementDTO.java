package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ModeReglement entity.
 */
public class ModeReglementDTO implements Serializable {

    private Long id;

    private String code;

    private String libele;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModeReglementDTO modeReglementDTO = (ModeReglementDTO) o;
        if(modeReglementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modeReglementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModeReglementDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libele='" + getLibele() + "'" +
            "}";
    }
}
