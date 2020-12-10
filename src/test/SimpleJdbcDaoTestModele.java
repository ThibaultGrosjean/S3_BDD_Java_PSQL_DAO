package test;

import dao.Dao;
import dao.jdbc.ModeleDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Modele;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestModele {
    private Connection connection;
    private Dao dao;

    public void testfindAllModeles() {
        Dao dao = new ModeleDaoImpl(connection);

        try {
            Collection<Entity> modeles = dao.findAll();
            for (Entity entity : modeles) {
                Modele modele = (Modele) entity;
                System.out.println(modele);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdModeles(int id) {
        Dao dao = new ModeleDaoImpl(connection);

        try {
            Modele modele = (Modele) dao.findById(id);
            System.out.println(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateModele(Modele modele) {
        try {
            dao.create(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditModele(Modele modele) {
        try {
            dao.update(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteModele(Modele modele) {
        try {
            dao.delete(modele);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new ModeleDaoImpl(connection);
        testfindAllModeles();

        Modele modele = new Modele(8,"MODELE", 1000);

        //create
        System.out.println("\n***** Création d'une modele : ");
        testCreateModele(modele);

        //find all
        System.out.println("\n***** Liste des modeles : ");
        testfindAllModeles();

        //find by id
        System.out.println("\n***** Modele 5 : ");
        testfindByIdModeles(5);


        //edit
        System.out.println("\n***** Modification d'une modele : ");
        testEditModele(
                new Modele(1,"MODELEEDIT")
        );
        testfindAllModeles();

        //delete
        System.out.println("\n***** Suppression d'une modele : ");
        testDeleteModele(modele);
        testfindAllModeles();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestModele().test();
    }
}