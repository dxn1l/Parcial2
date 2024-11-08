package com.example.Parcial2;

import com.example.Parcial2.Service.CsvGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

@SpringBootApplication
public class Parcial2Application {

	public static void main(String[] args) {
		System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
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