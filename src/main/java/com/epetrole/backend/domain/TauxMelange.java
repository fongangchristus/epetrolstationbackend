package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TauxMelange.
 */
@Entity
@Table(name = "taux_melange")
public class TauxMelange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taux_melange")
    private Double tauxMelange;

    @Column(name = "prix_melange")
    private Double prixMelange;

    @Column(name = "taux_en_cours")
    private Boolean tauxEnCours;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTauxMelange() {
        return tauxMelange;
    }

    public TauxMelange tauxMelange(Double tauxMelange) {
        this.tauxMelange = tauxMelange;
        return this;
    }

    public void setTauxMelange(Double tauxMelange) {
        this.tauxMelange = tauxMelange;
    }

    public Double getPrixMelange() {
        return prixMelange;
    }

    public TauxMelange prixMelange(Double prixMelange) {
        this.prixMelange = prixMelange;
        return this;
    }

    public void setPrixMelange(Double prixMelange) {
        this.prixMelange = prixMelange;
    }

    public Boolean isTauxEnCours() {
        return tauxEnCours;
    }

    public TauxMelange tauxEnCours(Boolean tauxEnCours) {
        this.tauxEnCours = tauxEnCours;
        return this;
    }

    public void setTauxEnCours(Boolean tauxEnCours) {
        this.tauxEnCours = tauxEnCours;
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
        TauxMelange tauxMelange = (TauxMelange) o;
        if (tauxMelange.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tauxMelange.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TauxMelange{" +
            "id=" + getId() +
            ", tauxMelange=" + getTauxMelange() +
            ", prixMelange=" + getPrixMelange() +
            ", tauxEnCours='" + isTauxEnCours() + "'" +
            "}";
    }
}
