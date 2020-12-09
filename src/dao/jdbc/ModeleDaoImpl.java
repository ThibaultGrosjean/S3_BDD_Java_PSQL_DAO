package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ModeleDaoImpl extends JdbcDao {

    public ModeleDaoImpl(Connection connection) {
        super(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> modeles = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM modele");

            while (resultSet.next()) {
                Modele modele = new Modele();
                modele.setId(resultSet.getInt("idmodele"));
                modele.setDenomination(resultSet.getString("denomination"));
                modele.setPuissanceFiscale(resultSet.getInt("puissancefiscale"));
                modeles.add(modele);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return modeles;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Modele modele = new Modele();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM modele WHERE idModele ="+ id +";");

            while (resultSet.next()) {
                modele.setId(resultSet.getInt("idmodele"));
                modele.setDenomination(resultSet.getString("denomination"));
                modele.setPuissanceFiscale(resultSet.getInt("puissancefiscale"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return modele;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Modele modele = (Modele) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO MODELE(denomination, puissanceFiscale) VALUES ((?),(?)) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, modele.getDenomination());
            stmt.setInt(2,modele.getPuissanceFiscale());

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
        Modele modele = (Modele) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE MODELE set denomination = (?) , puissanceFiscale = (?) WHERE idModele = (?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, modele.getDenomination());
            stmt.setInt(2, modele.getPuissanceFiscale());
            stmt.setInt(3, modele.getId());


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
        Modele modele = (Modele) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM modele WHERE idmodele = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, modele.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}