package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Produit entity.
 */
public class ProduitDTO implements Serializable {

    private Long id;

    private String designation;

    private Double soldeInit;

    private Double prixAchat;

    private Double prixVente;

    private Double quantiteDispo;

    private Double quantiteInit;

    private Double seuilReaprov;

    private Double reference;

    private Long tvaId;

    private Long categorieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSoldeInit() {
        return soldeInit;
    }

    public void setSoldeInit(Double soldeInit) {
        this.soldeInit = soldeInit;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Double getQuantiteDispo() {
        return quantiteDispo;
    }

    public void setQuantiteDispo(Double quantiteDispo) {
        this.quantiteDispo = quantiteDispo;
    }

    public Double getQuantiteInit() {
        return quantiteInit;
    }

    public void setQuantiteInit(Double quantiteInit) {
        this.quantiteInit = quantiteInit;
    }

    public Double getSeuilReaprov() {
        return seuilReaprov;
    }

    public void setSeuilReaprov(Double seuilReaprov) {
        this.seuilReaprov = seuilReaprov;
    }

    public Double getReference() {
        return reference;
    }

    public void setReference(Double reference) {
        this.reference = reference;
    }

    public Long getTvaId() {
        return tvaId;
    }

    public void setTvaId(Long tvaId) {
        this.tvaId = tvaId;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if(produitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", soldeInit=" + getSoldeInit() +
            ", prixAchat=" + getPrixAchat() +
            ", prixVente=" + getPrixVente() +
            ", quantiteDispo=" + getQuantiteDispo() +
            ", quantiteInit=" + getQuantiteInit() +
            ", seuilReaprov=" + getSeuilReaprov() +
            ", reference=" + getReference() +
            "}";
    }
}
