package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tresorerie.
 */
@Entity
@Table(name = "tresorerie")
public class Tresorerie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "code_rib")
    private String codeRib;

    @Column(name = "solde_reserve")
    private String soldeReserve;

    @Column(name = "solde_ouverture")
    private String soldeOuverture;

    @Column(name = "chiffre_comptable")
    private String chiffreComptable;

    @OneToOne
    @JoinColumn(unique = true)
    private TypeTresorerie hastypet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public Tresorerie libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getCodeRib() {
        return codeRib;
    }

    public Tresorerie codeRib(String codeRib) {
        this.codeRib = codeRib;
        return this;
    }

    public void setCodeRib(String codeRib) {
        this.codeRib = codeRib;
    }

    public String getSoldeReserve() {
        return soldeReserve;
    }

    public Tresorerie soldeReserve(String soldeReserve) {
        this.soldeReserve = soldeReserve;
        return this;
    }

    public void setSoldeReserve(String soldeReserve) {
        this.soldeReserve = soldeReserve;
    }

    public String getSoldeOuverture() {
        return soldeOuverture;
    }

    public Tresorerie soldeOuverture(String soldeOuverture) {
        this.soldeOuverture = soldeOuverture;
        return this;
    }

    public void setSoldeOuverture(String soldeOuverture) {
        this.soldeOuverture = soldeOuverture;
    }

    public String getChiffreComptable() {
        return chiffreComptable;
    }

    public Tresorerie chiffreComptable(String chiffreComptable) {
        this.chiffreComptable = chiffreComptable;
        return this;
    }

    public void setChiffreComptable(String chiffreComptable) {
        this.chiffreComptable = chiffreComptable;
    }

    public TypeTresorerie getHastypet() {
        return hastypet;
    }

    public Tresorerie hastypet(TypeTresorerie typeTresorerie) {
        this.hastypet = typeTresorerie;
        return this;
    }

    public void setHastypet(TypeTresorerie typeTresorerie) {
        this.hastypet = typeTresorerie;
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
        Tresorerie tresorerie = (Tresorerie) o;
        if (tresorerie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tresorerie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tresorerie{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", codeRib='" + getCodeRib() + "'" +
            ", soldeReserve='" + getSoldeReserve() + "'" +
            ", soldeOuverture='" + getSoldeOuverture() + "'" +
            ", chiffreComptable='" + getChiffreComptable() + "'" +
            "}";
    }
}
