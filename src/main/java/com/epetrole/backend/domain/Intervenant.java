package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Intervenant.
 */
@Entity
@Table(name = "intervenant")
public class Intervenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_file")
    private String imageFile;

    @Column(name = "nom_complet")
    private String nomComplet;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "cni")
    private String cni;

    @Column(name = "tel")
    private String tel;

    @Column(name = "addresse")
    private String addresse;

    @Column(name = "solde_init")
    private String soldeInit;

    @ManyToOne
    private EntreeProduit entreeProduit;

    @ManyToOne
    private EntreeCarburant entreeCarburant;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant manager;

    @OneToOne
    @JoinColumn(unique = true)
    private TypeIntervenant typeIntervenant;

    @OneToOne
    @JoinColumn(unique = true)
    private User is;

    @ManyToOne
    private Station station;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Intervenant imageFile(String imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public Intervenant nomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
        return this;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getFonction() {
        return fonction;
    }

    public Intervenant fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getCni() {
        return cni;
    }

    public Intervenant cni(String cni) {
        this.cni = cni;
        return this;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTel() {
        return tel;
    }

    public Intervenant tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddresse() {
        return addresse;
    }

    public Intervenant addresse(String addresse) {
        this.addresse = addresse;
        return this;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getSoldeInit() {
        return soldeInit;
    }

    public Intervenant soldeInit(String soldeInit) {
        this.soldeInit = soldeInit;
        return this;
    }

    public void setSoldeInit(String soldeInit) {
        this.soldeInit = soldeInit;
    }

    public EntreeProduit getEntreeProduit() {
        return entreeProduit;
    }

    public Intervenant entreeProduit(EntreeProduit entreeProduit) {
        this.entreeProduit = entreeProduit;
        return this;
    }

    public void setEntreeProduit(EntreeProduit entreeProduit) {
        this.entreeProduit = entreeProduit;
    }

    public EntreeCarburant getEntreeCarburant() {
        return entreeCarburant;
    }

    public Intervenant entreeCarburant(EntreeCarburant entreeCarburant) {
        this.entreeCarburant = entreeCarburant;
        return this;
    }

    public void setEntreeCarburant(EntreeCarburant entreeCarburant) {
        this.entreeCarburant = entreeCarburant;
    }

    public Intervenant getManager() {
        return manager;
    }

    public Intervenant manager(Intervenant intervenant) {
        this.manager = intervenant;
        return this;
    }

    public void setManager(Intervenant intervenant) {
        this.manager = intervenant;
    }

    public TypeIntervenant getTypeIntervenant() {
        return typeIntervenant;
    }

    public Intervenant typeIntervenant(TypeIntervenant typeIntervenant) {
        this.typeIntervenant = typeIntervenant;
        return this;
    }

    public void setTypeIntervenant(TypeIntervenant typeIntervenant) {
        this.typeIntervenant = typeIntervenant;
    }

    public User getIs() {
        return is;
    }

    public Intervenant is(User user) {
        this.is = user;
        return this;
    }

    public void setIs(User user) {
        this.is = user;
    }

    public Station getStation() {
        return station;
    }

    public Intervenant station(Station station) {
        this.station = station;
        return this;
    }

    public void setStation(Station station) {
        this.station = station;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Intervenant intervenant = (Intervenant) o;
        if (intervenant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intervenant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Intervenant{" +
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
