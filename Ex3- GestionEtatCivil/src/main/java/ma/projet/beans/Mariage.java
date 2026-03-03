package ma.projet.beans;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Mariage implements Serializable {

    @EmbeddedId
    private MariagePK id;

    @ManyToOne
    @MapsId("hommeId")
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @MapsId("femmeId")
    @JoinColumn(name = "femme_id")
    private Femme femme;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin; // null si toujours en cours

    @Column(name = "nombreEnfants")
    private int nombreEnfants;

    public Mariage() {}

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nombreEnfants) {
        this.id = new MariagePK(homme.getId(), femme.getId());
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreEnfants = nombreEnfants;
    }

    // Getters et Setters
    public MariagePK getId() {
        return id;
    }

    public void setId(MariagePK id) {
        this.id = id;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
        if (id == null) {
            this.id = new MariagePK();
        }
        this.id.setHommeId(homme.getId());
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
        if (id == null) {
            this.id = new MariagePK();
        }
        this.id.setFemmeId(femme.getId());
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }
}