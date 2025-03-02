import java.sql.*;  // Импорт всех классов из пакета java.sql, необходимых для работы с DB
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class OrderManagement {
    public static int CreateOrder(order order) {
        String InsertOrder = "INSERT INTO orders (user_id, total_price, payment_method, payment_status, delivery_address, delivery_status) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        String InsertOrderItems = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement Orderstmt = con.prepareStatement(InsertOrder);
             PreparedStatement OrderItemsstmt = con.prepareStatement(InsertOrderItems)) {

            // Вставляем заказ
            Orderstmt.setInt(1, order.getUser_id());
            Orderstmt.setDouble(2, order.getTotalPrice());
            Orderstmt.setString(3, order.getPayment_method());
            Orderstmt.setString(4, order.getPayment_status());
            Orderstmt.setString(5, order.getDelivery_address());
            Orderstmt.setString(6, order.getDelivery_status());

            // Получаем order_id через RETURNING
            ResultSet rs = Orderstmt.executeQuery();
            if (rs.next()) {
                int order_id = rs.getInt(1);  // PostgreSQL вернёт id из RETURNING

                // Вставляем товары заказа
                for (OrderItems item : order.getItems()) {
                    OrderItemsstmt.setInt(1, order_id);
                    OrderItemsstmt.setInt(2, item.getProduct_id());
                    OrderItemsstmt.setInt(3, item.getQuantity());
                    OrderItemsstmt.setDouble(4, item.getPrice());
                    OrderItemsstmt.executeUpdate();
                }
                return order_id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static order getOrder(int order_id) {
        // SQL query to select an order based on the given order_id
        String returnOrder = "SELECT * FROM orders WHERE id=?";
        order orderObj = null; // Initialize order object to null, will be assigned if data exists

        try (Connection con = DatabaseConnection.getConnection(); // Establish database connection
             PreparedStatement stmt = con.prepareStatement(returnOrder)) { // Prepare SQL statement

            stmt.setInt(1, order_id); // Set the order_id in the query

            try (ResultSet rs = stmt.executeQuery()) { // Execute the query and get the result set
                if (rs.next()) { // Check if a matching order was found
                    List<OrderItems> items = OrderItemsManagement.getItems(order_id); // Retrieve associated order items

                    // Create an order object using the retrieved data
                    orderObj = new order(
                            rs.getInt("user_id"),
                            rs.getDouble("total_price"),
                            items,
                            rs.getString("payment_method"),
                            rs.getString("payment_status"),
                            rs.getString("delivery_address"),
                            rs.getString("delivery_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details if an exception occurs
        }
        return orderObj; // Return the retrieved order object, or null if not found
    }

    public static void ChangePaymentStatus(int order_id, String payment_status) {
        // SQL query to update the payment status of an order
        String updateStatus = "UPDATE orders SET payment_status = ? WHERE id = ?";

        // Try-with-resources ensures the connection and statement are automatically closed
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(updateStatus)) {

            stmt.setString(1, payment_status);
            stmt.setInt(2, order_id);

            stmt.executeUpdate(); // Execute the update query
            System.out.println("Status updated successfully"); // Print confirmation message
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details if an exception occurs
        }
    }

    public static void ChangeDeliveryStatus(int order_id, String delivery_status) {
        // SQL query to update the delivery status of an order
        String updateDeliveryStatus = "UPDATE orders SET delivery_status = ? WHERE id = ?";

        // Try-with-resources ensures the connection and statement are automatically closed
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(updateDeliveryStatus)) {

            stmt.setString(1, delivery_status);
            stmt.setInt(2, order_id);

            stmt.executeUpdate(); // Execute the update query
            System.out.println("Status updated successfully"); // Print confirmation message
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details if an exception occurs
        }
    }
}

