package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pompe entity.
 */
public class PompeDTO implements Serializable {

    private Long id;

    private String nbChiffre;

    private String cInit;

    private Boolean melange;

    private Long hasciId;

    private Long hasreId;

    private Long hastaId;

    private Long hascaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbChiffre() {
        return nbChiffre;
    }

    public void setNbChiffre(String nbChiffre) {
        this.nbChiffre = nbChiffre;
    }

    public String getcInit() {
        return cInit;
    }

    public void setcInit(String cInit) {
        this.cInit = cInit;
    }

    public Boolean isMelange() {
        return melange;
    }

    public void setMelange(Boolean melange) {
        this.melange = melange;
    }

    public Long getHasciId() {
        return hasciId;
    }

    public void setHasciId(Long citerneId) {
        this.hasciId = citerneId;
    }

    public Long getHasreId() {
        return hasreId;
    }

    public void setHasreId(Long reservoirId) {
        this.hasreId = reservoirId;
    }

    public Long getHastaId() {
        return hastaId;
    }

    public void setHastaId(Long tauxMelangeId) {
        this.hastaId = tauxMelangeId;
    }

    public Long getHascaId() {
        return hascaId;
    }

    public void setHascaId(Long catCarburantId) {
        this.hascaId = catCarburantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PompeDTO pompeDTO = (PompeDTO) o;
        if(pompeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pompeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PompeDTO{" +
            "id=" + getId() +
            ", nbChiffre='" + getNbChiffre() + "'" +
            ", cInit='" + getcInit() + "'" +
            ", melange='" + isMelange() + "'" +
            "}";
    }
}
