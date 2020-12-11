package test;

import dao.Dao;
import dao.jdbc.CategorieDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Categorie;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestCategorie {
    private Connection connection;
    private Dao dao;

    public void testfindAllCategories() {
        Dao dao = new CategorieDaoImpl(connection);

        try {
            Collection<Entity> categories = dao.findAll();
            for (Entity entity : categories) {
                Categorie categorie = (Categorie) entity;
                System.out.println(categorie);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindChiffreAffaireCategories() {
        CategorieDaoImpl dao = new CategorieDaoImpl(connection);

        try {
            Collection<Entity> categories = dao.findChiffreAffaireCategorie();
            for (Entity entity : categories) {
                Categorie categorie = (Categorie) entity;
                System.out.println(categorie.getId() + " | " + categorie.getLibelle() + " | " + categorie.getChiffreAffaire());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdCategories(int id) {
        Dao dao = new CategorieDaoImpl(connection);

        try {
            Categorie categorie = (Categorie) dao.findById(id);
            System.out.println(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateCategorie(Categorie categorie) {
        try {
            dao.create(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditCategorie(Categorie categorie) {
        try {
            dao.update(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteCategorie(Categorie categorie) {
        try {
            dao.delete(categorie);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new CategorieDaoImpl(connection);
        testfindAllCategories();

        Categorie categorie = new Categorie("CATEGORIE");

        //create
        System.out.println("\n***** Création d'une categorie : ");
        testCreateCategorie(categorie);

        //find all
        System.out.println("\n***** Liste des categories : ");
        testfindAllCategories();

        //find by id
        System.out.println("\n***** Categorie 5 : ");
        testfindByIdCategories(5);


        //edit
        System.out.println("\n***** Modification d'une categorie : ");
        testEditCategorie(
                new Categorie(1,"CATEGORIEEDIT")
        );
        testfindAllCategories();

        //delete
        System.out.println("\n***** Suppression d'une categorie : ");
        categorie.setId(5);
        testDeleteCategorie(categorie);
        testfindAllCategories();

        // 8.
        System.out.println("\n***** Le chiffre d’affaire par catégorie : ");
        testfindChiffreAffaireCategories();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestCategorie().test();
    }
}