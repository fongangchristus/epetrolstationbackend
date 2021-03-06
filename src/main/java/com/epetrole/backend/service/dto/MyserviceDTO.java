package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Myservice entity.
 */
public class MyserviceDTO implements Serializable {

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

        MyserviceDTO myserviceDTO = (MyserviceDTO) o;
        if(myserviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myserviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyserviceDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
