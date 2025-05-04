package com.example.ameramain.network;

import com.example.ameramain.models.Favourites;
import com.example.ameramain.models.LoginRequest;
import com.example.ameramain.models.LoginResponse;
import com.example.ameramain.models.ApiResponse;
import com.example.ameramain.models.Product;
import com.example.ameramain.models.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/v1/apps/new-user")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/v1/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("api/v1/apps/products") // пример endpoint'а: https://yourdomain.com/api/products
    Call<List<Product>> getAllProducts();

    @GET("/api/v1/apps/favourites/{userId}")
    Call<List<Favourites>> getFavourites(@Path("userId") Long userId);
}
