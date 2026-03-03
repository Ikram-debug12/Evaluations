package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = "findByEmploye", query = "from Projet Where employe =: employe")
})

@Entity
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date DateDebut;
    @Temporal(TemporalType.DATE)
    private Date DateFin ;
    @ManyToOne
    private Employe employe;

    public Projet() {
    }

    public Projet(String nom, Date dateDebut, Date dateFin, Employe employe) {
        this.nom = nom;
        this.DateDebut = dateDebut;
        this.DateFin = dateFin;
        this.employe = employe;
    }

    //Getters et Setters
    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
