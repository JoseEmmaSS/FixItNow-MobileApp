package com.example.myapplication;

public class Pedido {
    private String id;
    private String nombreEmpresa;
    private String telefono;
    private String costo;

    public Pedido(String id, String nombreEmpresa, String telefono, String costo) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCosto() {
        return costo;
    }
}
