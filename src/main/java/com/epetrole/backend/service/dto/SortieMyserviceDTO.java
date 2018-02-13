package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SortieMyservice entity.
 */
public class SortieMyserviceDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private Double prixTotalht;

    private Double prixTTC;

    private Long servId;

    private Long hasinterId;

    private Long hasModeRId;

    private Long hastresorId;

    private Long hastreId;

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

    public Double getPrixTotalht() {
        return prixTotalht;
    }

    public void setPrixTotalht(Double prixTotalht) {
        this.prixTotalht = prixTotalht;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Long getServId() {
        return servId;
    }

    public void setServId(Long myserviceId) {
        this.servId = myserviceId;
    }

    public Long getHasinterId() {
        return hasinterId;
    }

    public void setHasinterId(Long intervenantId) {
        this.hasinterId = intervenantId;
    }

    public Long getHasModeRId() {
        return hasModeRId;
    }

    public void setHasModeRId(Long modeReglementId) {
        this.hasModeRId = modeReglementId;
    }

    public Long getHastresorId() {
        return hastresorId;
    }

    public void setHastresorId(Long tresorerieId) {
        this.hastresorId = tresorerieId;
    }

    public Long getHastreId() {
        return hastreId;
    }

    public void setHastreId(Long tresorerieId) {
        this.hastreId = tresorerieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SortieMyserviceDTO sortieMyserviceDTO = (SortieMyserviceDTO) o;
        if(sortieMyserviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sortieMyserviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SortieMyserviceDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", prixTotalht=" + getPrixTotalht() +
            ", prixTTC=" + getPrixTTC() +
            "}";
    }
}
