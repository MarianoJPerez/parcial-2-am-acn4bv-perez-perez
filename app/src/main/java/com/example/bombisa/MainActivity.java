package com.example.bombisa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int contador = 0;
    private TextView contadorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button botonSumar = findViewById(R.id.pedidos);
        contadorTextView = findViewById(R.id.contadorTextView);

        botonSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contador++;

                contadorTextView.setText(String.valueOf("Panes pedidos: " +contador ));
            }
        });
    }
    }
