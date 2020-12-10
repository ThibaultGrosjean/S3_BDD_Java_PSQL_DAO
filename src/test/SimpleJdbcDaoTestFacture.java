package test;

import dao.Dao;
import dao.jdbc.ContratDaoImpl;
import dao.jdbc.FactureDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Facture;
import model.Contrat;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestFacture {
    private Connection connection;
    private Dao dao;

    public void testfindAllFactures() {
        Dao dao = new FactureDaoImpl(connection);

        try {
            Collection<Entity> factures = dao.findAll();
            for (Entity entity : factures) {
                Facture facture = (Facture) entity;
                System.out.println(facture.getId()
                        + " | " + facture.getMontant()
                        + " | " + facture.getContrat().getId()
                        + " | " );
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdFactures(int id) {
        Dao dao = new FactureDaoImpl(connection);

        try {
            Facture facture = (Facture) dao.findById(id);
            System.out.println(facture.getId()
                    + " | " + facture.getMontant()
                    + " | " + facture.getContrat().getId()
                    + " | " );        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateFacture(Facture facture) {
        try {
            dao.create(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditFacture(Facture facture) {
        try {
            dao.update(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteFacture(Facture facture) {
        try {
            dao.delete(facture);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() throws DaoException {
        connection = PostgresConnection.getInstance();
        dao = new FactureDaoImpl(connection);
        ContratDaoImpl contratDao = new ContratDaoImpl(connection);
        testfindAllFactures();


        Facture facture = new Facture(
                7,
                600000,
                (Contrat) contratDao.findById(1)
        );

        //create
        System.out.println("\n***** Création d'une facture : ");
        testCreateFacture(facture);

        //find all
        System.out.println("\n***** Liste des factures : ");
        testfindAllFactures();

        //find by id
        System.out.println("\n***** Facture 5 : ");
        testfindByIdFactures(5);


        //edit
        System.out.println("\n***** Modification d'une facture : ");
        facture.setMontant(9999999);
        testEditFacture(facture);
        testfindAllFactures();

        //delete
        System.out.println("\n***** Suppression d'une facture : ");
        testDeleteFacture(facture);
        testfindAllFactures();
    }


    public static void main(String[] args) throws DaoException {
        new SimpleJdbcDaoTestFacture().test();
    }
}