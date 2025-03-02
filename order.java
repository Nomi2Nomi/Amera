import java.util.List;

public class order {
    private int order_id;
    private int user_id;
    private double total_price;
    private String payment_method;
    private String payment_status;
    private String delivery_address;
    private String delivery_status;
    private List<OrderItems> items;


    //Constructs a new order with the specified data
    public order( int user_id, double totalPrice, List<OrderItems> items, String payment_method, String payment_status, String delivery_address, String delivery_status ) {
        this.user_id = user_id;
        this.total_price = totalPrice;
        this.items = items;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
        this.delivery_address = delivery_address;
        this.delivery_status = delivery_status;
    }
    //Returns the needed data associated with this order.
    public int getUser_id() {
        return user_id;
    }
    public double getTotalPrice() {
        return total_price;
    }
    public List<OrderItems> getItems() {
        return items;
    }
    public String getPayment_method() {
        return payment_method;
    }
    public String getPayment_status() {
        return payment_status;
    }
    public String getDelivery_address() {
        return delivery_address;
    }
    public String getDelivery_status() {
            return delivery_status;
    }

    @Override  //this method overrides the default toString() method from the Object class.
    public String toString() {
        return user_id + ": " + total_price + " " + items + " " + payment_method + " " + payment_status + " " + delivery_address + " " + delivery_status;
    }
}
