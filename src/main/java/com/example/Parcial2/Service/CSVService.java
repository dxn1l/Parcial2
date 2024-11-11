package com.example.Parcial2.service;

import com.example.Parcial2.Repository.DatoDistribucionRepository;
import org.springframework.stereotype.Service;
import com.example.Parcial2.Entity.DatoDistribucion;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CSVService {

    private final DatoDistribucionRepository repository;

    public CSVService(DatoDistribucionRepository repository) {
        this.repository = repository;
    }

    public List<DatoDistribucion> leerYGuardarDatosDesdeCSV(String filePath) throws IOException {
        List<DatoDistribucion> datos = new ArrayList<>();

        repository.deleteAll();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            br.readLine(); // Saltar la cabecera

            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                if (valores.length == 2) { // Verificar que haya dos columnas
                    try {
                        int posicion = Integer.parseInt(valores[0].trim());
                        int cantidad = Integer.parseInt(valores[1].trim());

                        // Crear instancia de DatoDistribucion y agregarla a la lista
                        DatoDistribucion dato = new DatoDistribucion(posicion, cantidad);
                        datos.add(dato);
                    } catch (NumberFormatException e) {
                        System.err.println("Formato incorrecto en línea: " + linea);
                    }
                }
            }

            // Guardar todos los datos en la base de datos
            repository.saveAll(datos);
        } catch (Exception e) {
            throw new IOException("Error al leer y guardar datos desde CSV", e);
        }

        return datos; // Retornar la lista de datos almacenados
    }
}