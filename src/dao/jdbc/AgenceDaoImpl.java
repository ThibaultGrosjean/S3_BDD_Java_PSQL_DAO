package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Agence;
import model.Ville;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class AgenceDaoImpl extends JdbcDao {

    private VilleDaoImpl villeDao ;

    public AgenceDaoImpl(Connection connection) {
        super(connection);
        villeDao = new VilleDaoImpl(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> agences = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agence");

            while (resultSet.next()) {
                Agence agence = new Agence();
                agence.setId(resultSet.getInt("idagence"));
                agence.setNbEmployes(resultSet.getInt("nbEmployes"));
                agence.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
                agences.add(agence);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return agences;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Agence agence = new Agence();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM agence WHERE idAgence ="+ id +";");

            while (resultSet.next()) {
                agence.setId(resultSet.getInt("idagence"));
                agence.setNbEmployes(resultSet.getInt("nbEmployes"));
                agence.setVille((Ville) villeDao.findById(resultSet.getInt("idville")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return agence;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Agence agence = (Agence) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO agence(nbemployes, idville) VALUES (?,?)";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, agence.getNbEmployes());
            stmt.setInt(2, agence.getVille().getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne insérée");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void update(Entity entity) throws DaoException {
        Agence agence = (Agence) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE AGENCE set nbEmployes = (?) , idVille = (?) WHERE idAgence = (?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, agence.getNbEmployes());
            stmt.setInt(2, agence.getVille().getId());
            stmt.setInt(3, agence.getId());


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
        Agence agence = (Agence) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM agence WHERE idagence = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, agence.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}