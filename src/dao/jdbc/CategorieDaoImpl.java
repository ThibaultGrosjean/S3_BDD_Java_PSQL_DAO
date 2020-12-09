package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CategorieDaoImpl extends JdbcDao {

    public CategorieDaoImpl(Connection connection) {
        super(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> categories = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorie");

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("idcategorie"));
                categorie.setLibelle(resultSet.getString("libellecategorie"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return categories;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Categorie categorie = new Categorie();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorie WHERE idCategorie ="+ id +";");

            while (resultSet.next()) {
                categorie.setId(resultSet.getInt("idcategorie"));
                categorie.setLibelle(resultSet.getString("libellecategorie"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return categorie;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Categorie categorie = (Categorie) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO categorie(libellecategorie) VALUES (?)";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, categorie.getLibelle());

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
        Categorie categorie = (Categorie) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE categorie SET libelleCategorie = (?) WHERE idcategorie = (?);";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, categorie.getLibelle());
            stmt.setInt(2, categorie.getId());


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
        Categorie categorie = (Categorie) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM categorie WHERE idcategorie = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, categorie.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}