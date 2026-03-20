package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection con;
    private Statement stmt;

    private static DatabaseConnection instance = null;

    private DatabaseConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "system",
                    "Slim123"
            );
            stmt = con.createStatement();
            System.out.println("Connected Successfully");
        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }

    // only one connection for the whole app
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }

    public void closeConnection() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
            System.out.println("Connection Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}