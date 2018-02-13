package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CatCarburant entity.
 */
public class CatCarburantDTO implements Serializable {

    private Long id;

    private String libele;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CatCarburantDTO catCarburantDTO = (CatCarburantDTO) o;
        if(catCarburantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catCarburantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CatCarburantDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
