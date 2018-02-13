package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Depense entity.
 */
public class DepenseDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private String code;

    private String designation;

    private String description;

    private Double montant;

    private Long hastresroId;

    private Long hasintervId;

    private Long useId;

    private Long hasId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Long getHastresroId() {
        return hastresroId;
    }

    public void setHastresroId(Long tresorerieId) {
        this.hastresroId = tresorerieId;
    }

    public Long getHasintervId() {
        return hasintervId;
    }

    public void setHasintervId(Long intervenantId) {
        this.hasintervId = intervenantId;
    }

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long categorieDepenseId) {
        this.useId = categorieDepenseId;
    }

    public Long getHasId() {
        return hasId;
    }

    public void setHasId(Long modeReglementId) {
        this.hasId = modeReglementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepenseDTO depenseDTO = (DepenseDTO) o;
        if(depenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepenseDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", code='" + getCode() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
