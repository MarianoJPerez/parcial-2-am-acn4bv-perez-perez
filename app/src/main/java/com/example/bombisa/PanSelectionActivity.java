package com.example.bombisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class PanSelectionActivity extends AppCompatActivity {

    private Spinner panSpinner;
    private EditText quantityEditText;
    private Button buttonSelectPan;
    private String selectedPan;
    private int selectedQuantity;
    private Conexion conexion;
    // Lista para almacenar las selecciones
    private ArrayList<HashMap<String, String>> panSelections;

    // SharedPreferences para almacenar las selecciones
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_selection2);
       conexion =new Conexion();
        // Configurar insets para manejo de padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets;
        });

        // Inicializar los componentes de la UI
        panSpinner = findViewById(R.id.panSpinner);
        quantityEditText = findViewById(R.id.quantityEditText);
        buttonSelectPan = findViewById(R.id.buttonSelectPan);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("PanSelections", MODE_PRIVATE);

        // Lista para almacenar las selecciones
        panSelections = new ArrayList<>();

        // Lista de panes
        String[] panes = {"Pan Blanco", "Pan Salvado", "Pan semillado", "Pan para pancho", "Pan para hamburguesa", "Pan light", "Pan sin sal"};

        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        panSpinner.setAdapter(adapter);

        // Manejo de la selección del Spinner
        panSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPan = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPan = null;
            }
        });

        // Manejo del botón de selección
        buttonSelectPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityText = quantityEditText.getText().toString();

                if (selectedPan != null && !quantityText.isEmpty()) {
                    selectedQuantity = Integer.parseInt(quantityText);

                    // Crear un HashMap para almacenar la selección
                    HashMap<String, String> selection = new HashMap<>();
                    selection.put("Pan", selectedPan);
                    selection.put("Cantidad", String.valueOf(selectedQuantity));

                    // Añadir la selección a la lista
                    panSelections.add(selection);

                    Toast.makeText(PanSelectionActivity.this, "Seleccionaste: " + selectedPan + " - Cantidad: " + selectedQuantity, Toast.LENGTH_SHORT).show();

                    // Limpiar el campo de cantidad después de agregar
                    quantityEditText.setText("");
                } else {
                    Toast.makeText(PanSelectionActivity.this, "Por favor selecciona un pan y una cantidad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón para volver a la MainActivity
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardar selecciones en SharedPreferences antes de volver a MainActivity
                savePanSelections();
                // Volver a la MainActivity
                finish();
            }
        });
    }

    private void savePanSelections() {
        // Limpiar las selecciones anteriores en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        // Guardar las nuevas selecciones en SharedPreferences
        for (int i = 0; i < panSelections.size(); i++) {
            HashMap<String, String> selection = panSelections.get(i);
            editor.putString("Pan_" + i, selection.get("Pan"));
            editor.putString("Cantidad_" + i, selection.get("Cantidad"));
        }
        editor.apply();
    }

    private void insertarPanSeleccionEnBaseDeDatos(String userId, String pan, int cantidad) {
        if (conexion != null) {
            try {
                conexion.insertarPanSeleccion(userId, pan, cantidad);
                Log.d("PanSelectionActivity", "Insertado correctamente: " + pan + " - " + cantidad);
            } catch (Exception e) {
                Log.e("PanSelectionActivity", "Error al insertar en la base de datos", e);
            }
        } else {
            Log.e("PanSelectionActivity", "Conexión a la base de datos no inicializada");
        }
    }}