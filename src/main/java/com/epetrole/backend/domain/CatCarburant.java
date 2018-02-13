package com.epetrole.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CatCarburant.
 */
@Entity
@Table(name = "cat_carburant")
public class CatCarburant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "catCarburant")
    @JsonIgnore
    private Set<Carburant> have = new HashSet<>();

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

    public CatCarburant libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getDescription() {
        return description;
    }

    public CatCarburant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Carburant> getHave() {
        return have;
    }

    public CatCarburant have(Set<Carburant> carburants) {
        this.have = carburants;
        return this;
    }

    public CatCarburant addHas(Carburant carburant) {
        this.have.add(carburant);
        carburant.setCatCarburant(this);
        return this;
    }

    public CatCarburant removeHas(Carburant carburant) {
        this.have.remove(carburant);
        carburant.setCatCarburant(null);
        return this;
    }

    public void setHave(Set<Carburant> carburants) {
        this.have = carburants;
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
        CatCarburant catCarburant = (CatCarburant) o;
        if (catCarburant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catCarburant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CatCarburant{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
