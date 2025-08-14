package com.example.mobileApp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitApiService {

    @GET
    Call<Map<String, Object>> doGet(@Url String endpointUrl, @QueryMap Map<String, String> params);

}
