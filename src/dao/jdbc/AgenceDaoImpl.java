package dao.jdbc;
import dao.exception.DaoException;
import model.Entity;
import model.Agence;
import model.Vehicule;
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

    public int findChiffreAffaire(int idAgence, int mois, int annee) throws DaoException {
        int chiffreAffaire = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
             "SELECT SUM(f.montant) AS ChiffreAffaire " +
                "FROM AGENCE AS a " +
                "JOIN CONTRAT C ON a.idAgence = C.idAgenceDeRetour " +
                "JOIN FACTURE f ON C.idContrat = f.idcontrat " +
                "WHERE c.idAgenceDeRetour = "+ idAgence +
                " AND EXTRACT(MONTH FROM dateDeRetour) = "+ mois +" AND EXTRACT(YEAR FROM dateDeRetour) = "+ annee +";");

            while (resultSet.next()) {
                chiffreAffaire = resultSet.getInt("chiffreaffaire");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return chiffreAffaire;
    }

    public void findChiffreAffaireAnnee(int annee) throws DaoException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
            "SELECT v.nomville, v.idVille, a.idAgence ,SUM(f.montant) AS ChiffreAffaire " +
                "FROM AGENCE a " +
                "JOIN CONTRAT C ON a.idAgence = C.idAgenceDeRetour " +
                "JOIN FACTURE f ON C.idContrat = f.idcontrat " +
                "JOIN VILLE AS v ON a.idville = v.idville " +
                "WHERE EXTRACT(YEAR FROM dateDeRetour) = " + annee +
                " GROUP BY a.idAgence, v.idVille, v.nomville;");

            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getString("nomville"))
                        .append(" | ").append(resultSet.getInt("idVille"))
                        .append(" | ").append(resultSet.getInt("ChiffreAffaire"));
                System.out.println(stringBuilder.toString());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void findNbVehicule(int nbAnnee, int nbKm) throws DaoException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
            "SELECT a.idagence,  COUNT(*) as nbVehicule FROM VEHICULE " +
                    "join agence a on a.idagence = vehicule.idagence \n" +
                    "WHERE EXTRACT(YEAR FROM dateMiseEnCirculation) < EXTRACT(YEAR FROM NOW()) - " + nbAnnee + " \n" +
                    "  AND nbKilometres > "+ nbKm + " \n" +
                    "group by a.idagence;"
            );

            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(resultSet.getInt("idagence"))
                        .append(" | ").append(resultSet.getInt("nbVehicule"));
                System.out.println(stringBuilder.toString());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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