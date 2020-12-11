package model;

public class Marque extends Entity {

    private int id;
    private String nom;
    private int nbVehicule;

    public Marque() {
        this(0);
    }

    public Marque(int id) {
        this(id, null);
    }

    public Marque(String nom) {
        this.nom = nom;
    }

    public Marque(int id, String nom) {
        super();
        this.id = id;
        this.nom = nom;
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

    public int getNbVehicule() {
        return nbVehicule;
    }

    public void setNbVehicule(int nbVehicule) {
        this.nbVehicule = nbVehicule;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(nom);
        return stringBuilder.toString();
    }
}