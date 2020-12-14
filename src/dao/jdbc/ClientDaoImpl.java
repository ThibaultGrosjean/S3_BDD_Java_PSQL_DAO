package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Client;
import model.Ville;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ClientDaoImpl extends JdbcDao {

    private VilleDaoImpl villeDao ;

    public ClientDaoImpl(Connection connection) {
        super(connection);
        villeDao = new VilleDaoImpl(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client");

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("idclient"));
                client.setNom(resultSet.getString("nomClient"));
                client.setAdresse(resultSet.getString("adresseClient"));
                client.setCodePostale(resultSet.getInt("codePostalCLient"));
                client.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return clients;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Client client = new Client();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE idClient ="+ id +";");

            while (resultSet.next()) {
                client.setId(resultSet.getInt("idclient"));
                client.setNom(resultSet.getString("nomClient"));
                client.setAdresse(resultSet.getString("adresseClient"));
                client.setCodePostale(resultSet.getInt("codePostalCLient"));
                client.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return client;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Client client = (Client) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO CLIENT(nomClient, adresseClient, codePostalCLient, idVille) VALUES ((?),(?), (?),(?)) ;\n";

        try {
            stmt = connection.prepareStatement(sqlReq,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getAdresse());
            stmt.setInt(3, client.getCodePostale());
            stmt.setInt(4, client.getVille().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        client.setId((int) resultSet.getLong(1));
                    }
                    else {
                        throw new SQLException("Creating failed, no ID obtained.");
                    }
                }
                System.out.println("Ligne insérée");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Client client = (Client) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE CLIENT set nomClient = (?) ,adresseClient = (?) ,codePostalCLient = (?) , idVille = (?) WHERE idClient = (?) ;\n";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getAdresse());
            stmt.setInt(3, client.getCodePostale());
            stmt.setInt(4, client.getVille().getId());
            stmt.setInt(5, client.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne modifié");
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Entity entity) throws DaoException {
        Client client = (Client) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM client WHERE idclient = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, client.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}