package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DBConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/examora";
    private static final String user = "postgres";
    private static final String password = "Nemo-264852";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url , user, password);

    }
}
