package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employes")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    private String nom;
    private String prenom;
    private String telephone;
    @OneToMany(mappedBy = "employe", fetch = FetchType.EAGER)
    private List<Projet> projet;
    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefDeProjet;  // L'attribut qui manquait !

    // Relation avec les projets (comme chef)
    @OneToMany(mappedBy = "employe")
    private List<Projet> projetsGeres;
    public Employe() {}

    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public List<Projet> getProjets() {
        return projet;
    }

    public void setProjets(List<Projet> projet) {
        this.projet = projet;
    }

    public Employe getChefDeProjet() {
        return chefDeProjet;
    }

    public void setChefDeProjet(Employe chefDeProjet) {
        this.chefDeProjet = chefDeProjet;
    }

    public List<Projet> getProjetsGeres() {
        return projetsGeres;
    }

    public void setProjetsGeres(List<Projet> projetsGeres) {
        this.projetsGeres = projetsGeres;
    }
}


