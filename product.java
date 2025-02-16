public class product {
    private int id;
    private String name;
    private String category;
    private String price;
    private String description;
    private String imageUrl;

    public product(int id, String name, String category, String price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return id + ": " + name + " " + category + " " + price + " " + description + " " + imageUrl;
    }
}
