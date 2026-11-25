import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String USER = "root";
    private static final String PASSWORD = "example";
    private static final String DEFAULT_HOST = "localhost:33060";

    public static Connection getConnection() throws SQLException {
        String host = System.getProperty("db.host");

        if (host == null || host.trim().isEmpty()) {
            host = System.getenv("DB_HOST");
        }
        if (host == null || host.trim().isEmpty()) {
            host = DEFAULT_HOST;
        }

        int delay = 30000;
        try {
            delay = Integer.parseInt(System.getProperty("db.delay", "30000"));
        } catch (NumberFormatException ignored) {
        }

        return connect(host, delay);
    }

    public static Connection connect(String location, int delay) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Could not load MySQL driver", e);
        }

        String url = "jdbc:mysql://" + location
                + "/employees?allowPublicKeyRetrieval=true&useSSL=false";

        SQLException lastError = null;

        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                return DriverManager.getConnection(url, USER, PASSWORD);
            } catch (SQLException e) {
                lastError = e;
                System.out.println("Database connection attempt " + attempt + " failed.");

                if (attempt < 5) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        throw new SQLException("Database connection interrupted", interrupted);
                    }
                }
            }
        }

        throw new SQLException("Unable to connect to database after 5 attempts", lastError);
    }
}
