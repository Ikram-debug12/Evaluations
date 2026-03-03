package ma.projet.classes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id;  // La clé composite

    @ManyToOne
    @MapsId("employeId")  // Lie la partie employeId de l'EmbeddedId
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")    // Lie la partie tacheId de l'EmbeddedId
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut_reelle")
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin_reelle")
    private Date dateFinReelle;

    // Constructeur par défaut
    public EmployeTache() {}

    // Constructeur avec paramètres
    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.id = new EmployeTachePK(employe.getId(), tache.getId());
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    // Getters et Setters
    public EmployeTachePK getId() {
        return id;
    }

    public void setId(EmployeTachePK id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
        // Mettre à jour l'ID si nécessaire
        if (this.id == null) {
            this.id = new EmployeTachePK();
        }
        this.id.setEmployeId(employe.getId());
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
        // Mettre à jour l'ID si nécessaire
        if (this.id == null) {
            this.id = new EmployeTachePK();
        }
        this.id.setTacheId(tache.getId());
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
}

