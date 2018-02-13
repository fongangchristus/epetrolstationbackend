package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Depense.
 */
@Entity
@Table(name = "depense")
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "code")
    private String code;

    @Column(name = "designation")
    private String designation;

    @Column(name = "description")
    private String description;

    @Column(name = "montant")
    private Double montant;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie hastresro;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant hasinterv;

    @OneToOne
    @JoinColumn(unique = true)
    private CategorieDepense use;

    @OneToOne
    @JoinColumn(unique = true)
    private ModeReglement has;

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

    public Depense date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public Depense code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public Depense designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public Depense description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontant() {
        return montant;
    }

    public Depense montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Tresorerie getHastresro() {
        return hastresro;
    }

    public Depense hastresro(Tresorerie tresorerie) {
        this.hastresro = tresorerie;
        return this;
    }

    public void setHastresro(Tresorerie tresorerie) {
        this.hastresro = tresorerie;
    }

    public Intervenant getHasinterv() {
        return hasinterv;
    }

    public Depense hasinterv(Intervenant intervenant) {
        this.hasinterv = intervenant;
        return this;
    }

    public void setHasinterv(Intervenant intervenant) {
        this.hasinterv = intervenant;
    }

    public CategorieDepense getUse() {
        return use;
    }

    public Depense use(CategorieDepense categorieDepense) {
        this.use = categorieDepense;
        return this;
    }

    public void setUse(CategorieDepense categorieDepense) {
        this.use = categorieDepense;
    }

    public ModeReglement getHas() {
        return has;
    }

    public Depense has(ModeReglement modeReglement) {
        this.has = modeReglement;
        return this;
    }

    public void setHas(ModeReglement modeReglement) {
        this.has = modeReglement;
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
        Depense depense = (Depense) o;
        if (depense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Depense{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", code='" + getCode() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
