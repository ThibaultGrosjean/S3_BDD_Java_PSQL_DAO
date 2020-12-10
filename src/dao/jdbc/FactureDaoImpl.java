package dao.jdbc;
import dao.exception.DaoException;
import model.Contrat;
import model.Entity;
import model.Facture;
import model.Ville;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class FactureDaoImpl extends JdbcDao {

    private ContratDaoImpl contratDao ;

    public FactureDaoImpl(Connection connection) {
        super(connection);
        contratDao = new ContratDaoImpl(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> factures = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facture");

            while (resultSet.next()) {
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("idFacture"));
                facture.setMontant(resultSet.getInt("montant"));
                facture.setContrat((Contrat) contratDao.findById(resultSet.getInt("idContrat")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return factures;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Facture facture = new Facture();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facture WHERE idFacture ="+ id +";");

            while (resultSet.next()) {
                facture.setId(resultSet.getInt("idFacture"));
                facture.setMontant(resultSet.getInt("montant"));
                facture.setContrat((Contrat) contratDao.findById(resultSet.getInt("idContrat")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return facture;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Facture facture = (Facture) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO facture(montant, idContrat) VALUES (?,?)";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, facture.getMontant());
            stmt.setInt(2, facture.getContrat().getId());

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
        Facture facture = (Facture) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE FACTURE set montant = (?) , idContrat = (?) WHERE idFacture = (?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, facture.getMontant());
            stmt.setInt(2, facture.getContrat().getId());
            stmt.setInt(3, facture.getId());


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
        Facture facture = (Facture) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM facture WHERE idfacture = ?;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, facture.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}