package com.epetrole.backend.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EntreeCiterne entity.
 */
public class EntreeCiterneDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private String valeurMax;

    private String valeurActuel;

    private String quantite;

    private Long hasciterneId;

    private Long hasuniteId;

    private Long hasiId;

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

    public String getValeurMax() {
        return valeurMax;
    }

    public void setValeurMax(String valeurMax) {
        this.valeurMax = valeurMax;
    }

    public String getValeurActuel() {
        return valeurActuel;
    }

    public void setValeurActuel(String valeurActuel) {
        this.valeurActuel = valeurActuel;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public Long getHasciterneId() {
        return hasciterneId;
    }

    public void setHasciterneId(Long citerneId) {
        this.hasciterneId = citerneId;
    }

    public Long getHasuniteId() {
        return hasuniteId;
    }

    public void setHasuniteId(Long uniteId) {
        this.hasuniteId = uniteId;
    }

    public Long getHasiId() {
        return hasiId;
    }

    public void setHasiId(Long intervenantId) {
        this.hasiId = intervenantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntreeCiterneDTO entreeCiterneDTO = (EntreeCiterneDTO) o;
        if(entreeCiterneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeCiterneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeCiterneDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", valeurMax='" + getValeurMax() + "'" +
            ", valeurActuel='" + getValeurActuel() + "'" +
            ", quantite='" + getQuantite() + "'" +
            "}";
    }
}
