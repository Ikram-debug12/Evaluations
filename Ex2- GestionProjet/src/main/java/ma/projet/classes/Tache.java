package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "findTachesPrixSuperieur1000",
        query = "from Tache where prix > 1000")
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private Double prix;
@ManyToOne
    @JoinColumn(name = "projet")
private Projet projet ;

@OneToMany(mappedBy = "tache")
private List<EmployeTache> employeTaches ;

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
