package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pompe.
 */
@Entity
@Table(name = "pompe")
public class Pompe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nb_chiffre")
    private String nbChiffre;

    @Column(name = "c_init")
    private String cInit;

    @Column(name = "melange")
    private Boolean melange;

    @OneToOne
    @JoinColumn(unique = true)
    private Citerne hasci;

    @OneToOne
    @JoinColumn(unique = true)
    private Reservoir hasre;

    @OneToOne
    @JoinColumn(unique = true)
    private TauxMelange hasta;

    @OneToOne
    @JoinColumn(unique = true)
    private CatCarburant hasca;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbChiffre() {
        return nbChiffre;
    }

    public Pompe nbChiffre(String nbChiffre) {
        this.nbChiffre = nbChiffre;
        return this;
    }

    public void setNbChiffre(String nbChiffre) {
        this.nbChiffre = nbChiffre;
    }

    public String getcInit() {
        return cInit;
    }

    public Pompe cInit(String cInit) {
        this.cInit = cInit;
        return this;
    }

    public void setcInit(String cInit) {
        this.cInit = cInit;
    }

    public Boolean isMelange() {
        return melange;
    }

    public Pompe melange(Boolean melange) {
        this.melange = melange;
        return this;
    }

    public void setMelange(Boolean melange) {
        this.melange = melange;
    }

    public Citerne getHasci() {
        return hasci;
    }

    public Pompe hasci(Citerne citerne) {
        this.hasci = citerne;
        return this;
    }

    public void setHasci(Citerne citerne) {
        this.hasci = citerne;
    }

    public Reservoir getHasre() {
        return hasre;
    }

    public Pompe hasre(Reservoir reservoir) {
        this.hasre = reservoir;
        return this;
    }

    public void setHasre(Reservoir reservoir) {
        this.hasre = reservoir;
    }

    public TauxMelange getHasta() {
        return hasta;
    }

    public Pompe hasta(TauxMelange tauxMelange) {
        this.hasta = tauxMelange;
        return this;
    }

    public void setHasta(TauxMelange tauxMelange) {
        this.hasta = tauxMelange;
    }

    public CatCarburant getHasca() {
        return hasca;
    }

    public Pompe hasca(CatCarburant catCarburant) {
        this.hasca = catCarburant;
        return this;
    }

    public void setHasca(CatCarburant catCarburant) {
        this.hasca = catCarburant;
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
        Pompe pompe = (Pompe) o;
        if (pompe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pompe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pompe{" +
            "id=" + getId() +
            ", nbChiffre='" + getNbChiffre() + "'" +
            ", cInit='" + getcInit() + "'" +
            ", melange='" + isMelange() + "'" +
            "}";
    }
}
