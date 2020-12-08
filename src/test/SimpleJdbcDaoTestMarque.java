package test;

import dao.Dao;
import dao.exception.DaoException;
import dao.jdbc.MarqueDaoImpl;
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
                System.out.println(marque.getId() + " | " + marque.getNom());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdMarques(int id) {
        Dao dao = new MarqueDaoImpl(connection);

        try {
            Marque marque = (Marque) dao.findById(id);
            System.out.println(marque.getId() + " | " + marque.getNom());
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
        marque.setNom("Wolsvagen das auto");
        testEditMarque(marque);
        testfindAllMarques();

        //delete
        System.out.println("\n***** Suppression d'une marque : ");
        testDeleteMarque(marque);
        testfindAllMarques();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestMarque().test();
    }
}