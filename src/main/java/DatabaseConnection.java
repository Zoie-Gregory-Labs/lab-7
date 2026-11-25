import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String USER = "root";
    private static final String PASSWORD = "example";
    private static final String DEFAULT_HOST = "db:3306";

    public static Connection getConnection() throws SQLException {

        String host = System.getProperty("db.host");

        if (host == null || host.trim().isEmpty()) {
            host = System.getenv("DB_HOST");
        }

        if (host == null || host.trim().isEmpty()) {
            host = DEFAULT_HOST;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Could not load MySQL driver", e);
        }

        String url = "jdbc:mysql://" + host
                + "/employees?allowPublicKeyRetrieval=true&useSSL=false";

        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}