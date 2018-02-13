package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Carburant entity.
 */
public class CarburantDTO implements Serializable {

    private Long id;

    private String designation;

    private Double soldeInit;

    private Double prixAchat;

    private Double prixVente;

    private Double reference;

    private Long catCarburantId;

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

    public Double getReference() {
        return reference;
    }

    public void setReference(Double reference) {
        this.reference = reference;
    }

    public Long getCatCarburantId() {
        return catCarburantId;
    }

    public void setCatCarburantId(Long catCarburantId) {
        this.catCarburantId = catCarburantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarburantDTO carburantDTO = (CarburantDTO) o;
        if(carburantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carburantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarburantDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", soldeInit=" + getSoldeInit() +
            ", prixAchat=" + getPrixAchat() +
            ", prixVente=" + getPrixVente() +
            ", reference=" + getReference() +
            "}";
    }
}
