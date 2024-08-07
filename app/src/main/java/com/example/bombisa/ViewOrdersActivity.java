package com.example.bombisa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewOrdersActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        // Inicializar FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de pedidos y el adaptador
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);

        // Recuperar los pedidos de Firestore
        fetchOrders();
    }

    private void fetchOrders() {
        db.collection("panSelections")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        orderList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Order order = document.toObject(Order.class);
                            orderList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("ViewOrdersActivity", "Error getting documents.", task.getException());
                        Toast.makeText(ViewOrdersActivity.this, "Error fetching orders.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}