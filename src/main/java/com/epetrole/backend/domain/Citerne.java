package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Citerne.
 */
@Entity
@Table(name = "citerne")
public class Citerne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "volume_init")
    private String volumeInit;

    @Column(name = "volume_max")
    private String volumeMax;

    @OneToOne
    @JoinColumn(unique = true)
    private CatCarburant hasc;

    @OneToOne
    @JoinColumn(unique = true)
    private Unite hasu;

    @OneToOne
    @JoinColumn(unique = true)
    private Station hai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVolumeInit() {
        return volumeInit;
    }

    public Citerne volumeInit(String volumeInit) {
        this.volumeInit = volumeInit;
        return this;
    }

    public void setVolumeInit(String volumeInit) {
        this.volumeInit = volumeInit;
    }

    public String getVolumeMax() {
        return volumeMax;
    }

    public Citerne volumeMax(String volumeMax) {
        this.volumeMax = volumeMax;
        return this;
    }

    public void setVolumeMax(String volumeMax) {
        this.volumeMax = volumeMax;
    }

    public CatCarburant getHasc() {
        return hasc;
    }

    public Citerne hasc(CatCarburant catCarburant) {
        this.hasc = catCarburant;
        return this;
    }

    public void setHasc(CatCarburant catCarburant) {
        this.hasc = catCarburant;
    }

    public Unite getHasu() {
        return hasu;
    }

    public Citerne hasu(Unite unite) {
        this.hasu = unite;
        return this;
    }

    public void setHasu(Unite unite) {
        this.hasu = unite;
    }

    public Station getHai() {
        return hai;
    }

    public Citerne hai(Station station) {
        this.hai = station;
        return this;
    }

    public void setHai(Station station) {
        this.hai = station;
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
        Citerne citerne = (Citerne) o;
        if (citerne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), citerne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Citerne{" +
            "id=" + getId() +
            ", volumeInit='" + getVolumeInit() + "'" +
            ", volumeMax='" + getVolumeMax() + "'" +
            "}";
    }
}
