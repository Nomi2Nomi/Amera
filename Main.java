import java.util.Arrays;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // Retrieve the list of products from the database using the ProductManagement class.
        List<product> products = ProductManagement.getAllProducts();
        System.out.println("Product Gallery");

        // Loop through each product in the list and print its details.
        for(product product : products){
            System.out.println(product);
        }
        user user = new user(002,"Kate","Black","katie@gmail.com","7856932456");  // Create a new user
        UserManagement.addUser(user);  // Add the new user to the database using the UserManagement class.

        // Create order items:
        OrderItems item1 = new OrderItems(1, 2, 75000.50);
        OrderItems item2 = new OrderItems(2, 1, 35000.00);

        // Create an order
        order order = new order(1, 185000.50, Arrays.asList(item1, item2));

        // Call the OrderManagement.CreateOrder method to save the order and get the generated order ID.
        int orderId = OrderManagement.CreateOrder(order);

        // Check if the order was created successfully
        if (orderId != -1) {
            System.out.println("Заказ успешно создан! ID: " + orderId);
        } else {
            System.out.println("Ошибка при создании заказа.");
        }

        // Display the current contents of the user's cart.
        user.getCart().showCart();
    }
}
