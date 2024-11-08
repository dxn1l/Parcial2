package com.example.Parcial2.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante implements Runnable {

    @Id
    private Long id;

    @Column(name = "edad")
    private Double edad;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "nota_final")
    private Double notaFinal;

    @Column(name = "genero")
    private String genero;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEdad() {
        return edad;
    }

    public void setEdad(Double edad) {
        this.edad = edad;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public void run() {
        // Implementar la lógica que se debe ejecutar en un hilo separado
        System.out.println("Procesando estudiante con ID: " + id);
    }
}