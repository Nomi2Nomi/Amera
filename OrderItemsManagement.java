import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsManagement {
    public static List<OrderItems> getItems(int order_id) {
        List<OrderItems> orderItems = new ArrayList<>(); // List to store order items
        String dbQuery = "SELECT * FROM order_items WHERE order_id = ?"; // SQL query to fetch order items

        try (Connection con = DatabaseConnection.getConnection(); // Establish database connection
             PreparedStatement stmt = con.prepareStatement(dbQuery)) {

            stmt.setInt(1, order_id); // Set order_id in the query

            try (ResultSet rs = stmt.executeQuery()) { // Execute query and retrieve results
                while (rs.next()) { // Iterate through result set
                    // Create an OrderItems object with retrieved data
                    OrderItems orderItem = new OrderItems(
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    orderItems.add(orderItem); // Add item to the list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception if any error occurs
        }

        return orderItems;
    }
}


