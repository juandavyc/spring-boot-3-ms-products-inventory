# Microservicios Inventory y Products

Este proyecto contiene dos microservicios desarrollados en **Java 21** y **Spring Boot 3**, con base de datos H2 para pruebas rápidas.

## Arquitectura

- Arquitectura hexagonal, recomendada para microservicios.
- Comunicación entre microservicios vía **Feign Client**.
- Seguridad mediante **API Key** y uso de **UUID** para identificación.
- Uso de **Lombok** para simplificar anotaciones.
- Métricas y salud expuestas a través de **Spring Actuator**.
- Documentación de endpoints generada con **Swagger / Springdoc OpenAPI**.
- Construcción manual de respuestas en formato **JSON:API**.
- Pruebas end-to-end implementadas para verificar la funcionalidad de los servicios.

---

## Tecnologías

- Java 21
- Spring Boot 3
- H2 Database (para pruebas rápidas)
- Lombok
- Feign Client
- Resilience4j para retry de llamadas entre microservicios
- Spring Actuator
- Swagger / Springdoc OpenAPI

---

## Ejecución con Docker

1. Asegúrate de que los puertos **8081** y **8082** estén libres.
2. Desde la carpeta `docker-compose`, ejecuta:

```bash
docker compose up
```

## Ejecución con Docker

Este proyecto puede levantarse con Docker Compose. Esto levantará ambos microservicios:

- **Products** en: [http://localhost:8082](http://localhost:8082)  
- **Inventory** en: [http://localhost:8081](http://localhost:8081)  

### Endpoints principales

- Productos: [http://localhost:8082/api/products](http://localhost:8082/api/products)  
- Inventario: [http://localhost:8081/api/inventory](http://localhost:8081/api/inventory)  

> Los ejemplos de cómo funciona la API están disponibles en la carpeta `postman`, donde se encuentra la colección completa de requests.

---

## Instalación y ejecución local

Clona el repositorio, esta listo para usar.

Gracias y disculpen el poco tiempo que pude dedicarle.

