package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tresorerie entity.
 */
public class TresorerieDTO implements Serializable {

    private Long id;

    private String libele;

    private String codeRib;

    private String soldeReserve;

    private String soldeOuverture;

    private String chiffreComptable;

    private Long hastypetId;

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

    public String getCodeRib() {
        return codeRib;
    }

    public void setCodeRib(String codeRib) {
        this.codeRib = codeRib;
    }

    public String getSoldeReserve() {
        return soldeReserve;
    }

    public void setSoldeReserve(String soldeReserve) {
        this.soldeReserve = soldeReserve;
    }

    public String getSoldeOuverture() {
        return soldeOuverture;
    }

    public void setSoldeOuverture(String soldeOuverture) {
        this.soldeOuverture = soldeOuverture;
    }

    public String getChiffreComptable() {
        return chiffreComptable;
    }

    public void setChiffreComptable(String chiffreComptable) {
        this.chiffreComptable = chiffreComptable;
    }

    public Long getHastypetId() {
        return hastypetId;
    }

    public void setHastypetId(Long typeTresorerieId) {
        this.hastypetId = typeTresorerieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TresorerieDTO tresorerieDTO = (TresorerieDTO) o;
        if(tresorerieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tresorerieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TresorerieDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", codeRib='" + getCodeRib() + "'" +
            ", soldeReserve='" + getSoldeReserve() + "'" +
            ", soldeOuverture='" + getSoldeOuverture() + "'" +
            ", chiffreComptable='" + getChiffreComptable() + "'" +
            "}";
    }
}
