package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tva entity.
 */
public class TvaDTO implements Serializable {

    private Long id;

    private String modeTva;

    private String tauxTva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModeTva() {
        return modeTva;
    }

    public void setModeTva(String modeTva) {
        this.modeTva = modeTva;
    }

    public String getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(String tauxTva) {
        this.tauxTva = tauxTva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TvaDTO tvaDTO = (TvaDTO) o;
        if(tvaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tvaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TvaDTO{" +
            "id=" + getId() +
            ", modeTva='" + getModeTva() + "'" +
            ", tauxTva='" + getTauxTva() + "'" +
            "}";
    }
}
