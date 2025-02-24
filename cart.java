import java.util.List;
import java.util.ArrayList;
public class cart {
    private List<product> products;
    public cart() {  //Constructor for the cart class
        this.products = new ArrayList<>();
    }

    public void addProduct(product Product){
        // Add the provided product to the list of products.
        products.add(Product);
        System.out.println(Product.getName() + " added to cart.");
    }

    //Displays all products currently in the cart
    public void showCart(){
        // Check if the cart is empty.
        if(products.size()==0) {
            System.out.println("No products added to cart.");
        }
        else {
                System.out.println("Your Cart: ");
            // Iterate through each product in the list and print its details.
                for(product p : products) {
                    System.out.println(p);
                }
        }
    }

    //Removes a product from the cart based on the product ID.
    public void removeProduct(int productId){
        products.removeIf(product -> product.getId() == productId);
        System.out.println(productId + " removed from cart.");
    }
}
