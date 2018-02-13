package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SortieCarburant.
 */
@Entity
@Table(name = "sortie_carburant")
public class SortieCarburant implements Serializable {

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

    @Column(name = "quantite_i_nit")
    private Double quantiteINit;

    @Column(name = "quantite_final")
    private Double quantiteFinal;

    @Column(name = "prix_ttc")
    private Double prixTTC;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant inter;

    @OneToOne
    @JoinColumn(unique = true)
    private Carburant carb;

    @OneToOne
    @JoinColumn(unique = true)
    private Pompe pomp;

    @OneToOne
    @JoinColumn(unique = true)
    private ModeReglement mode;

    @OneToOne
    @JoinColumn(unique = true)
    private Tresorerie tres;

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

    public SortieCarburant date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public SortieCarburant prixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
        return this;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getQuantite() {
        return quantite;
    }

    public SortieCarburant quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getQuantiteINit() {
        return quantiteINit;
    }

    public SortieCarburant quantiteINit(Double quantiteINit) {
        this.quantiteINit = quantiteINit;
        return this;
    }

    public void setQuantiteINit(Double quantiteINit) {
        this.quantiteINit = quantiteINit;
    }

    public Double getQuantiteFinal() {
        return quantiteFinal;
    }

    public SortieCarburant quantiteFinal(Double quantiteFinal) {
        this.quantiteFinal = quantiteFinal;
        return this;
    }

    public void setQuantiteFinal(Double quantiteFinal) {
        this.quantiteFinal = quantiteFinal;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public SortieCarburant prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Intervenant getInter() {
        return inter;
    }

    public SortieCarburant inter(Intervenant intervenant) {
        this.inter = intervenant;
        return this;
    }

    public void setInter(Intervenant intervenant) {
        this.inter = intervenant;
    }

    public Carburant getCarb() {
        return carb;
    }

    public SortieCarburant carb(Carburant carburant) {
        this.carb = carburant;
        return this;
    }

    public void setCarb(Carburant carburant) {
        this.carb = carburant;
    }

    public Pompe getPomp() {
        return pomp;
    }

    public SortieCarburant pomp(Pompe pompe) {
        this.pomp = pompe;
        return this;
    }

    public void setPomp(Pompe pompe) {
        this.pomp = pompe;
    }

    public ModeReglement getMode() {
        return mode;
    }

    public SortieCarburant mode(ModeReglement modeReglement) {
        this.mode = modeReglement;
        return this;
    }

    public void setMode(ModeReglement modeReglement) {
        this.mode = modeReglement;
    }

    public Tresorerie getTres() {
        return tres;
    }

    public SortieCarburant tres(Tresorerie tresorerie) {
        this.tres = tresorerie;
        return this;
    }

    public void setTres(Tresorerie tresorerie) {
        this.tres = tresorerie;
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
        SortieCarburant sortieCarburant = (SortieCarburant) o;
        if (sortieCarburant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieCarburant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieCarburant{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", quantite=" + getQuantite() +
            ", quantiteINit=" + getQuantiteINit() +
            ", quantiteFinal=" + getQuantiteFinal() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
