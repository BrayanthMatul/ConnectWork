package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {


    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/horizonte_sin_limites";
    private static final String USER = "user_proyecto_final";
    private static final String PASSWORD = "12345";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC", e);
        }
    }

    private ConexionDB() {
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
    }
}

