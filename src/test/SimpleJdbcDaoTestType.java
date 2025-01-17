package test;

import dao.Dao;
import dao.jdbc.TypeDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Type;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestType {
    private Connection connection;
    private Dao dao;

    public void testfindAllTypes() {
        Dao dao = new TypeDaoImpl(connection);

        try {
            Collection<Entity> types = dao.findAll();
            for (Entity entity : types) {
                Type type = (Type) entity;
                System.out.println(type);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdTypes(int id) {
        Dao dao = new TypeDaoImpl(connection);

        try {
            Type type = (Type) dao.findById(id);
            System.out.println(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testChiffreAffaireTypes() {
        TypeDaoImpl dao = new TypeDaoImpl(connection);
        try {
            dao.findChiffreAffaireType();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateType(Type type) {
        try {
            dao.create(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditType(Type type) {
        try {
            dao.update(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteType(Type type) {
        try {
            dao.delete(type);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new TypeDaoImpl(connection);
        testfindAllTypes();

        Type type = new Type("TYPE");

        //create
        System.out.println("\n***** Création d'une type : ");
        testCreateType(type);

        //find all
        System.out.println("\n***** Liste des types : ");
        testfindAllTypes();

        //find by id
        System.out.println("\n***** Type id : ");
        testfindByIdTypes(type.getId());


        //edit
        System.out.println("\n***** Modification d'une type : ");
        type.setLibelle("TYPEEDIT");
        testEditType(type);
        testfindAllTypes();

        //delete
        System.out.println("\n***** Suppression d'une type : ");
        testDeleteType(type);
        testfindAllTypes();

        //TODO: Requete 9
        System.out.println("\n***** Le chiffre d’affaire par type : ");
        testChiffreAffaireTypes();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestType().test();
    }
}