import java.sql.Connection;  // Represents a connection to the database.
import java.sql.DriverManager;  // Provides methods to obtain a connection
import java.sql.SQLException;  // Exception thrown when a database access error occurs.

public class DatabaseConnection { //establishing a connection with the database.
        // URL for connecting to the PostgreSQL database.
        private static final String URL = "jdbc:postgresql://localhost:5432/javasql";
        private static final String USER = "postgres";
        private static final String PASSWORD = "Hellokitty143";

        // Use DriverManager to establish a connection with the specified data.
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
}
