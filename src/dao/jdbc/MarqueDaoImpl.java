package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MarqueDaoImpl extends JdbcDao {

    public MarqueDaoImpl(Connection connection) {
        super(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> marques = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marque");

            while (resultSet.next()) {
                Marque marque = new Marque();
                marque.setId(resultSet.getInt("idmarque"));
                marque.setNom(resultSet.getString("nommarque"));
                marques.add(marque);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return marques;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Marque marque = new Marque();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marque WHERE idMarque ="+ id +";");

            while (resultSet.next()) {
                marque.setId(resultSet.getInt("idmarque"));
                marque.setNom(resultSet.getString("nommarque"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return marque;
    }

    public void findNbVehiculeMarque() throws DaoException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                     "SELECT m.nomMarque, COUNT(v.immatriculation) AS nbVehiculeParMarque " +
                        "FROM MARQUE AS m " +
                        "JOIN VEHICULE AS v ON m.idMarque = v.idMarque " +
                        "GROUP BY m.idMarque ;");

            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getString("nommarque"))
                        .append(" | ").append(resultSet.getInt("nbVehiculeParMarque"));
                System.out.println(stringBuilder.toString());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Marque marque = (Marque) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO marque(nommarque) VALUES (?)";

        try {
            stmt = connection.prepareStatement(sqlReq,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, marque.getNom());

            int res = stmt.executeUpdate();
            if (res > 0) {
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        marque.setId((int) resultSet.getLong(1));
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
        Marque marque = (Marque) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE marque SET nomMarque = (?) WHERE idMarque = (?);";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, marque.getNom());
            stmt.setInt(2, marque.getId());


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
        Marque marque = (Marque) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM marque WHERE idmarque = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, marque.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}