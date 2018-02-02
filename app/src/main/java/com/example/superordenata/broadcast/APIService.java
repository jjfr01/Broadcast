package com.example.superordenata.broadcast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("registro")
    Call<ArrayList<Registro>> getRegistro();

    @POST("registro")
    Call<Registro> postRegistro(@Body Registro nuevo);

}
