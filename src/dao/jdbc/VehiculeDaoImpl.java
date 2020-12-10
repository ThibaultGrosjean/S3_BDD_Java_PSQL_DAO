package dao.jdbc;
import dao.exception.DaoException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VehiculeDaoImpl extends JdbcDao {

    private MarqueDaoImpl marqueDao ;
    private ModeleDaoImpl modeleDao ;
    private CategorieDaoImpl categorieDao ;
    private TypeDaoImpl typeDao ;
    private AgenceDaoImpl agenceDao ;

    public VehiculeDaoImpl(Connection connection) {
        super(connection);
        marqueDao = new MarqueDaoImpl(connection);
        modeleDao = new ModeleDaoImpl(connection);
        categorieDao = new CategorieDaoImpl(connection);
        typeDao = new TypeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> vehicules = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicule");

            while (resultSet.next()) {
                Vehicule vehicule = new Vehicule();
                vehicule.setImmatriculation(resultSet.getInt("immatriculation"));
                vehicule.setDateMiseEnCirculation(resultSet.getDate("dateMiseEnCirculation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNbKilometres(resultSet.getInt("nbKilometres"));
                vehicule.setPrixParJourDeLocation(resultSet.getInt("prixParJourDeLocation"));
                vehicule.setMarque((Marque) marqueDao.findById(resultSet.getInt("idmarque")));
                vehicule.setModele((Modele) modeleDao.findById(resultSet.getInt("idModele")));
                vehicule.setCategorie((Categorie) categorieDao.findById(resultSet.getInt("idCategorie")));
                vehicule.setType((Type) typeDao.findById(resultSet.getInt("idType")));
                vehicule.setAgence((Agence) agenceDao.findById(resultSet.getInt("idAgence")));
                vehicules.add(vehicule);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return vehicules;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Vehicule vehicule = new Vehicule();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicule WHERE immatriculation ="+ id +";");

            while (resultSet.next()) {
                vehicule.setImmatriculation(resultSet.getInt("immatriculation"));
                vehicule.setDateMiseEnCirculation(resultSet.getDate("dateMiseEnCirculation"));
                vehicule.setEtat(resultSet.getString("etat"));
                vehicule.setNbKilometres(resultSet.getInt("nbKilometres"));
                vehicule.setPrixParJourDeLocation(resultSet.getInt("prixParJourDeLocation"));
                vehicule.setMarque((Marque) marqueDao.findById(resultSet.getInt("idmarque")));
                vehicule.setModele((Modele) modeleDao.findById(resultSet.getInt("idModele")));
                vehicule.setCategorie((Categorie) categorieDao.findById(resultSet.getInt("idCategorie")));
                vehicule.setType((Type) typeDao.findById(resultSet.getInt("idType")));
                vehicule.setAgence((Agence) agenceDao.findById(resultSet.getInt("idAgence")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return vehicule;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO VEHICULE(dateMiseEnCirculation, etat, nbKilometres, prixParJourDeLocation, idMarque, idModele, idCategorie, idType, idAgence) VALUES ((?),(?),?,?,?,?,?,?,?) ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setDate(1, (Date) vehicule.getDateMiseEnCirculation());
            stmt.setString(2, vehicule.getEtat());
            stmt.setInt(3, vehicule.getNbKilometres());
            stmt.setInt(4, vehicule.getPrixParJourDeLocation());
            stmt.setInt(5, vehicule.getMarque().getId());
            stmt.setInt(6, vehicule.getModele().getId());
            stmt.setInt(7, vehicule.getCategorie().getId());
            stmt.setInt(8, vehicule.getType().getId());
            stmt.setInt(9, vehicule.getAgence().getId());

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
        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE VEHICULE set dateMiseEnCirculation = (?) ,etat = (?) ,nbKilometres = ? , prixParJourDeLocation = ?, idMarque = ?, idModele = ?, idCategorie = ?, idType = ?, idAgence = ? WHERE immatriculation = ? ;";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setDate(1, (Date) vehicule.getDateMiseEnCirculation());
            stmt.setString(2, vehicule.getEtat());
            stmt.setInt(3, vehicule.getNbKilometres());
            stmt.setInt(4, vehicule.getPrixParJourDeLocation());
            stmt.setInt(5, vehicule.getMarque().getId());
            stmt.setInt(6, vehicule.getModele().getId());
            stmt.setInt(7, vehicule.getCategorie().getId());
            stmt.setInt(8, vehicule.getType().getId());
            stmt.setInt(9, vehicule.getAgence().getId());
            stmt.setInt(10, vehicule.getImmatriculation());

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
        Vehicule vehicule = (Vehicule) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM VEHICULE WHERE immatriculation = ? ;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, vehicule.getImmatriculation());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());

        }
    }

}