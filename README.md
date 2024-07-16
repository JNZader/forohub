# forohub
 # ForoHub

ForoHub es una API REST desarrollada con Spring Boot que simula el funcionamiento de un foro de discusión, centrándose específicamente en la gestión de tópicos.

## Descripción

Este proyecto es parte del Challenge Back End de Alura Latam, donde se busca replicar el funcionamiento de un foro a nivel de back end. La API permite a los usuarios realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre los tópicos del foro.

## Características

- Crear un nuevo tópico
- Mostrar todos los tópicos creados
- Mostrar un tópico específico
- Actualizar un tópico
- Eliminar un tópico
- Autenticación y autorización para restringir el acceso a la información

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.3.1
- Spring Data JPA
- Spring Security
- MySQL
- Flyway para migraciones de base de datos
- Lombok
- Maven

## Configuración del proyecto

1. Clona este repositorio
2. Asegúrate de tener instalado Java 21 y Maven
3. Configura tu base de datos MySQL en `application.properties`
4. Ejecuta `mvn spring-boot:run` para iniciar la aplicación

## Estructura del proyecto

El proyecto sigue una arquitectura de capas típica de Spring Boot:

- `model`: Contiene las clases DTO (Data Transfer Object)
- `entity`: Clases de entidad JPA
- `repository`: Interfaces de repositorio de Spring Data JPA
- `service`: Lógica de negocio
- `rest`: Controladores REST
- `config`: Clases de configuración

## API Endpoints

- `GET /api/topics`: Obtiene todos los tópicos
- `GET /api/topics/{id}`: Obtiene un tópico específico
- `POST /api/topics`: Crea un nuevo tópico
- `PUT /api/topics/{id}`: Actualiza un tópico existente
- `DELETE /api/topics/{id}`: Elimina un tópico

Nota: Existen endpoints similares para usuarios y respuestas.

## Seguridad

El proyecto utiliza Spring Security con JWT para la autenticación y autorización. Los endpoints están protegidos y requieren autenticación para ser accedidos.
