package com.example.Parcial2;

import com.example.Parcial2.Service.CsvGeneratorService;
import com.example.Parcial2.Service.EstudianteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Parcial2Application {

	public static void main(String[] args) {
		SpringApplication.run(Parcial2Application.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(CsvGeneratorService csvGeneratorService) {
		return args -> {
			String filePath = "src/main/resources/datos_estudiantes.csv";
			csvGeneratorService.generateCsv(filePath);
		};
	}
}
