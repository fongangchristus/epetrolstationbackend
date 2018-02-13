package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TauxMelange entity.
 */
public class TauxMelangeDTO implements Serializable {

    private Long id;

    private Double tauxMelange;

    private Double prixMelange;

    private Boolean tauxEnCours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTauxMelange() {
        return tauxMelange;
    }

    public void setTauxMelange(Double tauxMelange) {
        this.tauxMelange = tauxMelange;
    }

    public Double getPrixMelange() {
        return prixMelange;
    }

    public void setPrixMelange(Double prixMelange) {
        this.prixMelange = prixMelange;
    }

    public Boolean isTauxEnCours() {
        return tauxEnCours;
    }

    public void setTauxEnCours(Boolean tauxEnCours) {
        this.tauxEnCours = tauxEnCours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TauxMelangeDTO tauxMelangeDTO = (TauxMelangeDTO) o;
        if(tauxMelangeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tauxMelangeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TauxMelangeDTO{" +
            "id=" + getId() +
            ", tauxMelange=" + getTauxMelange() +
            ", prixMelange=" + getPrixMelange() +
            ", tauxEnCours='" + isTauxEnCours() + "'" +
            "}";
    }
}
