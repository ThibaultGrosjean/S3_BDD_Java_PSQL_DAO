package test;

import dao.Dao;
import dao.jdbc.ContratDaoImpl;
import dao.exception.DaoException;
import model.*;
import sql.PostgresConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.Collection;

public class SimpleJdbcDaoTestContrat {
    private Connection connection;
    private Dao dao;


    public void testfindAllContrats() {
        Dao dao = new ContratDaoImpl(connection);

        try {
            Collection<Entity> contrats = dao.findAll();
            for (Entity entity : contrats) {
                Contrat contrat = (Contrat) entity;
                System.out.println(contrat);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdContrats(int id) {
        Dao dao = new ContratDaoImpl(connection);

        try {
            Contrat contrat = (Contrat) dao.findById(id);
            System.out.println(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateContrat(Contrat contrat) {
        try {
            dao.create(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditContrat(Contrat contrat) {
        try {
            dao.update(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteContrat(Contrat contrat) {
        try {
            dao.delete(contrat);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() throws DaoException {
        connection = PostgresConnection.getInstance();
        dao = new ContratDaoImpl(connection);
        testfindAllContrats();
        Contrat contratToGetRelation = (Contrat) dao.findById(1);
        Client client = contratToGetRelation.getClient();
        Vehicule vehicule = contratToGetRelation.getVehicule();
        Agence agence = contratToGetRelation.getAgence();

        Contrat contrat = new Contrat(
                8,
                Date.valueOf("2020-04-10"),
                Date.valueOf("2020-04-20"),
                24000,
                25742,
                client,
                vehicule,
                agence
        );

        //create
        System.out.println("\n***** Création d'une contrat : ");
        testCreateContrat(contrat);

        //find all
        System.out.println("\n***** Liste des contrats : ");
        testfindAllContrats();

        //find by id
        System.out.println("\n***** Contrat 5 : ");
        testfindByIdContrats(5);

        //edit
        System.out.println("\n***** Modification d'une contrat : ");
        contrat.setKmRetour(9999999);
        testEditContrat(contrat);
        testfindAllContrats();

        //delete
        System.out.println("\n***** Suppression d'une contrat : ");
        testDeleteContrat(contrat);
        testfindAllContrats();
    }


    public static void main(String[] args) throws DaoException {
        new SimpleJdbcDaoTestContrat().test();
    }
}