import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class favorites {
    private List<favorites> favorites;
    public favorites() {  //Constructor for the cart class
        this.favorites = new ArrayList<>();
    }
    //add product to the users cart
    public static void addProductToFavorites(int productId, int userId) {
        String sql = "INSERT INTO favorite_products (user_id, product_id) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            System.out.println("Product added to favorites.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Displays all products currently in the cart
    public static void showFavorites(int userId) {
        String sql = "SELECT p.product_id, p.name, p.price FROM favorite_products c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.isBeforeFirst()) { // Checks if ResultSet is empty
                System.out.println("No products added to favorites.");
            } else {
                System.out.println("Your Favorites:");
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

    public static void removeFromFavorites(int productId, int userId) {
        String sql = "DELETE FROM favorite_products VALUES user_id=? AND product_id= ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            System.out.println("Product removed from favorites.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
