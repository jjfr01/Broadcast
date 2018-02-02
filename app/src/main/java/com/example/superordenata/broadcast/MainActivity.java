package com.example.superordenata.broadcast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private void init(){
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        getMethod(service);
    }

    private void getMethod(APIService service) {
        Call<ArrayList<Registro>> registroCall = service.getRegistro();

        registroCall.enqueue(new Callback<ArrayList<Registro>>() {
            @Override
            public void onResponse(Call<ArrayList<Registro>> call, Response<ArrayList<Registro>> response) {
                ArrayList<Registro> array = response.body();

                String result = "";

                for (Registro registro : array) {
                    result += "Id: " + registro.getId() + " -- Tipo: " + registro.getTipo() + " -- Fecha/Hora: " + registro.getFechahora() + " -- Numero: " + registro.getNumero() + "\n";
                }
                textView.setText(result);
            }

            @Override
            public void onFailure(Call<ArrayList<Registro>> call, Throwable t) {
                Log.d("Prueba", "Error: " + t.getMessage());
            }
        });
    }

}
