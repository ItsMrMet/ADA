package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/bd_peticionsclients?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "123456";
    public static Connection conn = null;

    public static Connection conectarMySQL() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Se ha conectado correctamente a la base de datos!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se ha podido conectar a la base de datos.");
        }
        return conn;
    }

    public static void desconectarMySQL() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Se ha desconectado de la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo desconectar de la base de datos.");
        }
    }

}
