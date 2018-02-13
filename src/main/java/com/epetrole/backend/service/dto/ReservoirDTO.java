package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reservoir entity.
 */
public class ReservoirDTO implements Serializable {

    private Long id;

    private String designation;

    private String quantiteInit;

    private String quantiteMax;

    private String quantiteAlerte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQuantiteInit() {
        return quantiteInit;
    }

    public void setQuantiteInit(String quantiteInit) {
        this.quantiteInit = quantiteInit;
    }

    public String getQuantiteMax() {
        return quantiteMax;
    }

    public void setQuantiteMax(String quantiteMax) {
        this.quantiteMax = quantiteMax;
    }

    public String getQuantiteAlerte() {
        return quantiteAlerte;
    }

    public void setQuantiteAlerte(String quantiteAlerte) {
        this.quantiteAlerte = quantiteAlerte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservoirDTO reservoirDTO = (ReservoirDTO) o;
        if(reservoirDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservoirDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReservoirDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", quantiteInit='" + getQuantiteInit() + "'" +
            ", quantiteMax='" + getQuantiteMax() + "'" +
            ", quantiteAlerte='" + getQuantiteAlerte() + "'" +
            "}";
    }
}
