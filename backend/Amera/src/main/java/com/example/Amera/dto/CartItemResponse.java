// CartItemResponse.java
package com.example.Amera.dto;

import com.example.Amera.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Product product;
    private int quantity;
}
