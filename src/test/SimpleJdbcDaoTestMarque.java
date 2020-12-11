package test;

import dao.Dao;
import dao.exception.DaoException;
import dao.jdbc.AgenceDaoImpl;
import dao.jdbc.MarqueDaoImpl;
import model.Agence;
import model.Entity;
import model.Marque;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestMarque {
    private Connection connection;
    private Dao dao;

    public void testfindAllMarques() {
        Dao dao = new MarqueDaoImpl(connection);

        try {
            Collection<Entity> marques = dao.findAll();
            for (Entity entity : marques) {
                Marque marque = (Marque) entity;
                System.out.println(marque);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testNbVehiculeMarque() {
        MarqueDaoImpl dao = new MarqueDaoImpl(connection);

        try {
            Collection<Entity> marques = dao.findNbVehiculeMarque();
            for (Entity entity : marques) {
                Marque marque = (Marque) entity;
                System.out.println(marque.getNom() + " | " + marque.getNbVehicule());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdMarques(int id) {
        Dao dao = new MarqueDaoImpl(connection);

        try {
            Marque marque = (Marque) dao.findById(id);
            System.out.println(marque);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateMarque(Marque marque) {
        try {
            dao.create(marque);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditMarque(Marque marque) {
        try {
            dao.update(marque);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteMarque(Marque marque) {
        try {
            dao.delete(marque);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new MarqueDaoImpl(connection);
        testfindAllMarques();

        //create
        System.out.println("\n***** Création d'une marque : ");
        Marque marque = new Marque("Wolsvagen");
        testCreateMarque(marque);

        //find all
        System.out.println("\n***** Liste des marques : ");
        testfindAllMarques();

        //find by id
        System.out.println("\n***** Marque wolsvagen : ");
        testfindByIdMarques(6);

        //edit
        System.out.println("\n***** Modification d'une marque : ");
        testEditMarque(new Marque(1,"VROUUUM"));
        testfindAllMarques();

        //delete
        System.out.println("\n***** Suppression d'une marque : ");
        marque.setId(6);
        testDeleteMarque(marque);
        testfindAllMarques();

        // 6.
        System.out.println("\n***** Le nombre de véhicules pour chaque marque : ");
        testNbVehiculeMarque();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestMarque().test();
    }
}