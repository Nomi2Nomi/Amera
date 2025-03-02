public class OrderItems {
    private int product_id;
    private int quantity;
    private double price;

    //OrderItems class constructor
    public OrderItems( int product_id, int quantity, double price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }
    //getters
    public int getProduct_id() {
        return product_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }

    @Override  //this method overrides the default toString() method from the Object class.
    public String toString() {
        return product_id + ": " + quantity;
    }
}
