package model;

public class Categorie extends Entity {

    private int id;
    private String libelle;
    private int chiffreAffaire;

    public Categorie() {
        this(0);
    }

    public Categorie(int id) {
        this(id, null);
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(int id, String libelle) {
        super();
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(int chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(libelle);
        return stringBuilder.toString();
    }
}