package test;

import dao.Dao;
import dao.jdbc.AgenceDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Agence;
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
                System.out.println(agence.getId() + " | " + agence.getNbEmployes() + "|" + agence.getVille().getId() + "|"+ agence.getVille().getNom() + "|"+ agence.getVille().getNbHabitant() + "|");
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdAgences(int id) {
        Dao dao = new AgenceDaoImpl(connection);

        try {
            Agence agence = (Agence) dao.findById(id);
            System.out.println(agence.getId() + " | " + agence.getNbEmployes() + "|" + agence.getVille().getId() + "|"+ agence.getVille().getNom() + "|"+ agence.getVille().getNbHabitant() + "|");
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

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new AgenceDaoImpl(connection);
        testfindAllAgences();

        Agence agence = new Agence(5,600000);
        agence.setVille(new Ville(1));

        //create
        System.out.println("\n***** Création d'une agence : ");
        testCreateAgence(agence);

        //find all
        System.out.println("\n***** Liste des agences : ");
        testfindAllAgences();

        //find by id
        System.out.println("\n***** Agence 5 : ");
        testfindByIdAgences(5);


        //edit
        System.out.println("\n***** Modification d'une agence : ");
        agence.setNbEmployes(0101010);
        testEditAgence(agence);
        testfindAllAgences();

        //delete
        System.out.println("\n***** Suppression d'une agence : ");
        agence.setId(5);
        testDeleteAgence(agence);
        testfindAllAgences();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestAgence().test();
    }
}