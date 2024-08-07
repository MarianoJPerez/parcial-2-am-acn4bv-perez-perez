package com.example.bombisa;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PanSelectionActivity extends AppCompatActivity {

    private EditText quantityEditText;
    private String selectedPan;
    private int selectedQuantity;
    private TextView textView;
    // Lista para almacenar las selecciones
    private ArrayList<HashMap<String, String>> panSelections;

    // SharedPreferences para almacenar las selecciones


    // url de ejemplo para la imagen
    private static final String IMAGE_URL = "https://d2l55l4rhs1y6r.cloudfront.net/s3fs-public/headers/conocenos_header_banner-oso-n_0_0_1.png?VersionId=a9dklvJSUyZZ5bhTVymVGZqWXnM41T4b";
    // base url para Retrofit
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_selection2);

        // Configurar insets para manejo de padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Inicializar los componentes de la UI
        Spinner panSpinner = findViewById(R.id.panSpinner);
        quantityEditText = findViewById(R.id.quantityEditText);
        Button buttonSelectPan = findViewById(R.id.buttonSelectPan);
        ImageView imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);



        // Inicializar Firestore
        // Firestore

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
        buttonSelectPan.setOnClickListener(v -> {
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
        });

        // Configurar el botón para volver a la MainActivity
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            // Guardar selecciones en SharedPreferences y Firestore antes de volver a MainActivity
            savePanSelections();
            // Volver a la MainActivity
            finish();
        });

        // Cargo la imagen usando Glide
        Glide.with(this)
                .load(IMAGE_URL)
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .fitCenter()
                .into(imageView);

        // Configuro Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getPosts().enqueue(new Callback<>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    // No actualizar el TextView
                    Log.d("PanSelectionActivity", "Datos de la API recibidos pero no actualizados.");
                } else {
                    textView.setText("Error: " + response.code());
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                textView.setText("Error al cargar los datos.");
                Log.e("PanSelectionActivity", "Error en la llamada a la API", t);
            }
        });
    }

    private void savePanSelections() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (HashMap<String, String> selection : panSelections) {
            Task<DocumentReference> documentReferenceTask = db.collection("panSelections")
                    .add(selection)
                    .addOnSuccessListener(documentReference -> Log.d("PanSelectionActivity", "Seleccion guardada en Firestore con ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> {
                        Log.w("PanSelectionActivity", "Error al guardar la selección en Firestore", e);
                    });
        }
    }}