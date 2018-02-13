package com.epetrole.backend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A EntreeCiterne.
 */
@Entity
@Table(name = "entree_citerne")
public class EntreeCiterne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "valeur_max")
    private String valeurMax;

    @Column(name = "valeur_actuel")
    private String valeurActuel;

    @Column(name = "quantite")
    private String quantite;

    @OneToOne
    @JoinColumn(unique = true)
    private Citerne hasciterne;

    @OneToOne
    @JoinColumn(unique = true)
    private Unite hasunite;

    @OneToOne
    @JoinColumn(unique = true)
    private Intervenant hasi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public EntreeCiterne date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getValeurMax() {
        return valeurMax;
    }

    public EntreeCiterne valeurMax(String valeurMax) {
        this.valeurMax = valeurMax;
        return this;
    }

    public void setValeurMax(String valeurMax) {
        this.valeurMax = valeurMax;
    }

    public String getValeurActuel() {
        return valeurActuel;
    }

    public EntreeCiterne valeurActuel(String valeurActuel) {
        this.valeurActuel = valeurActuel;
        return this;
    }

    public void setValeurActuel(String valeurActuel) {
        this.valeurActuel = valeurActuel;
    }

    public String getQuantite() {
        return quantite;
    }

    public EntreeCiterne quantite(String quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public Citerne getHasciterne() {
        return hasciterne;
    }

    public EntreeCiterne hasciterne(Citerne citerne) {
        this.hasciterne = citerne;
        return this;
    }

    public void setHasciterne(Citerne citerne) {
        this.hasciterne = citerne;
    }

    public Unite getHasunite() {
        return hasunite;
    }

    public EntreeCiterne hasunite(Unite unite) {
        this.hasunite = unite;
        return this;
    }

    public void setHasunite(Unite unite) {
        this.hasunite = unite;
    }

    public Intervenant getHasi() {
        return hasi;
    }

    public EntreeCiterne hasi(Intervenant intervenant) {
        this.hasi = intervenant;
        return this;
    }

    public void setHasi(Intervenant intervenant) {
        this.hasi = intervenant;
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
        EntreeCiterne entreeCiterne = (EntreeCiterne) o;
        if (entreeCiterne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreeCiterne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntreeCiterne{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", valeurMax='" + getValeurMax() + "'" +
            ", valeurActuel='" + getValeurActuel() + "'" +
            ", quantite='" + getQuantite() + "'" +
            "}";
    }
}
