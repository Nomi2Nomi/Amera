package com.example.Amera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@Builder // Если нужен паттерн Builder
@AllArgsConstructor  // Генерирует конструктор с параметрами
@NoArgsConstructor   // Генерирует конструктор без параметров
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    @Column(name = "product_id") // Название столбца в базе данных
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    @Min(0)
    private Double price;

    @Column(unique = true)
    private String description;

    private String imageurl;

    @Min(0) // Ограничение, что количество не может быть отрицательным
    private Integer quantity;  // Заменено на Integer, чтобы можно было хранить null
}
