package tn.agil.miniprojet.agil;

/**
 * Created by Linda on 14/11/2017.
 */

public class ProduitPromo {
    private String nom ;
    private String qte ;
    private int prix ;
    private String dateDeb ;
    private String dateF;

    public ProduitPromo(String nom, String qte, int prix, String dateDeb, String dateF) {
        this.nom = nom;
        this.qte = qte;
        this.prix = prix;
        this.dateDeb = dateDeb;
        this.dateF = dateF;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    @Override
    public String toString() {
        return "ProduitPromo{" +
                "nom='" + nom + '\'' +
                ", qte='" + qte + '\'' +
                ", prix=" + prix +
                ", dateDeb=" + dateDeb +
                ", dateF=" + dateF +
                '}';
    }
}
