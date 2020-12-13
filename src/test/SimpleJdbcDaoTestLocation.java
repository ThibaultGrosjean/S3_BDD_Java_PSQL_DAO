package test;

import dao.exception.DaoException;
import dao.jdbc.ContratDaoImpl;
import dao.jdbc.FactureDaoImpl;
import dao.jdbc.VehiculeDaoImpl;
import model.Contrat;
import sql.PostgresConnection;

import java.io.IOException;
import java.sql.Connection;

public class SimpleJdbcDaoTestLocation {
    private ContratDaoImpl contratDao ;
    private FactureDaoImpl factureDao ;
    private VehiculeDaoImpl vehiculeDao ;
    private Connection connection;


    public static void main(String[] args) throws DaoException {
        new SimpleJdbcDaoTestLocation().test();
    }

    public void test() throws DaoException {
        connection = PostgresConnection.getInstance();
        contratDao = new ContratDaoImpl(connection);
        factureDao = new FactureDaoImpl(connection);
        vehiculeDao = new VehiculeDaoImpl(connection);

        System.out.println("\n***** Location d'une voiture : ");
        Contrat contrat = testLocationVoiture(1,1,3,7);

        try {
            System.in.read();
        } catch (IOException ignored) {}


        System.out.println("\n***** Retour d'une voiture : ");
        testRetourVoiture(1,5700, contrat.getId()) ;

        try {
            System.in.read();
        } catch (IOException ignored) {}

        System.out.println("\n***** Creation Facture : ");
        testFacture(contrat.getId()) ;
    }

    private void testFacture(int id) {
        try {
            System.out.println(factureDao.etablitFacture(id));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private Contrat testLocationVoiture(int clientId, int vehiculeId, int agenceId, int jour) {
        Contrat contrat = null ;
        try {
            contrat  = contratDao.locationVehicule(clientId, vehiculeId, agenceId, jour);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return contrat ;
    }

    private void testRetourVoiture(int vehiculeId,  int kmFait, int contratId) {
        try {
            contratDao.retourVehicule(vehiculeId, kmFait, contratId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


}
