import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManagement {
    public static void addUser(user user){
        // SQL query for inserting a new user into the "users" table.
        // The query uses placeholders (?) for the values to be inserted.
        String InsertUser = "INSERT INTO users (name, surname, email, phone) VALUES (?,?,?,?)";
        // Try-with-resources to automatically close the Connection and PreparedStatement.
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(InsertUser)){
            stmt.setString(1,user.getName());
            stmt.setString(2,user.getSurname());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getPhone());
            // Execute the SQL update to insert the new user into the database.
            stmt.executeUpdate();
            System.out.println("User added successfully");
        }
        catch(SQLException e){
            e.printStackTrace();  //if an SQL exception occurs, print the stack trace
        }
    }
}
