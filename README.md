# Project

**Amera** is a cute and minimalistic e-commerce mobile app project. As part of our Object-Oriented Programming course, we wanted to apply the concepts we’ve learned by building something modern and practical. We chose to create a clothing store app that combines clean UI with basic backend logic, focusing on usability and simplicity.

### Features 

-   **User Management** – Register and manage user profiles.
    
-   **Product Catalog** – Browse a variety of cute clothing items.
    
-   **Shopping Cart** – Add and remove items from the cart.
    
-   **Favorites** – Save favorite products for future shopping.
    
-   **Order Management** – Place and track orders.

### Technologies Used

-   **Language**: Java
    
-   **Database**: PostgreSQL
    
-   **Development Tools**: Android Studio, Figma (for UI/UX design)

## Project Structure

This project is divided into the following key components:

-   `user.java` – Represents a user and their account info
    
-   `product.java` – Represents a product in the store
    
-   `cart.java` – Logic for the shopping cart
    
-   `order.java` – Order data structure
    
-   `userManagement.java`, `productManagement.java`, etc. – Handle operations for each class
    
-   `DatabaseConnection.java` – Sets up basic database interactions

## Our Team

We’re three second-year IT students passionate about building user-friendly tech:

-   👩‍💻 Frontend Developer Kristina – Focused on app design and UI
    
-   🧠 Backend Developer Leili – Handled logic and data handling
    
-   🔧 Integrator Nika – Connected all parts and ensured smooth flow

## UI/UX Design

You can view our app’s prototype on Figma here:  
🔗 Figma Design [here](https://www.figma.com/design/vb9AUuNbQMUEfKMXW6MMHo/Amera?m=auto&t=0kXZntR2XpLES0kA-6).

##
## How to Set Up the Project

### 1. Create a PostgreSQL Database

To create the database, run the following SQL command:

`CREATE DATABASE my_app_db;` 

### 2. Configure `application.properties` or `application.yml` in `src/main/resources`

Add the following properties to your `application.properties` file:

`spring.datasource.url=jdbc:postgresql://localhost:5432/my_app_db`
`spring.datasource.username=your_username`
`spring.datasource.password=your_password`

`spring.jpa.hibernate.ddl-auto=update`
`server.port=8081`

> 🔒 Replace `your_username` and `your_password` with your actual PostgreSQL credentials.

### 3. Build and Run the Server

Use the following commands to build the project and run the application:

`./gradlew build
java -jar build/libs/Amera-0.0.1-SNAPSHOT.jar`

## UML diagram
Check out Amera App UML diagram
(To view this diagram, open this file in StackEdit or paste it into [https://mermaid.live](https://mermaid.live))

![UML diagram](file:///C:/Users/present/OneDrive/%D0%98%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F/Screenshots/%D0%A1%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA%20%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202025-04-14%20034154.png)
```mermaid
classDiagram
    class User {
        - int id
        - String name
        - String email
        - String phone
        - String address
        - Cart cart
        + register(String name, String email, String phone, String address)
        + login(String email, String password)
        + addToCart(Product, int quantity)
        + placeOrder()
    }

    class Product {
        - int id
        - String name
        - String description
        - double price
        - String imageUrl
        - int stock
        - Category category
        + getDetails()
    }

    class Cart {
        - List<CartItem> items
        + addItem(Product, int quantity)
        + removeItem(Product)
        + calculateTotal()
        + checkout()
    }

    class CartItem {
        - Product product
        - int quantity
        + getSubtotal()
    }

    class Order {
        - int id
        - User customer
        - List<OrderItem> items
        - double totalAmount
        - String status
        - Payment payment
        - Shipment shipment
        + placeOrder()
        + cancelOrder()
        + trackOrder()
    }

    class OrderItem {
        - Product product
        - int quantity
        - double priceAtPurchase
    }

    class Payment {
        - int id
        - Order order
        - String paymentMethod
        - boolean isPaid
        - String transactionId
        + processPayment()
    }

    class Shipment {
        - int id
        - Order order
        - String status
        - String trackingNumber
        + updateStatus()
    }

    class Category {
        - int id
        - String name
        - List<Product> products
    }

    class Review {
        - int id
        - User author
        - Product product
        - int rating
        - String comment
        + submitReview()
    }

    class Admin {
        - int id
        - String name
        - String email
        + manageProducts()
        + manageOrders()
        + manageUsers()
    }

    class Main {
        + main(String[] args)
    }

    User "1" --> "1" Cart : owns
    User "1" --> "*" Order : places
    Order "1" --> "1" Payment : has
    Order "1" --> "1" Shipment : includes
    Cart "1" --> "*" CartItem : contains
    CartItem "1" --> "1" Product : refers
    Order "1" --> "*" OrderItem : contains
    OrderItem "1" --> "1" Product : refers
    Product "1" --> "*" Review : has
    Product "1" --> "1" Category : belongs to
    Admin "1" --> "*" Product : manages
    Admin "1" --> "*" Order : manages


<!--stackedit_data:
eyJoaXN0b3J5IjpbNTUyNzMyMjcxLC0xNzQ2Mjg2MDY5XX0=
-->