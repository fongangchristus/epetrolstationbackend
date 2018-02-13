package com.epetrole.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Intervenant entity.
 */
public class IntervenantDTO implements Serializable {

    private Long id;

    private String imageFile;

    private String nomComplet;

    private String fonction;

    private String cni;

    private String tel;

    private String addresse;

    private String soldeInit;

    private Long entreeProduitId;

    private Long entreeCarburantId;

    private Long managerId;

    private Long typeIntervenantId;

    private Long isId;

    private Long stationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getSoldeInit() {
        return soldeInit;
    }

    public void setSoldeInit(String soldeInit) {
        this.soldeInit = soldeInit;
    }

    public Long getEntreeProduitId() {
        return entreeProduitId;
    }

    public void setEntreeProduitId(Long entreeProduitId) {
        this.entreeProduitId = entreeProduitId;
    }

    public Long getEntreeCarburantId() {
        return entreeCarburantId;
    }

    public void setEntreeCarburantId(Long entreeCarburantId) {
        this.entreeCarburantId = entreeCarburantId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long intervenantId) {
        this.managerId = intervenantId;
    }

    public Long getTypeIntervenantId() {
        return typeIntervenantId;
    }

    public void setTypeIntervenantId(Long typeIntervenantId) {
        this.typeIntervenantId = typeIntervenantId;
    }

    public Long getIsId() {
        return isId;
    }

    public void setIsId(Long userId) {
        this.isId = userId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntervenantDTO intervenantDTO = (IntervenantDTO) o;
        if(intervenantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intervenantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntervenantDTO{" +
            "id=" + getId() +
            ", imageFile='" + getImageFile() + "'" +
            ", nomComplet='" + getNomComplet() + "'" +
            ", fonction='" + getFonction() + "'" +
            ", cni='" + getCni() + "'" +
            ", tel='" + getTel() + "'" +
            ", addresse='" + getAddresse() + "'" +
            ", soldeInit='" + getSoldeInit() + "'" +
            "}";
    }
}
