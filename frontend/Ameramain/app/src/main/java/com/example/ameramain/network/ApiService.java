package com.example.ameramain.network;

import com.example.ameramain.models.CartProduct;
import com.example.ameramain.models.CartRequest;
import com.example.ameramain.models.FavouriteRequest;
import com.example.ameramain.models.Favourites;
import com.example.ameramain.models.LoginRequest;
import com.example.ameramain.models.LoginResponse;
import com.example.ameramain.models.ApiResponse;
import com.example.ameramain.models.Product;
import com.example.ameramain.models.RegisterRequest;
import com.example.ameramain.models.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    @GET("api/v1/apps/favourites/products/{userId}")
    Call<List<Product>> getFavouriteProducts(@Path("userId") Long userId);

    @POST("api/v1/apps/favourites/add")
    Call<Void> addToFavourites(@Body FavouriteRequest request);

    @HTTP(method = "DELETE", path = "api/v1/apps/favourites/remove", hasBody = true)
    Call<Void> removeFromFavourites(@Body FavouriteRequest request);

    @GET("api/v1/apps/cart/products/{userId}")
    Call<List<CartProduct>> getCartProducts(@Path("userId") Long userId);


    @POST("api/v1/apps/cart/add")
    Call<Void> addToCart(@Body CartRequest request);

    @HTTP(method = "DELETE", path = "api/v1/apps/cart/remove", hasBody = true)
    Call<Void> removeFromCart(@Body CartRequest request);

    @DELETE("api/v1/apps/cart/clear/{userId}")
    Call<Void> clearCart(@Path("userId") Long userId);

    @GET("api/v1/apps/cart/count/{userId}")
    Call<Integer> getCartCount(@Path("userId") Long userId);

    @GET("api/users/{id}")
    Call<UserDto> getUserById(@Path("id") Long id);

}
