import java.util.List;

public class order {
    private int order_id;
    private int user_id;
    private double total_price;

    private List<OrderItems> items;
    //Constructs a new order with the specified user ID, total price, and list of order items.
    public order( int user_id, double totalPrice, List<OrderItems> items) {
        this.user_id = user_id;
        this.total_price = totalPrice;
        this.items = items;
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

}
