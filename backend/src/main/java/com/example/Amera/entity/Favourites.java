package com.example.Amera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "favourites")
@Builder // Если нужен паттерн Builder
@AllArgsConstructor  // Генерирует конструктор с параметрами
@NoArgsConstructor   // Генерирует конструктор без параметров
public class Favourites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
