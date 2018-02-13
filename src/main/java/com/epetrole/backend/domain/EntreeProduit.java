package com.epetrole.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EntreeProduit.
 */
@Entity
@Table(name = "entree_produit")
public class EntreeProduit implements Serializable {

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
    private ModeReglement modeR;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie tresorerie;

    @OneToOne
    @JoinColumn(unique = true)
    private Produit produit;

    @OneToMany(mappedBy = "entreeProduit")
    @JsonIgnore
    private Set<Intervenant> commercials = new HashSet<>();

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

    public EntreeProduit date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public EntreeProduit motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public EntreeProduit prixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
        return this;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getQuantite() {
        return quantite;
    }

    public EntreeProduit quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public EntreeProduit prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public ModeReglement getModeR() {
        return modeR;
    }

    public EntreeProduit modeR(ModeReglement modeReglement) {
        this.modeR = modeReglement;
        return this;
    }

    public void setModeR(ModeReglement modeReglement) {
        this.modeR = modeReglement;
    }

    public Tresorerie getTresorerie() {
        return tresorerie;
    }

    public EntreeProduit tresorerie(Tresorerie tresorerie) {
        this.tresorerie = tresorerie;
        return this;
    }

    public void setTresorerie(Tresorerie tresorerie) {
        this.tresorerie = tresorerie;
    }

    public Produit getProduit() {
        return produit;
    }

    public EntreeProduit produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Set<Intervenant> getCommercials() {
        return commercials;
    }

    public EntreeProduit commercials(Set<Intervenant> intervenants) {
        this.commercials = intervenants;
        return this;
    }

    public EntreeProduit addCommercial(Intervenant intervenant) {
        this.commercials.add(intervenant);
        intervenant.setEntreeProduit(this);
        return this;
    }

    public EntreeProduit removeCommercial(Intervenant intervenant) {
        this.commercials.remove(intervenant);
        intervenant.setEntreeProduit(null);
        return this;
    }

    public void setCommercials(Set<Intervenant> intervenants) {
        this.commercials = intervenants;
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
        EntreeProduit entreeProduit = (EntreeProduit) o;
        if (entreeProduit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeProduit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeProduit{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", motif='" + getMotif() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
