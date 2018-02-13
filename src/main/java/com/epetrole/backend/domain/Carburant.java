package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Carburant.
 */
@Entity
@Table(name = "carburant")
public class Carburant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "solde_init")
    private Double soldeInit;

    @Column(name = "prix_achat")
    private Double prixAchat;

    @Column(name = "prix_vente")
    private Double prixVente;

    @Column(name = "reference")
    private Double reference;

    @ManyToOne
    private CatCarburant catCarburant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public Carburant designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSoldeInit() {
        return soldeInit;
    }

    public Carburant soldeInit(Double soldeInit) {
        this.soldeInit = soldeInit;
        return this;
    }

    public void setSoldeInit(Double soldeInit) {
        this.soldeInit = soldeInit;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public Carburant prixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
        return this;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public Carburant prixVente(Double prixVente) {
        this.prixVente = prixVente;
        return this;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Double getReference() {
        return reference;
    }

    public Carburant reference(Double reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Double reference) {
        this.reference = reference;
    }

    public CatCarburant getCatCarburant() {
        return catCarburant;
    }

    public Carburant catCarburant(CatCarburant catCarburant) {
        this.catCarburant = catCarburant;
        return this;
    }

    public void setCatCarburant(CatCarburant catCarburant) {
        this.catCarburant = catCarburant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carburant carburant = (Carburant) o;
        if (carburant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carburant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Carburant{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", soldeInit=" + getSoldeInit() +
            ", prixAchat=" + getPrixAchat() +
            ", prixVente=" + getPrixVente() +
            ", reference=" + getReference() +
            "}";
    }
}
