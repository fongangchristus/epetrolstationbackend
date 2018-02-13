package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FraisRecue.
 */
@Entity
@Table(name = "frais_recue")
public class FraisRecue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "montant")
    private Double montant;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie hastrr;

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

    public FraisRecue date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public FraisRecue montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Tresorerie getHastrr() {
        return hastrr;
    }

    public FraisRecue hastrr(Tresorerie tresorerie) {
        this.hastrr = tresorerie;
        return this;
    }

    public void setHastrr(Tresorerie tresorerie) {
        this.hastrr = tresorerie;
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
        FraisRecue fraisRecue = (FraisRecue) o;
        if (fraisRecue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fraisRecue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FraisRecue{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
