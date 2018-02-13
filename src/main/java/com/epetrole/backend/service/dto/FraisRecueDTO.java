package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FraisRecue entity.
 */
public class FraisRecueDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private Double montant;

    private Long hastrrId;

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

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Long getHastrrId() {
        return hastrrId;
    }

    public void setHastrrId(Long tresorerieId) {
        this.hastrrId = tresorerieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FraisRecueDTO fraisRecueDTO = (FraisRecueDTO) o;
        if(fraisRecueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fraisRecueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FraisRecueDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
