package model;

import java.util.Date;

public class Contrat extends Entity {

    private int id;
    private Date dateDeRetrait;
    private Date dateDeRetour;
    private int kmRetrait;
    private int kmRetour;
    private Client client;
    private Vehicule vehicule;
    private Agence agence;

    public Contrat() {
        this(0);
    }

    public Contrat(int id) {
        this.id=id;
    }

    public Contrat(int id, Date dateDeRetrait,Date dateDeRetour, int kmRetrait, int kmRetour, Client client, Vehicule vehicule, Agence agence) {
        this(id);
        this.dateDeRetrait = dateDeRetrait;
        this.dateDeRetour = dateDeRetour;
        this.kmRetrait = kmRetrait;
        this.kmRetour = kmRetour;
        this.client = client;
        this.vehicule = vehicule;
        this.agence = agence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDeRetrait() {
        return dateDeRetrait;
    }

    public void setDateDeRetrait(Date dateDeRetrait) {
        this.dateDeRetrait = dateDeRetrait;
    }

    public Date getDateDeRetour() {
        return dateDeRetour;
    }

    public void setDateDeRetour(Date dateDeRetour) {
        this.dateDeRetour = dateDeRetour;
    }

    public int getKmRetrait() {
        return kmRetrait;
    }

    public void setKmRetrait(int kmRetrait) {
        this.kmRetrait = kmRetrait;
    }

    public int getKmRetour() {
        return kmRetour;
    }

    public void setKmRetour(int kmRetour) {
        this.kmRetour = kmRetour;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id)
                .append(" | ").append(dateDeRetrait)
                .append(" | ").append(dateDeRetour)
                .append(" | ").append(kmRetrait)
                .append(" | ").append(kmRetour)
                .append(" | ").append(client)
                .append(" | ").append(vehicule)
                .append(" | ").append(agence)
        ;
        return stringBuilder.toString();
    }
}