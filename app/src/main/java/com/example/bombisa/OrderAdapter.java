package com.example.bombisa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Definir la clase OrderAdapter
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;

    // Constructor para pasar la lista de pedidos
    public OrderAdapter(List<Order> orders) {
        this.orderList = orders;
    }

    // Definir la clase interna OrderViewHolder
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView panTextView;
        public TextView cantidadTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            panTextView = itemView.findViewById(R.id.textViewPan);
            cantidadTextView = itemView.findViewById(R.id.textViewCantidad);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        // Obtener el pedido en la posición actual
        Order currentOrder = orderList.get(position);

        // Asignar los datos a las vistas
        holder.panTextView.setText(currentOrder.getPan());
        holder.cantidadTextView.setText(String.valueOf(currentOrder.getCantidad()));
    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista de pedidos
        return orderList.size();
    }
}
