package com.example.celularesapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CelularesActivity extends AppCompatActivity implements SensorEventListener {

    EditText txtMarca;
    EditText txtEmailUsuario;
    EditText txtPulgadas;
    EditText txtRam;

    Button btnGuardar;

    SensorManager sensorManager;
    Sensor acelerometro;
    Sensor luz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celulares);

        txtMarca = findViewById(R.id.txtMarca);
        txtEmailUsuario = findViewById(R.id.txtEmailUsuario);
        txtPulgadas = findViewById(R.id.txtPulgadas);
        txtRam = findViewById(R.id.txtRam);

        btnGuardar = findViewById(R.id.btnGuardar);

        // Sensores
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        acelerometro =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        luz =
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (acelerometro != null) {
            sensorManager.registerListener(
                    this,
                    acelerometro,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

        if (luz != null) {
            sensorManager.registerListener(
                    this,
                    luz,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

        btnGuardar.setOnClickListener(v -> guardarCelular());
    }

    private void guardarCelular() {

        String marca = txtMarca.getText().toString();
        String email = txtEmailUsuario.getText().toString();
        String pulgadas = txtPulgadas.getText().toString();
        String ram = txtRam.getText().toString();

        Celular celular = new Celular(
                marca,
                email,
                pulgadas,
                ram
        );

        ApiService apiService =
                RetrofitClient.getRetrofit()
                        .create(ApiService.class);

        Call<Celular> call =
                apiService.guardarCelular(celular);

        call.enqueue(new Callback<Celular>() {

            @Override
            public void onResponse(Call<Celular> call,
                                   Response<Celular> response) {

                Toast.makeText(
                        CelularesActivity.this,
                        "Celular guardado correctamente",
                        Toast.LENGTH_LONG
                ).show();
            }

            @Override
            public void onFailure(Call<Celular> call,
                                  Throwable t) {

                Toast.makeText(
                        CelularesActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];

            if (Math.abs(x) > 8) {

                Toast.makeText(
                        this,
                        "Acelerómetro detectó movimiento",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            float valorLuz = event.values[0];

            if (valorLuz > 100) {

                Toast.makeText(
                        this,
                        "Sensor de luz activo",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}