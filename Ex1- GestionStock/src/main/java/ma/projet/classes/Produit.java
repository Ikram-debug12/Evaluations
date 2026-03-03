package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "produit.prixSuperieur100",
        query = "from Produit where prix > 100")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> ligneCommandes;

    public Produit() {}

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommandeProduit> getLigneCommandes() { return ligneCommandes; }
    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}
