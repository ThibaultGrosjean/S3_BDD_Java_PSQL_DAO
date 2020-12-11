package model;

public class Agence extends Entity {

    private int id;
    private int nbEmployes;
    private Ville ville;
    private int nbVehicule;
    private int chiffreAffaire;

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

    public int getNbVehicule() {
        return nbVehicule;
    }

    public void setNbVehicule(int nbVehicule) {
        this.nbVehicule = nbVehicule;
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
        stringBuilder.append(id).append(" | ").append(nbEmployes).append(" | ").append(ville);
        return stringBuilder.toString();
    }
}