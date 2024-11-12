package com.example.Parcial2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DatoDistribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int posicion;
    private int cantidad;

    public DatoDistribucion(int posicion, int cantidad) {
        this.posicion = posicion;
        this.cantidad = cantidad;
    }

    public DatoDistribucion() {}

    public int getPosicion() {
        return posicion;
    }

    public int getCantidad() {
        return cantidad;
    }
}


