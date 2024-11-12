package com.example.Parcial2.service;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.Repository.DatoDistribucionRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class DistribucionDataService {

    private final CSVService csvService;
    private final DatoDistribucionRepository repository;

    public DistribucionDataService(CSVService csvService, DatoDistribucionRepository repository) {
        this.csvService = csvService;
        this.repository = repository;
    }

    public List<DatoDistribucion> cargarDatosDesdeCSV(String filePath) throws IOException {
        List<DatoDistribucion> datosCSV = csvService.leerYGuardarDatosDesdeCSV(filePath);
        return datosCSV;
    }

    public List<DatoDistribucion> obtenerDatos() {
        List<DatoDistribucion> datos = repository.findAll();
        System.out.println("Total de datos obtenidos de la base de datos: " + datos.size());
        return datos;
    }


}
