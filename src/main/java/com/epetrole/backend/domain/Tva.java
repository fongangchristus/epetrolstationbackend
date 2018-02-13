package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tva.
 */
@Entity
@Table(name = "tva")
public class Tva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mode_tva")
    private String modeTva;

    @Column(name = "taux_tva")
    private String tauxTva;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModeTva() {
        return modeTva;
    }

    public Tva modeTva(String modeTva) {
        this.modeTva = modeTva;
        return this;
    }

    public void setModeTva(String modeTva) {
        this.modeTva = modeTva;
    }

    public String getTauxTva() {
        return tauxTva;
    }

    public Tva tauxTva(String tauxTva) {
        this.tauxTva = tauxTva;
        return this;
    }

    public void setTauxTva(String tauxTva) {
        this.tauxTva = tauxTva;
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
        Tva tva = (Tva) o;
        if (tva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tva{" +
            "id=" + getId() +
            ", modeTva='" + getModeTva() + "'" +
            ", tauxTva='" + getTauxTva() + "'" +
            "}";
    }
}
