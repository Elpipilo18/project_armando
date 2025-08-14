package com.example.mobileApp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String BASE_URL = "https://localhost:8080/"; // Usa la raíz, aunque pasaremos endpoints completos

    private static RetrofitApiService retrofitApiService;

    public static RetrofitApiService getInstance() {
        if (retrofitApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofitApiService = retrofit.create(RetrofitApiService.class);
        }
        return retrofitApiService;
    }

}
