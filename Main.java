public class Main {
    public static void main(String[] args) {
        product shoes1 = new product(001, "Mary Janes blue", "Shoes", "36.50$", "New Winter Collection", "url");
        product shoes2 = new product(002, "Mary Janes black", "Shoes", "38.50$", "New Winter Collection", "url2");
        product shoes3 = new product(003, "Mary Janes red", "Shoes", "35.50$", "New Winter Collection", "url3");

        user user = new user(001,"Amelia","Johanes","amelia@gmail.com","6555571035");

        System.out.println("Product Gallery");
        System.out.println(shoes1);
        System.out.println(shoes2);
        System.out.println(shoes3);

        user.getCart().addProduct(shoes2);
        user.getCart().addProduct(shoes3);
        user.getCart().showCart();
        user.getCart().removeProduct(002);
        user.getCart().showCart();


    }
}
