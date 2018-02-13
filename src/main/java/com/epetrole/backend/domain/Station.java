package com.epetrole.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Station.
 */
@Entity
@Table(name = "station")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "fax")
    private String fax;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresse;

    @OneToMany(mappedBy = "station")
    @JsonIgnore
    private Set<Intervenant> intervenants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Station code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDenomination() {
        return denomination;
    }

    public Station denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getTelephone() {
        return telephone;
    }

    public Station telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Station email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public Station fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Station adresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Set<Intervenant> getIntervenants() {
        return intervenants;
    }

    public Station intervenants(Set<Intervenant> intervenants) {
        this.intervenants = intervenants;
        return this;
    }

    public Station addIntervenant(Intervenant intervenant) {
        this.intervenants.add(intervenant);
        intervenant.setStation(this);
        return this;
    }

    public Station removeIntervenant(Intervenant intervenant) {
        this.intervenants.remove(intervenant);
        intervenant.setStation(null);
        return this;
    }

    public void setIntervenants(Set<Intervenant> intervenants) {
        this.intervenants = intervenants;
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
        Station station = (Station) o;
        if (station.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), station.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", fax='" + getFax() + "'" +
            "}";
    }
}
