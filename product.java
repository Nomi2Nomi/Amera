public class product {
    private int id;
    private String name;
    private String category;
    private double price;
    private String description;
    private int quantity;
    private String imageUrl;

    //Constructor to initialize a new Product object
    public product( int id, String name, String category, double price, String description, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    //Returns the needed data
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    //Returns a string representation of the product.
    @Override  //this method overrides the default toString() method from the Object class.
    public String toString() {
        return  name + " " + category + " " + price + " " + description + " " + quantity + " " + imageUrl;
    }
}
