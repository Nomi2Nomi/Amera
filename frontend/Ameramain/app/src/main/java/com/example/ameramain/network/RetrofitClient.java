package com.example.ameramain.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static ApiService apiService;
    private static final String BASE_URL = "http://88.198.10.91:8081/";

    public static void init(Context context) {
        if (retrofit == null) {
            // 🔍 Логирование HTTP-запросов
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Включает тело запроса/ответа

            // ⛓ Интерцептор для добавления токена авторизации
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    SharedPreferences preferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    String token = preferences.getString("auth_token", null);
                    Log.d("AuthInterceptor", "Token = " + token);

                    Request.Builder requestBuilder = original.newBuilder();
                    if (token != null) {
                        requestBuilder.addHeader("Authorization", "Bearer " + token);
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };

            // 🛠 Сборка OkHttpClient с логированием и авторизацией
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // ⚙️ Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            Log.e("RetrofitClient", "Retrofit not initialized!");
            throw new IllegalStateException("RetrofitClient is not initialized. Call RetrofitClient.init(context) first.");
        }
        return apiService;
    }
}
