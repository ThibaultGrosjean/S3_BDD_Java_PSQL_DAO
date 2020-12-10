package test;

import dao.Dao;
import dao.jdbc.VehiculeDaoImpl;
import dao.exception.DaoException;
import model.*;
import sql.PostgresConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.Collection;

public class SimpleJdbcDaoTestVehicule {
    private Connection connection;
    private Dao dao;

    public void testfindAllVehicules() {
        Dao dao = new VehiculeDaoImpl(connection);

        try {
            Collection<Entity> vehicules = dao.findAll();
            for (Entity entity : vehicules) {
                Vehicule vehicule = (Vehicule) entity;
                System.out.println(vehicule);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdVehicules(int id) {
        Dao dao = new VehiculeDaoImpl(connection);

        try {
            Vehicule vehicule = (Vehicule) dao.findById(id);
            System.out.println(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateVehicule(Vehicule vehicule) {
        try {
            dao.create(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditVehicule(Vehicule vehicule) {
        try {
            dao.update(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteVehicule(Vehicule vehicule) {
        try {
            dao.delete(vehicule);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() throws DaoException {
        connection = PostgresConnection.getInstance();
        dao = new VehiculeDaoImpl(connection);
        testfindAllVehicules();
        Vehicule vehiculeToGetRelation = (Vehicule) dao.findById(1);
        Agence agence = vehiculeToGetRelation.getAgence();
        Marque marque = vehiculeToGetRelation.getMarque();
        Modele modele = vehiculeToGetRelation.getModele();
        Categorie categorie = vehiculeToGetRelation.getCategorie();
        Type type = vehiculeToGetRelation.getType();

        Vehicule vehicule = new Vehicule(
                9,
                Date.valueOf("2002-04-24"),
                "Abimée",
                240000,
                150,
                marque,
                modele,
                categorie,
                type,
                agence
        );

        //create
        System.out.println("\n***** Création d'une vehicule : ");
        testCreateVehicule(vehicule);

        //find all
        System.out.println("\n***** Liste des vehicules : ");
        testfindAllVehicules();

        //find by id
        System.out.println("\n***** Vehicule 5 : ");
        testfindByIdVehicules(5);

        //edit
        System.out.println("\n***** Modification d'une vehicule : ");
        vehicule.setPrixParJourDeLocation(9999999);
        testEditVehicule(vehicule);
        testfindAllVehicules();

        //delete
        System.out.println("\n***** Suppression d'une vehicule : ");
        testDeleteVehicule(vehicule);
        testfindAllVehicules();
    }


    public static void main(String[] args) throws DaoException {
        new SimpleJdbcDaoTestVehicule().test();
    }
}