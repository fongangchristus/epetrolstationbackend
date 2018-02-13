package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

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

    @Column(name = "quantite_dispo")
    private Double quantiteDispo;

    @Column(name = "quantite_init")
    private Double quantiteInit;

    @Column(name = "seuil_reaprov")
    private Double seuilReaprov;

    @Column(name = "reference")
    private Double reference;

    @OneToOne
    @JoinColumn(unique = true)
    private Tva tva;

    @ManyToOne
    private Categorie categorie;

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

    public Produit designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSoldeInit() {
        return soldeInit;
    }

    public Produit soldeInit(Double soldeInit) {
        this.soldeInit = soldeInit;
        return this;
    }

    public void setSoldeInit(Double soldeInit) {
        this.soldeInit = soldeInit;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public Produit prixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
        return this;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public Produit prixVente(Double prixVente) {
        this.prixVente = prixVente;
        return this;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Double getQuantiteDispo() {
        return quantiteDispo;
    }

    public Produit quantiteDispo(Double quantiteDispo) {
        this.quantiteDispo = quantiteDispo;
        return this;
    }

    public void setQuantiteDispo(Double quantiteDispo) {
        this.quantiteDispo = quantiteDispo;
    }

    public Double getQuantiteInit() {
        return quantiteInit;
    }

    public Produit quantiteInit(Double quantiteInit) {
        this.quantiteInit = quantiteInit;
        return this;
    }

    public void setQuantiteInit(Double quantiteInit) {
        this.quantiteInit = quantiteInit;
    }

    public Double getSeuilReaprov() {
        return seuilReaprov;
    }

    public Produit seuilReaprov(Double seuilReaprov) {
        this.seuilReaprov = seuilReaprov;
        return this;
    }

    public void setSeuilReaprov(Double seuilReaprov) {
        this.seuilReaprov = seuilReaprov;
    }

    public Double getReference() {
        return reference;
    }

    public Produit reference(Double reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Double reference) {
        this.reference = reference;
    }

    public Tva getTva() {
        return tva;
    }

    public Produit tva(Tva tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Produit categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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
        Produit produit = (Produit) o;
        if (produit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produit{" +
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
