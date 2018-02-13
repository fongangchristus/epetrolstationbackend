package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeIntervenant.
 */
@Entity
@Table(name = "type_intervenant")
public class TypeIntervenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "description")
    private String description;

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

    public TypeIntervenant libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getDescription() {
        return description;
    }

    public TypeIntervenant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        TypeIntervenant typeIntervenant = (TypeIntervenant) o;
        if (typeIntervenant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeIntervenant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeIntervenant{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
