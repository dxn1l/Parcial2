package com.example.Parcial2.service;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.Repository.DatoDistribucionRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



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
        return datosCSV; // Retornar los datos leídos y guardados en la BD
    }



    public List<DatoDistribucion> obtenerDatos() {
        return repository.findAll();
    }


}
