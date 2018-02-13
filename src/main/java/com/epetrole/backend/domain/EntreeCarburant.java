package com.epetrole.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EntreeCarburant.
 */
@Entity
@Table(name = "entree_carburant")
public class EntreeCarburant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "prix_totalht")
    private Double prixTotalht;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "prix_ttc")
    private Double prixTTC;

    @OneToOne
    @JoinColumn(unique = true)
    private ModeReglement modeReglement;

    @OneToOne
    @JoinColumn(unique = true)
    private Carburant carburant;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie tresorerie;

    @OneToMany(mappedBy = "entreeCarburant")
    @JsonIgnore
    private Set<Intervenant> pompistes = new HashSet<>();

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

    public EntreeCarburant date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public EntreeCarburant prixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
        return this;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getQuantite() {
        return quantite;
    }

    public EntreeCarburant quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public EntreeCarburant prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public ModeReglement getModeReglement() {
        return modeReglement;
    }

    public EntreeCarburant modeReglement(ModeReglement modeReglement) {
        this.modeReglement = modeReglement;
        return this;
    }

    public void setModeReglement(ModeReglement modeReglement) {
        this.modeReglement = modeReglement;
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public EntreeCarburant carburant(Carburant carburant) {
        this.carburant = carburant;
        return this;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }

    public Tresorerie getTresorerie() {
        return tresorerie;
    }

    public EntreeCarburant tresorerie(Tresorerie tresorerie) {
        this.tresorerie = tresorerie;
        return this;
    }

    public void setTresorerie(Tresorerie tresorerie) {
        this.tresorerie = tresorerie;
    }

    public Set<Intervenant> getPompistes() {
        return pompistes;
    }

    public EntreeCarburant pompistes(Set<Intervenant> intervenants) {
        this.pompistes = intervenants;
        return this;
    }

    public EntreeCarburant addPompiste(Intervenant intervenant) {
        this.pompistes.add(intervenant);
        intervenant.setEntreeCarburant(this);
        return this;
    }

    public EntreeCarburant removePompiste(Intervenant intervenant) {
        this.pompistes.remove(intervenant);
        intervenant.setEntreeCarburant(null);
        return this;
    }

    public void setPompistes(Set<Intervenant> intervenants) {
        this.pompistes = intervenants;
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
        EntreeCarburant entreeCarburant = (EntreeCarburant) o;
        if (entreeCarburant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeCarburant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeCarburant{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
