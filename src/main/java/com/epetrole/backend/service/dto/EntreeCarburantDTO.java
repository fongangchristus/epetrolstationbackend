package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EntreeCarburant entity.
 */
public class EntreeCarburantDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private Double prixTotalht;

    private Double quantite;

    private Double prixTTC;

    private Long modeReglementId;

    private Long carburantId;

    private Long tresorerieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Long getModeReglementId() {
        return modeReglementId;
    }

    public void setModeReglementId(Long modeReglementId) {
        this.modeReglementId = modeReglementId;
    }

    public Long getCarburantId() {
        return carburantId;
    }

    public void setCarburantId(Long carburantId) {
        this.carburantId = carburantId;
    }

    public Long getTresorerieId() {
        return tresorerieId;
    }

    public void setTresorerieId(Long tresorerieId) {
        this.tresorerieId = tresorerieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntreeCarburantDTO entreeCarburantDTO = (EntreeCarburantDTO) o;
        if(entreeCarburantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeCarburantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeCarburantDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
