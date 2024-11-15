# Proyecto Parcial2

Integrantes:
- Daniel Andrés Moreno Rey.
- Rodrigo Rojas Redondo.

Link del repositorio:
[https://github.com/dxn1l/Parcial2](https://github.com/dxn1l/Parcial2)

## Descripción

Este proyecto es una aplicación Spring Boot que simula una Máquina de Galton, generando datos con una distribución normal, cargándolos en una base de datos y visualizándolos gradualmente en un frontend.

## Estructura del proyecto

### BackEnd

#### Controllers:
- `DistribucionCargaController.java` - Controlador REST para cargar datos desde un archivo CSV y obtener datos de manera gradual.
- `CSVController.java` - Controlador REST para generar archivos CSV con datos distribuidos normalmente.

#### Services:
- `CSVGeneratorService.java` - Servicio para generar archivos CSV con datos con distribución normal.
- `CSVService.java` - Servicio para leer y guardar datos desde un archivo CSV.
- `DistribucionDataService.java` - Servicio para manejar la carga y obtención de datos de distribución.
- `EnsamblajeService.java` - Servicio para manejar la recepción de datos desde RabbitMQ y enviarlos al frontend.

#### Entities:
- `DatoDistribucion.java` - Entidad JPA que representa un dato de distribución en el sistema.
- `EstacionDeTrabajo.java` - Entidad que representa una estación de trabajo que procesa datos y los envía a RabbitMQ.

#### Repository:
- `DatoDistribucionRepository.java` - Repositorio JPA para manejar las operaciones de la entidad `DatoDistribucion`.

#### Config:
- `RabbitMQConfig.java` - Configuración de RabbitMQ.
- `RabbitMQListener.java` - Componente para manejar mensajes recibidos desde RabbitMQ.

- `Parcial2Application.java` - Clase principal de la aplicación Spring Boot.

### FrontEnd

- `index.html` - Página HTML para generar y cargar datos, y visualizar la Máquina de Galton.

### Base de datos

- `docker-compose.yml` - Archivo de configuración de Docker Compose para levantar una base de datos MySQL y un servidor RabbitMQ.
- `application.properties` - Archivo de configuración de Spring Boot para la conexión a la base de datos y RabbitMQ.

## Características

- Generación de archivos CSV con datos distribuidos normalmente.
- Carga de datos desde archivos CSV a la base de datos.
- Visualización gradual de datos en un frontend utilizando D3.js.
- Procesamiento de datos con RabbitMQ.

## Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- RabbitMQ
- Maven
- Docker
- HTML, CSS, JavaScript

## Ejecución

Para ejecutar la aplicación se debe hacer lo siguiente:

1. Se requiere tener instalado Docker y Maven en el sistema.
2. Clonar el repositorio en la máquina local.
3. Configurar Docker:
    ```bash
    docker-compose up -d
    ```
4. Ejecutar la aplicación:
    ```bash
    mvn spring-boot:run
    ```
5. Acceder a la URL [http://localhost:8080](http://localhost:8080) en un navegador web.

