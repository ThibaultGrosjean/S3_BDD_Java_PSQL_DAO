package sql;//package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

    private static String serveur = "localhost";
    private static String baseName = "bdd_ysmara";
    private static String user = "ysmara";
    private static String password = "0407";

    private static Connection connection;

    public static Connection getInstance() {

        if (connection == null) {
            String url = "jdbc:postgresql://" + serveur + "/" + baseName;
            try {
                connection = DriverManager.getConnection(url, user, password );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}