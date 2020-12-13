package dao.jdbc;
import dao.exception.DaoException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class ContratDaoImpl extends JdbcDao {

    private ClientDaoImpl clientDao;
    private VehiculeDaoImpl vehiculeDao;
    private AgenceDaoImpl agenceDao ;

    public ContratDaoImpl(Connection connection) {
        super(connection);
        clientDao = new ClientDaoImpl(connection);
        vehiculeDao = new VehiculeDaoImpl(connection);
        agenceDao = new AgenceDaoImpl(connection);
    }

    @Override public Collection<Entity> findAll() throws DaoException {
        Collection<Entity> contrats = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrat");

            while (resultSet.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(resultSet.getInt("idContrat"));
                contrat.setDateDeRetrait(resultSet.getDate("dateDeRetrait"));
                contrat.setDateDeRetour(resultSet.getDate("dateDeRetour"));
                contrat.setKmRetrait(resultSet.getInt("kmRetrait"));
                contrat.setKmRetour(resultSet.getInt("kmRetour"));
                contrat.setClient((Client) clientDao.findById(resultSet.getInt("idClient")));
                contrat.setVehicule((Vehicule) vehiculeDao.findById(resultSet.getInt("immatriculation")));
                contrat.setAgence((Agence) agenceDao.findById(resultSet.getInt("idAgenceDeRetour")));
                contrats.add(contrat);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return contrats;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        Contrat contrat = new Contrat();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contrat WHERE idContrat ="+ id +";");

            while (resultSet.next()) {
                contrat.setId(resultSet.getInt("idContrat"));
                contrat.setDateDeRetrait(resultSet.getDate("dateDeRetrait"));
                contrat.setDateDeRetour(resultSet.getDate("dateDeRetour"));
                contrat.setKmRetrait(resultSet.getInt("kmRetrait"));
                contrat.setKmRetour(resultSet.getInt("kmRetour"));
                contrat.setClient((Client) clientDao.findById(resultSet.getInt("idClient")));
                contrat.setVehicule((Vehicule) vehiculeDao.findById(resultSet.getInt("immatriculation")));
                contrat.setAgence((Agence) agenceDao.findById(resultSet.getInt("idAgenceDeRetour")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return contrat;
    }

    @Override
    public void create(Entity entity) throws DaoException {
        Contrat contrat = (Contrat) entity;

        PreparedStatement stmt= null;
        String sqlReq = "INSERT INTO CONTRAT(dateDeRetrait, dateDeRetour, kmRetrait, kmRetour, idClient, immatriculation, idAgenceDeRetour) VALUES ((?),(?), ?,?,?,?,?);";

        try {
            stmt = connection.prepareStatement(sqlReq,Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, (Date) contrat.getDateDeRetrait());
            stmt.setDate(2, (Date) contrat.getDateDeRetour());
            stmt.setInt(3, contrat.getKmRetrait());
            stmt.setInt(4, contrat.getKmRetour());
            stmt.setInt(5, contrat.getClient().getId());
            stmt.setInt(6, contrat.getVehicule().getImmatriculation());
            stmt.setInt(7, contrat.getAgence().getId());

            int res = stmt.executeUpdate();


            if (res > 0) {
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        contrat.setId((int) resultSet.getLong(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
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
        Contrat contrat = (Contrat) entity;

        PreparedStatement stmt= null;
        String sqlReq = "UPDATE CONTRAT set dateDeRetrait = (?), dateDeRetour = (?), kmRetrait = (?), kmRetour = (?) , idClient = (?), immatriculation = (?) , idAgenceDeRetour = (?) WHERE idContrat = (?) ;\n";

        try {
            stmt = connection.prepareStatement(sqlReq);
            stmt.setDate(1, (Date) contrat.getDateDeRetrait());
            stmt.setDate(2, (Date) contrat.getDateDeRetour());
            stmt.setInt(3, contrat.getKmRetrait());
            stmt.setInt(4, contrat.getKmRetour());
            stmt.setInt(5, contrat.getClient().getId());
            stmt.setInt(6, contrat.getVehicule().getImmatriculation());
            stmt.setInt(7, contrat.getAgence().getId());
            stmt.setInt(8, contrat.getId());

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
        Contrat contrat = (Contrat) entity;

        PreparedStatement stmt= null;

        String sqlReq = "DELETE FROM CONTRAT WHERE idContrat = ? ;";

        try {

            stmt = connection.prepareStatement(sqlReq);
            stmt.setInt(1, contrat.getId());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Ligne supprimé");
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getLocalizedMessage());
        }
    }

    public Contrat locationVehicule(int clientId, int vehiculeId, int agenceId, int jour) throws DaoException {
        Agence agence = (Agence) agenceDao.findById(agenceId) ;
        Vehicule vehicule = (Vehicule) vehiculeDao.findById(vehiculeId);
        Client client = (Client) clientDao.findById(clientId);

        if (vehicule.getAgence().getId() == agence.getId()) {
            throw new DaoException("L'agence de départ doit etre différente de celle de retour");
        }

        java.util.Date dateRetrait = new java.util.Date() ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(dateRetrait);
        c.add(Calendar.DATE, jour);
        java.util.Date dateRetour  = c.getTime() ;

        Contrat contrat = new Contrat(
                0,
                new Date(dateRetrait.getTime()),
                new Date(dateRetour.getTime()),
                vehicule.getNbKilometres(),
                0,
                client,
                vehicule,
                agence
        );

        vehicule.setEtat(false);

        create(contrat);
        vehiculeDao.update(vehicule);

        return contrat ;
    }

    public void retourVehicule(int vehiculeId , int kmFait, int contratId) throws DaoException{
        Vehicule vehicule = (Vehicule) vehiculeDao.findById(vehiculeId);
        Contrat contrat = (Contrat) findById(contratId) ;
        int kmFinal = vehicule.getNbKilometres()+kmFait;

        vehicule.setNbKilometres(kmFinal);
        vehicule.setEtat(true);

        contrat.setKmRetour(kmFinal);

        vehiculeDao.update(vehicule);
        update(contrat);
    }

}