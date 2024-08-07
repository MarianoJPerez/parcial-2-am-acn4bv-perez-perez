package com.example.bombisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

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
    }

    private void abrirPanSelectionActivity() {
        Intent intent = new Intent(MainActivity.this, PanSelectionActivity.class);
        startActivity(intent);
    }

    private void mostrarPedidos() {
        LinearLayout panSelectionContainer = findViewById(R.id.panSelectionContainer);
        panSelectionContainer.removeAllViews();

        // Obtener los pedidos de Firestore
        db.collection("panSelections")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String pan = document.getString("Pan");
                            String cantidad = document.getString("Cantidad");

                            TextView textView = new TextView(MainActivity.this);
                            textView.setText("Pan: " + pan + ", Cantidad: " + cantidad);
                            panSelectionContainer.addView(textView);
                        }
                    } else {
                        // Manejar el error
                        TextView errorTextView = new TextView(MainActivity.this);
                        errorTextView.setText("Error al obtener los pedidos.");
                        panSelectionContainer.addView(errorTextView);
                    }
                });
    }

    private void cerrarSesion() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
