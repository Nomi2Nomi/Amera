public class user {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private cart cart;

    //Constructor to initialize a new User object
    public user(int id, String name, String surname, String email, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.cart = new cart();
    }

    //Returns the needed data
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public cart getCart(){
        return cart;
    }

}
