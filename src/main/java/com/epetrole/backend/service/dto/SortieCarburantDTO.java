package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SortieCarburant entity.
 */
public class SortieCarburantDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private Double prixTotalht;

    private Double quantite;

    private Double quantiteINit;

    private Double quantiteFinal;

    private Double prixTTC;

    private Long interId;

    private Long carbId;

    private Long pompId;

    private Long modeId;

    private Long tresId;

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

    public Double getQuantiteINit() {
        return quantiteINit;
    }

    public void setQuantiteINit(Double quantiteINit) {
        this.quantiteINit = quantiteINit;
    }

    public Double getQuantiteFinal() {
        return quantiteFinal;
    }

    public void setQuantiteFinal(Double quantiteFinal) {
        this.quantiteFinal = quantiteFinal;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Long getInterId() {
        return interId;
    }

    public void setInterId(Long intervenantId) {
        this.interId = intervenantId;
    }

    public Long getCarbId() {
        return carbId;
    }

    public void setCarbId(Long carburantId) {
        this.carbId = carburantId;
    }

    public Long getPompId() {
        return pompId;
    }

    public void setPompId(Long pompeId) {
        this.pompId = pompeId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeReglementId) {
        this.modeId = modeReglementId;
    }

    public Long getTresId() {
        return tresId;
    }

    public void setTresId(Long tresorerieId) {
        this.tresId = tresorerieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SortieCarburantDTO sortieCarburantDTO = (SortieCarburantDTO) o;
        if(sortieCarburantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieCarburantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieCarburantDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", quantiteINit=" + getQuantiteINit() +
            ", quantiteFinal=" + getQuantiteFinal() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
