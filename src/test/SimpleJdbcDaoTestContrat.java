package test;

import dao.Dao;
import dao.jdbc.AgenceDaoImpl;
import dao.jdbc.ContratDaoImpl;
import dao.exception.DaoException;
import dao.jdbc.FactureDaoImpl;
import dao.jdbc.VehiculeDaoImpl;
import model.*;
import sql.PostgresConnection;

import java.sql.Connection;
import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        System.out.println("\n***** Contrat id : ");
        testfindByIdContrats(contrat.getId());

        //edit
        System.out.println("\n***** Modification d'une contrat : ");
        contrat.setKmRetour(9999999);
        testEditContrat(contrat);
        testfindAllContrats();

        //delete
        System.out.println("\n***** Suppression d'une contrat : ");
        testDeleteContrat(contrat);
        testfindAllContrats();

        //TODO: Requete 7
        System.out.println("\n***** Client ayant realiser le plus de loc agence donnee et annee : ");
        testClientAyantRealPlusDeLoc(2020,1);
    }

    public void testClientAyantRealPlusDeLoc(int annee , int agenceId) {
        ContratDaoImpl dao = new ContratDaoImpl(connection);

        try {
            Collection<Entity> clients = dao.findMeilleurClientAnnee(annee,agenceId);
            for (Entity entity : clients) {
                Client client = (Client) entity;
                System.out.println(client);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DaoException {
        new SimpleJdbcDaoTestContrat().test();
    }
}