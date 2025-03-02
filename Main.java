//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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
        user user = new user("Anna","White","anna4life@gmail.com","6850713354");  // Create a new user
        UserManagement.addUser(user);  // Add the new user to the database using the UserManagement class.
        //add items to cart
        cart.addProductToCart(5,4);
        cart.addProductToCart(6,4);
        // Display the current contents of the user's cart.
        cart.showCart(4);
        // Create order items:
        OrderItems item3 = new OrderItems(1, 2, 75000.50);
        OrderItems item4 = new OrderItems(3, 2, 35000.00);

        // Create an order
        order order = new order(4, 220001, Arrays.asList(item3, item4), "credit_card", "paid", "Kazakhstan, Almaty, St.Satbayev 20, 050000", "on the way" );

        // Call the OrderManagement.CreateOrder method to save the order and get the generated order ID.
        int orderId = OrderManagement.CreateOrder(order);

        // Check if the order was created successfully

        order sample = OrderManagement.getOrder(1);
        System.out.println(sample);
        OrderManagement.ChangeDeliveryStatus(1,"delivered");
        OrderManagement.ChangePaymentStatus(1,"paid");
        order sample1 = OrderManagement.getOrder(1);
        System.out.println(sample1);
    }
}