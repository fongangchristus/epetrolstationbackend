package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reservoir.
 */
@Entity
@Table(name = "reservoir")
public class Reservoir implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "quantite_init")
    private String quantiteInit;

    @Column(name = "quantite_max")
    private String quantiteMax;

    @Column(name = "quantite_alerte")
    private String quantiteAlerte;

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

    public Reservoir designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQuantiteInit() {
        return quantiteInit;
    }

    public Reservoir quantiteInit(String quantiteInit) {
        this.quantiteInit = quantiteInit;
        return this;
    }

    public void setQuantiteInit(String quantiteInit) {
        this.quantiteInit = quantiteInit;
    }

    public String getQuantiteMax() {
        return quantiteMax;
    }

    public Reservoir quantiteMax(String quantiteMax) {
        this.quantiteMax = quantiteMax;
        return this;
    }

    public void setQuantiteMax(String quantiteMax) {
        this.quantiteMax = quantiteMax;
    }

    public String getQuantiteAlerte() {
        return quantiteAlerte;
    }

    public Reservoir quantiteAlerte(String quantiteAlerte) {
        this.quantiteAlerte = quantiteAlerte;
        return this;
    }

    public void setQuantiteAlerte(String quantiteAlerte) {
        this.quantiteAlerte = quantiteAlerte;
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
        Reservoir reservoir = (Reservoir) o;
        if (reservoir.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservoir.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservoir{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", quantiteInit='" + getQuantiteInit() + "'" +
            ", quantiteMax='" + getQuantiteMax() + "'" +
            ", quantiteAlerte='" + getQuantiteAlerte() + "'" +
            "}";
    }
}
