package model;

public class Ville extends Entity {

    private int id;
    private String nom;
    private int nbHabitant;

    public Ville() {
        this(0);
    }

    public Ville(int id) {
        this(id, null);
    }

    public Ville(String nom) {
        this.nom = nom;
    }

    public Ville(int id, String nom) {
        super();
        this.id = id;
        this.nom = nom;
    }

    public Ville(int id, String nom, int nbHabitant) {
        super();
        this.id = id;
        this.nom = nom;
        this.nbHabitant = nbHabitant;
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

    public int getNbHabitant() {
        return nbHabitant;
    }

    public void setNbHabitant(int nbHabitant) {
        this.nbHabitant = nbHabitant;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(nom).append(" | ").append(nbHabitant);
        return stringBuilder.toString();
    }
}