import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/testDB";
    private static final String USER = "root";
    private static final String PASS = "Singh@789";

    public Connection connectDB() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database");
            e.printStackTrace();
            return null; // Return null or handle the error in a different way as per your requirements
        }
    }
}
