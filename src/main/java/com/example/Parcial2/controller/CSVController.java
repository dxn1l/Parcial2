package com.example.Parcial2.controller;

import com.example.Parcial2.service.CSVGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CSVController {

    private final CSVGeneratorService csvGeneratorService;

    public CSVController(CSVGeneratorService csvGeneratorService) {
        this.csvGeneratorService = csvGeneratorService;
    }

    @GetMapping("/generarCSV")
    public ResponseEntity<String> generarCSV(
            @RequestParam String filePath,
            @RequestParam int numDatos,
            @RequestParam double media,
            @RequestParam double desviacionEstandar) {
        try {
            csvGeneratorService.generarCSV(filePath, numDatos, media, desviacionEstandar);
            return ResponseEntity.ok("CSV generado correctamente en " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al generar el CSV: " + e.getMessage());
        }
    }
}

