package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SortieProduit entity.
 */
public class SortieProduitDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private String motif;

    private Double prixTotalht;

    private Double quantite;

    private Double prixTTC;

    private Long modeRegId;

    private Long clientId;

    private Long tresorId;

    private Long prodId;

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

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
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

    public Long getModeRegId() {
        return modeRegId;
    }

    public void setModeRegId(Long modeReglementId) {
        this.modeRegId = modeReglementId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long intervenantId) {
        this.clientId = intervenantId;
    }

    public Long getTresorId() {
        return tresorId;
    }

    public void setTresorId(Long tresorerieId) {
        this.tresorId = tresorerieId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long produitId) {
        this.prodId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SortieProduitDTO sortieProduitDTO = (SortieProduitDTO) o;
        if(sortieProduitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieProduitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieProduitDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", motif='" + getMotif() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
