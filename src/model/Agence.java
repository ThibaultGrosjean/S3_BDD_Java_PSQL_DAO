package model;

public class Agence extends Entity {

    private int id;
    private int nbEmployes;
    private Ville ville;

    public Agence() {
        this(0);
    }

    public Agence(int id) {
        this(id, 0);
    }
    
    public Agence(int id, int nbEmployes) {
        super();
        this.id = id;
        this.nbEmployes = nbEmployes;
    }

    public Agence(int id, int nbEmployes, Ville ville) {
        super();
        this.id = id;
        this.nbEmployes = nbEmployes;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbEmployes() {
        return nbEmployes;
    }

    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(nbEmployes).append(" | ").append(ville);
        return stringBuilder.toString();
    }
}