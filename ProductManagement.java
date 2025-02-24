import java.sql.SQLException;  // Handles SQL-related exceptions
import java.sql.Connection;  // Manages connection to the database
import java.sql.PreparedStatement;  // Prepares parameterized SQL queries
import java.sql.ResultSet;   // Processes results returned by SQL queries
// Importing utility classes for handling collections
import java.util.List;
import java.util.ArrayList;

public class ProductManagement {
    public static List<product> getAllProducts(){
        // Create an ArrayList to store the products from the database.
        List<product> products = new ArrayList<>();
        String dbQuery = "SELECT * FROM products";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(dbQuery);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                // Create a new product object using data from the current row.
                product product = new product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("imageUrl")
                );
                // Add the created product to the products list.
                products.add(product);
            }
        }
        catch(SQLException e){
            e.printStackTrace();  //method prints detailed error information when an exception is caught.
        }
        return products;
    }
}
