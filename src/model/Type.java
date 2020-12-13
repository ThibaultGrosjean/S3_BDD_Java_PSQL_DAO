package model;

public class Type extends Entity {

    private int id;
    private String libelle;

    public Type() {
        this(0);
    }

    public Type(int id) {
        this(id, null);
    }

    public Type(String libelle) {
        this.libelle = libelle;
    }

    public Type(int id, String libelle) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(" | ").append(libelle);
        return stringBuilder.toString();
    }
}