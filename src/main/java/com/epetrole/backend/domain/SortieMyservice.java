package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SortieMyservice.
 */
@Entity
@Table(name = "sortie_myservice")
public class SortieMyservice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "prix_totalht")
    private Double prixTotalht;

    @Column(name = "prix_ttc")
    private Double prixTTC;

    @OneToOne
    @JoinColumn(unique = true)
    private Myservice serv;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant hasinter;

    @OneToOne
    @JoinColumn(unique = true)
    private ModeReglement hasModeR;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie hastresor;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie hastre;

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

    public SortieMyservice date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public SortieMyservice prixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
        return this;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public SortieMyservice prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Myservice getServ() {
        return serv;
    }

    public SortieMyservice serv(Myservice myservice) {
        this.serv = myservice;
        return this;
    }

    public void setServ(Myservice myservice) {
        this.serv = myservice;
    }

    public Intervenant getHasinter() {
        return hasinter;
    }

    public SortieMyservice hasinter(Intervenant intervenant) {
        this.hasinter = intervenant;
        return this;
    }

    public void setHasinter(Intervenant intervenant) {
        this.hasinter = intervenant;
    }

    public ModeReglement getHasModeR() {
        return hasModeR;
    }

    public SortieMyservice hasModeR(ModeReglement modeReglement) {
        this.hasModeR = modeReglement;
        return this;
    }

    public void setHasModeR(ModeReglement modeReglement) {
        this.hasModeR = modeReglement;
    }

    public Tresorerie getHastresor() {
        return hastresor;
    }

    public SortieMyservice hastresor(Tresorerie tresorerie) {
        this.hastresor = tresorerie;
        return this;
    }

    public void setHastresor(Tresorerie tresorerie) {
        this.hastresor = tresorerie;
    }

    public Tresorerie getHastre() {
        return hastre;
    }

    public SortieMyservice hastre(Tresorerie tresorerie) {
        this.hastre = tresorerie;
        return this;
    }

    public void setHastre(Tresorerie tresorerie) {
        this.hastre = tresorerie;
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
        SortieMyservice sortieMyservice = (SortieMyservice) o;
        if (sortieMyservice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieMyservice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieMyservice{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
