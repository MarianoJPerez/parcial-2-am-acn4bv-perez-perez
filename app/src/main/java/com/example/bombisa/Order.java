package com.example.bombisa;

public class Order {
    private String pan;
    private int cantidad;

    public Order() {
        // Constructor vacío necesario para Firestore
    }

    public Order(String pan, int cantidad) {
        this.pan = pan;
        this.cantidad = cantidad;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
