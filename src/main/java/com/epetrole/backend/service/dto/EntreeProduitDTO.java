package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EntreeProduit entity.
 */
public class EntreeProduitDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private String motif;

    private Double prixTotalht;

    private Double quantite;

    private Double prixTTC;

    private Long modeRId;

    private Long tresorerieId;

    private Long produitId;

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

    public Long getModeRId() {
        return modeRId;
    }

    public void setModeRId(Long modeReglementId) {
        this.modeRId = modeReglementId;
    }

    public Long getTresorerieId() {
        return tresorerieId;
    }

    public void setTresorerieId(Long tresorerieId) {
        this.tresorerieId = tresorerieId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntreeProduitDTO entreeProduitDTO = (EntreeProduitDTO) o;
        if(entreeProduitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeProduitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeProduitDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", motif='" + getMotif() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
