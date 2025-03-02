import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class cart {
    private List<product> products;
    public cart() {  //Constructor for the cart class
        this.products = new ArrayList<>();
    }
    //add product to the users cart
    public static void addProductToCart(int productId, int userId) {
        String sql = "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            System.out.println("Product added to cart.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Displays all products currently in the cart
    public static void showCart(int userId) {
        String sql = "SELECT p.product_id, p.name, p.price FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.isBeforeFirst()) { // Checks if ResultSet is empty
                System.out.println("No products added to cart.");
            } else {
                System.out.println("Your Cart:");
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    System.out.println("ID: " + productId + ", Name: " + name + ", Price: $" + price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Removes a product from the cart based on the product ID.
    public void removeProduct(int productId){
        products.removeIf(product -> product.getId() == productId);
        System.out.println(productId + " removed from cart.");
    }
}
