package com.example.celularesapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Celulares")
    Call<Celular> guardarCelular(@Body Celular celular);

}