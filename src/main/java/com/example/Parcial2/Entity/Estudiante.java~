package com.example.Parcial2.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "estudiantes")
public class Estudiante implements Runnable{

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

    @Column(name = "tiempo_en_linea")
    private Double tiempoEnLinea;

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

    public Double getTiempoEnLinea() {
        return tiempoEnLinea;
    }

    public void setTiempoEnLinea(Double tiempoEnLinea) {
        this.tiempoEnLinea = tiempoEnLinea;
    }

    @Override
    public void run() {
        // Simular el proceso que pasa el estudiante
        try {
            // Supongamos que el proceso tarda entre 1 y 5 segundos
            int tiempoProceso = ThreadLocalRandom.current().nextInt(1, 6);
            this.tiempoEnLinea = (double) tiempoProceso;
            Thread.sleep(tiempoProceso * 1000);

            // Aquí puedes incluir cualquier otra lógica que necesites

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
