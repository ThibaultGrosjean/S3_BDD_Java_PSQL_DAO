package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TypeDaoImpl extends JdbcDao {

    public TypeDaoImpl(Connection connection) {
        super(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> types = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TYPE;");

            while (resultSet.next()) {
                Type type = new Type();
                type.setId(resultSet.getInt("idtype"));
                type.setLibelle(resultSet.getString("libelletype"));
                types.add(type);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return types;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Type type = new Type();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM type WHERE idType ="+ id +";");

            while (resultSet.next()) {
                type.setId(resultSet.getInt("idtype"));
                type.setLibelle(resultSet.getString("libelletype"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return type;
    }

    public void findChiffreAffaireType() throws DaoException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT t.idType, t.libelleType ,  SUM(f.montant) AS ChiffreAffaire " +
                        "FROM FACTURE f " +
                        "JOIN CONTRAT C ON C.idContrat = f.idContrat " +
                        "JOIN VEHICULE V ON C.immatriculation = V.immatriculation " +
                        "JOIN type t ON V.idType = t.idtype " +
                        "GROUP BY t.idType,t.libelleType;");

            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getInt("idType"))
                        .append(" | ").append(resultSet.getString("libelletype"))
                        .append(" | ").append(resultSet.getInt("ChiffreAffaire"));
                System.out.println(stringBuilder.toString());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Type type = (Type) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO TYPE(libelleType) VALUES (?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, type.getLibelle());

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
        Type type = (Type) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE TYPE set libelleType = (?) WHERE idType = (?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setString(1, type.getLibelle());
            stmt.setInt(2, type.getId());


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
        Type type = (Type) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM type WHERE idtype = (?);";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, type.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}