import java.util.List;
import java.util.ArrayList;
public class cart {
    private List<product> products;
    public cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(product Product){
        products.add(Product);
        System.out.println(Product.getName() + " added to cart.");
    }

    public void showCart(){
        if(products.size()==0) {
            System.out.println("No products added to cart.");
        }
        else {
                System.out.println("Your Cart: ");
                for(product p : products) {
                    System.out.println(p);
                }
        }
    }

    public void removeProduct(int productId){
        products.removeIf(product -> product.getId() == productId);
        System.out.println(productId + " removed from cart.");
    }
}
