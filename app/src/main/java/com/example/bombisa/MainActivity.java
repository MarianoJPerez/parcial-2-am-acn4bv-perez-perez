package com.example.bombisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bombisa.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pedirButton = findViewById(R.id.pedidos);
        pedirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPanSelectionActivity();
            }
        });

        Button verPedidosButton = findViewById(R.id.button3);
        verPedidosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPedidos();
            }
        });

        Button botonCerrarSesion = findViewById(R.id.button_logout);
        botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        sharedPreferences = getSharedPreferences("PanSelections", MODE_PRIVATE);
    }

    private void abrirPanSelectionActivity() {
        Intent intent = new Intent(MainActivity.this, PanSelectionActivity.class);
        startActivity(intent);
    }

    private void mostrarPedidos() {
        LinearLayout panSelectionContainer = findViewById(R.id.panSelectionContainer);
        panSelectionContainer.removeAllViews();

        int numPedidos = sharedPreferences.getAll().size() / 2; // Calculamos el número de pedidos guardados

        for (int i = 0; i < numPedidos; i++) {
            String pan = sharedPreferences.getString("Pan_" + i, "");
            String cantidad = sharedPreferences.getString("Cantidad_" + i, "");

            TextView textView = new TextView(this);
            textView.setText("Pan: " + pan + ", Cantidad: " + cantidad);
            panSelectionContainer.addView(textView);
        }
    }//hola
    private void cerrarSesion() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
