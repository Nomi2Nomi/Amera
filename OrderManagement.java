import java.sql.*;  // Импорт всех классов из пакета java.sql, необходимых для работы с DB
import java.util.List;

public class OrderManagement {
    public static int CreateOrder(order order) {
        String InsertOrder = "INSERT INTO orders (user_id, total_price) VALUES (?, ?) RETURNING id";
        String InsertOrderItems = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement Orderstmt = con.prepareStatement(InsertOrder);
             PreparedStatement OrderItemsstmt = con.prepareStatement(InsertOrderItems)) {

            // Вставляем заказ
            Orderstmt.setInt(1, order.getUser_id());
            Orderstmt.setDouble(2, order.getTotalPrice());

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
}
