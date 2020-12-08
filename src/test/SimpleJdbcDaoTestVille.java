package test;

import dao.Dao;
import dao.jdbc.VilleDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Ville;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestVille {
    private Connection connection;
    private Dao dao;

    public void testfindAllVilles() {
        Dao dao = new VilleDaoImpl(connection);

        try {
            Collection<Entity> villes = dao.findAll();
            for (Entity entity : villes) {
                Ville ville = (Ville) entity;
                System.out.println(ville.getId() + " | " + ville.getNom()+ " | " + ville.getNbHabitant());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdVilles(int id) {
        Dao dao = new VilleDaoImpl(connection);

        try {
            Ville ville = (Ville) dao.findById(id);
            System.out.println(ville.getId() + " | " + ville.getNom() + " | " + ville.getNbHabitant());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateVille(Ville ville) {
        try {
            dao.create(ville);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditVille(Ville ville) {
        try {
            dao.update(ville);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteVille(Ville ville) {
        try {
            dao.delete(ville);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new VilleDaoImpl(connection);
        testfindAllVilles();

        //create
        System.out.println("\n***** Création d'une ville : ");
        Ville ville = new Ville("Dijon");
        ville.setNbHabitant(656156);
        testCreateVille(ville);

        //find all
        System.out.println("\n***** Liste des villes : ");
        testfindAllVilles();

        //find by id
        System.out.println("\n***** Ville 5 : ");
        testfindByIdVilles(5);

        //edit
        System.out.println("\n***** Modification d'une ville : ");
        ville.setNom("Dijon++");
        testEditVille(ville);
        testfindAllVilles();

        //delete
        System.out.println("\n***** Suppression d'une ville : ");
        testDeleteVille(ville);
        testfindAllVilles();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestVille().test();
    }
}