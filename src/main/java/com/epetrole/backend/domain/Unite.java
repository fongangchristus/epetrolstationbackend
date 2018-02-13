package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Unite.
 */
@Entity
@Table(name = "unite")
public class Unite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "abreviation")
    private String abreviation;

    @Column(name = "equiv_en_litre")
    private String equivEnLitre;

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

    public Unite libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Unite abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getEquivEnLitre() {
        return equivEnLitre;
    }

    public Unite equivEnLitre(String equivEnLitre) {
        this.equivEnLitre = equivEnLitre;
        return this;
    }

    public void setEquivEnLitre(String equivEnLitre) {
        this.equivEnLitre = equivEnLitre;
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
        Unite unite = (Unite) o;
        if (unite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Unite{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", equivEnLitre='" + getEquivEnLitre() + "'" +
            "}";
    }
}
