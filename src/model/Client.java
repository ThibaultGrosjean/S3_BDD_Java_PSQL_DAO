package model;

public class Client extends Entity {

    private int id;
    private String nom;
    private String adresse;
    private int codePostale;
    private Ville ville;

    public Client() {
        this(0);
    }

    public Client(int id) {
        this(id, null);
    }

    public Client(String nom) {
        this.nom = nom;
    }

    public Client(int id, String nom) {
        super();
        this.id = id;
        this.nom = nom;
    }

    public Client(int id, String nom, String adresse) {
        this(id, nom);
        this.adresse = adresse;
    }

    public Client(int id, String nom, String adresse, int codePostale) {
        this(id, nom, adresse);
        this.codePostale = codePostale;
    }

    public Client(int id, String nom, String adresse, int codePostale, Ville ville) {
        this(id, nom, adresse, codePostale);
        this.ville = ville;
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

    public String getNbHabitant() {
        return adresse;
    }

    public void setNbHabitant(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(int codePostale) {
        this.codePostale = codePostale;
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
        stringBuilder.append(id).append(" | ").append(nom).append(" | ").append(adresse).append(" | ").append(codePostale).append(" | ").append(ville);
        return stringBuilder.toString();
    }
}