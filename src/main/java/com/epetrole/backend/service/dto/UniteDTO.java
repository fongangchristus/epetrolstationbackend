package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Unite entity.
 */
public class UniteDTO implements Serializable {

    private Long id;

    private String libele;

    private String abreviation;

    private String equivEnLitre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getEquivEnLitre() {
        return equivEnLitre;
    }

    public void setEquivEnLitre(String equivEnLitre) {
        this.equivEnLitre = equivEnLitre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UniteDTO uniteDTO = (UniteDTO) o;
        if(uniteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uniteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UniteDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", equivEnLitre='" + getEquivEnLitre() + "'" +
            "}";
    }
}
