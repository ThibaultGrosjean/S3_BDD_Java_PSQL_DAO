package test;

import dao.Dao;
import dao.jdbc.ClientDaoImpl;
import dao.exception.DaoException;
import model.Entity;
import model.Client;
import model.Ville;
import sql.PostgresConnection;

import java.sql.Connection;
import java.util.Collection;

public class SimpleJdbcDaoTestClient {
    private Connection connection;
    private Dao dao;

    public void testfindAllClients() {
        Dao dao = new ClientDaoImpl(connection);

        try {
            Collection<Entity> clients = dao.findAll();
            for (Entity entity : clients) {
                Client client = (Client) entity;
                System.out.println(client);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testfindByIdClients(int id) {
        Dao dao = new ClientDaoImpl(connection);

        try {
            Client client = (Client) dao.findById(id);
            System.out.println(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void testCreateClient(Client client) {
        try {
            dao.create(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testEditClient(Client client) {
        try {
            dao.update(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void testDeleteClient(Client client) {
        try {
            dao.delete(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public void test() {
        connection = PostgresConnection.getInstance();
        dao = new ClientDaoImpl(connection);
        testfindAllClients();

        Client client = new Client(0,"CLIENT","7depihped", 25200);
        client.setVille(new Ville(1));

        //create
        System.out.println("\n***** Création d'une client : ");
        testCreateClient(client);

        //find all
        System.out.println("\n***** Liste des clients : ");
        testfindAllClients();

        //find by id
        System.out.println("\n***** Client find id : ");
        testfindByIdClients(client.getId());


        //edit
        System.out.println("\n***** Modification d'une client : ");
        client.setNom("CLIENTEDIT");
        testEditClient(client);
        testfindAllClients();

        //delete
        System.out.println("\n***** Suppression d'une client : ");
        testDeleteClient(client);
        testfindAllClients();
    }


    public static void main(String[] args) {
        new SimpleJdbcDaoTestClient().test();
    }
}