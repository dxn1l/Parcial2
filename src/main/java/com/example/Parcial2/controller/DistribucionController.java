package com.example.Parcial2.controller;

import com.example.Parcial2.Entity.DatoDistribucion;
import com.example.Parcial2.service.DistribucionDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DistribucionController {

    private final DistribucionDataService distribucionDataService;

    public DistribucionController(DistribucionDataService distribucionDataService) {
        this.distribucionDataService = distribucionDataService;
    }


    @GetMapping("/cargarDatosCSV")
    public ResponseEntity<List<DatoDistribucion>> cargarDatos(@RequestParam String filePath) throws IOException {
        List<DatoDistribucion> datos = distribucionDataService.cargarDatosDesdeCSV(filePath);
        return ResponseEntity.ok(datos); // Retorna los datos que se guardaron en la BD
    }

    @GetMapping("/obtenerDatos")
    public ResponseEntity<List<DatoDistribucion>> obtenerDatos() {
        List<DatoDistribucion> datos = distribucionDataService.obtenerDatosAgrupados();
        return ResponseEntity.ok(datos);
    }

}

