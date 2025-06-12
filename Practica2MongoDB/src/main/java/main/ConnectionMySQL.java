package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {

    public static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/weatherdata_tv24?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "123456";
    public static Connection mySQLConnection = null;

    public static Connection connectMySQL() {
        try {
            Class.forName(driver);
            mySQLConnection = DriverManager.getConnection(url, username, password);
            System.out.println("S'ha connectat correctament a la base de dades!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No s'ha pogut connectar a la base de dades.");
        }
        return mySQLConnection;
    }

    public static void desconnectMySQL() {
        try {
            if (mySQLConnection != null) {
                mySQLConnection.close();
                System.out.println("S'ha desconnectat de la base de dades.");
            }
        } catch (SQLException e) {
            System.out.println("No s'ha pogut desconnectar de la base de dades.");
        }
    }

}
