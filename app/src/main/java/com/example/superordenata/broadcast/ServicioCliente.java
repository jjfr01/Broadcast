package com.example.superordenata.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioCliente extends Service {

    public ServicioCliente() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String state = intent.getStringExtra("estado");
        String phone = intent.getStringExtra("numero");
        String fecha = intent.getStringExtra("fecha");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Registro nuevo = new Registro(0, state, phone, fecha);

        postMethod(service, nuevo);

        return START_REDELIVER_INTENT;
    }

    private void postMethod(APIService service, Registro nuevo) {
        Call<Registro> registroCall = service.postRegistro(nuevo);

        registroCall.enqueue(new Callback<Registro>() {
            @Override
            public void onResponse(Call<Registro> call, Response<Registro> response) {
                Log.d("Prueba", "Registrado");
            }

            @Override
            public void onFailure(Call<Registro> call, Throwable t) {
                Log.d("Prueba", "Error: " + t.getMessage());
            }
        });

    }
}
