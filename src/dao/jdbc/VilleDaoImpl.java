package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Ville;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VilleDaoImpl extends JdbcDao {

    public VilleDaoImpl(Connection connection) {
        super(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> villes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville");

            while (resultSet.next()) {
                Ville ville = new Ville();
                ville.setId(resultSet.getInt("idville"));
                ville.setNom(resultSet.getString("nomville"));
                ville.setNbHabitant(resultSet.getInt("nombrehabitants"));
                villes.add(ville);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return villes;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Ville ville = new Ville();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville WHERE idVille ="+ id +";");

            while (resultSet.next()) {
                ville.setId(resultSet.getInt("idville"));
                ville.setNom(resultSet.getString("nomville"));
                ville.setNbHabitant(resultSet.getInt("nombrehabitants"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ville;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Ville ville = (Ville) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO ville(nomville, nombrehabitants) VALUES (?,?)";

        try {
            stmt = connection.prepareStatement(sqlReq,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, ville.getNom());
            stmt.setInt(2, ville.getNbHabitant());

            int res = stmt.executeUpdate();
            if (res > 0) {
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        ville.setId((int) resultSet.getLong(1));
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
        Ville ville = (Ville) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE ville SET nomVille = (?), nombreHabitants = (?) WHERE idville = (?);";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, ville.getNom());
            stmt.setInt(2, ville.getNbHabitant());
            stmt.setInt(3, ville.getId());


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
        Ville ville = (Ville) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM ville WHERE idville = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, ville.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}