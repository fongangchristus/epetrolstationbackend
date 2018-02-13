package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SortieProduit.
 */
@Entity
@Table(name = "sortie_produit")
public class SortieProduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "motif")
    private String motif;

    @Column(name = "prix_totalht")
    private Double prixTotalht;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "prix_ttc")
    private Double prixTTC;

    @OneToOne
    @JoinColumn(unique = true)
    private ModeReglement modeReg;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant client;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie tresor;

    @OneToOne
    @JoinColumn(unique = true)
    private Produit prod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public SortieProduit date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public SortieProduit motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public SortieProduit prixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
        return this;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getQuantite() {
        return quantite;
    }

    public SortieProduit quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public SortieProduit prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public ModeReglement getModeReg() {
        return modeReg;
    }

    public SortieProduit modeReg(ModeReglement modeReglement) {
        this.modeReg = modeReglement;
        return this;
    }

    public void setModeReg(ModeReglement modeReglement) {
        this.modeReg = modeReglement;
    }

    public Intervenant getClient() {
        return client;
    }

    public SortieProduit client(Intervenant intervenant) {
        this.client = intervenant;
        return this;
    }

    public void setClient(Intervenant intervenant) {
        this.client = intervenant;
    }

    public Tresorerie getTresor() {
        return tresor;
    }

    public SortieProduit tresor(Tresorerie tresorerie) {
        this.tresor = tresorerie;
        return this;
    }

    public void setTresor(Tresorerie tresorerie) {
        this.tresor = tresorerie;
    }

    public Produit getProd() {
        return prod;
    }

    public SortieProduit prod(Produit produit) {
        this.prod = produit;
        return this;
    }

    public void setProd(Produit produit) {
        this.prod = produit;
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
        SortieProduit sortieProduit = (SortieProduit) o;
        if (sortieProduit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieProduit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieProduit{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", motif='" + getMotif() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
