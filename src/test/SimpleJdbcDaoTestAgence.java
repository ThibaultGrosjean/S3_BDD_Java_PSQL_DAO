package test;

import dao.Dao;
import dao.jdbc.AgenceDaoImpl;
import dao.exception.DaoException;
import dao.jdbc.VehiculeDaoImpl;
import model.Entity;
import model.Agence;
import model.Vehicule;
import model.Ville;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestAgence {
    private Connection connection;
    private Dao dao;

    public void testfindAllAgences() {
        Dao dao = new AgenceDaoImpl(connection);

        try {
            Collection<Entity> agences = dao.findAll();
            for (Entity entity : agences) {
                Agence agence = (Agence) entity;
                System.out.println(agence);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testChiffreAffaire(Agence agence, int mois, int annee) {
        AgenceDaoImpl dao = new AgenceDaoImpl(connection);

        try {
            int chiffreAffaire = dao.findChiffreAffaire(agence.getId(), mois, annee);
            System.out.println("Chiffre d'affaire de "+ agence.getVille().getNom() +" : " + chiffreAffaire);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testNbVehiculeByAnneeAndKm(int nbAnnee, int nbKm) {
        AgenceDaoImpl dao = new AgenceDaoImpl(connection);
        try {
            dao.findNbVehicule(nbAnnee, nbKm);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    public void testFindChiffreAffaireAnnee(int nbAnnee) {
        AgenceDaoImpl dao = new AgenceDaoImpl(connection);
        try {
            dao.findChiffreAffaireAnnee(nbAnnee);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdAgences(int id) {
        Dao dao = new AgenceDaoImpl(connection);
        try {
            Agence agence = (Agence) dao.findById(id);
            System.out.println(agence);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateAgence(Agence agence) {
        try {
            dao.create(agence);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditAgence(Agence agence) {
        try {
            dao.update(agence);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteAgence(Agence agence) {
        try {
            dao.delete(agence);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public Agence getAgencesById(int id) {
        Dao dao = new AgenceDaoImpl(connection);
        Agence agence = new Agence();
        try {
            agence = (Agence) dao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return agence;
    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new AgenceDaoImpl(connection);
        testfindAllAgences();

        Agence agence = new Agence(0,600000);
        agence.setVille(new Ville(1));

        //create
        System.out.println("\n***** Création d'une agence : ");
        testCreateAgence(agence);

        //find all
        System.out.println("\n***** Liste des agences : ");
        testfindAllAgences();

        //find by id
        System.out.println("\n***** Agence id : ");
        testfindByIdAgences(agence.getId());


        //edit
        System.out.println("\n***** Modification d'une agence : ");
        agence.setNbEmployes(1000);
        testEditAgence(agence);
        testfindAllAgences();

        //delete
        System.out.println("\n***** Suppression d'une agence : ");
        testDeleteAgence(agence);
        testfindAllAgences();

        //TODO: Requete 5
        System.out.println("\n***** Chiffre d'affaire de l'agence de lyon pour janvier 2020: ");
        testChiffreAffaire(getAgencesById(2), 1, 2020);

        //TODO: Requete 10
        int nbAnnee = 2;
        int nbKm = 150000;
        System.out.println("\n***** Le nombre de véhicule(s) de plus de "+ nbAnnee +" ans et de plus de "+  nbKm +" km pour chacune des agences : ");
        testNbVehiculeByAnneeAndKm(nbAnnee, nbKm);

        //TODO: Requete 11
        System.out.println("\n***** Le chiffre d’affaire pour une année donnée pour chacune des agences ");
        testFindChiffreAffaireAnnee(2020);
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestAgence().test();
    }
}