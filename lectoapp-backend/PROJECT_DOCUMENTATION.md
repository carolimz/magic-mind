# Documentación del proyecto: Lectoapp Backend

Resumen
-------

Proyecto backend desarrollado con Spring Boot (3.5.4) y Java 17. API REST que gestiona usuarios y estudiantes, con persistencia en PostgreSQL y migraciones gestionadas por Flyway.

Arquitectura y organización
---------------------------

- **Estructura principal:** `application`, `domain`, `infraestructure`, `shared`, `resources`.
- **Puntos clave:** controladores en `src/main/java/lectoapp_backend/infraestructure/controller` (AuthController, EstudianteController, UsuarioController).
- **Mapeo y DTOs:** MapStruct se usa para convertir entre entidades y DTOs (mappers en `application/mapper`).

Dependencias y tecnologías
--------------------------

- **Spring Boot:** web, data-jpa, security, validation.
- **DB:** PostgreSQL (driver incluido). URL por defecto en `application.yml`: `jdbc:postgresql://localhost:5432/lectoapp`.
- **Migraciones:** Flyway, scripts en `src/main/resources/db/migration` (`V1__create_usuario.sql`, `V2__create_estudiante.sql`).
- **Autenticación:** JWT con la librería `io.jsonwebtoken` (secret y expiración configurados en `application.yml`).
- **MapStruct** para mapeos y **Lombok** para reducir boilerplate.
- **OpenAPI / Swagger:** `springdoc-openapi-starter-webmvc-ui` incluido.

Configuración importante
-----------------------

- Archivo: [src/main/resources/application.yml](src/main/resources/application.yml#L1)
  - Puerto: `8080`.
  - Datasource: user `postgres`, password `root` (ajustar en producción).
  - Flyway ubicado en `classpath:db/migration` y habilitado.
  - JWT: `secret` y `expiration` definidos; revisar seguridad del secreto.
  - `spring.jpa.hibernate.ddl-auto: validate` — la base de datos debe tener el esquema aplicado por Flyway antes de iniciar.

Estructura de código relevante
-----------------------------

- Entrada: [src/main/java/lectoapp_backend/LectoappBackendApplication.java](src/main/java/lectoapp_backend/LectoappBackendApplication.java#L1)
- Controladores: [src/main/java/lectoapp_backend/infraestructure/controller](src/main/java/lectoapp_backend/infraestructure/controller)
  - `AuthController.java`
  - `EstudianteController.java`
  - `UsuarioController.java`
- Migraciones: [src/main/resources/db/migration](src/main/resources/db/migration)

Ejecución local
---------------

1. Instalar PostgreSQL y crear BD `lectoapp`:

```powershell
psql -U postgres -c "CREATE DATABASE lectoapp;"
```

2. Ajustar credenciales en `application.yml` si necesario.

3. Ejecutar la aplicación con Maven Wrapper:

```powershell
.\\mvnw.cmd spring-boot:run
```

Notas: Flyway ejecutará las migraciones al iniciar; con `ddl-auto: validate` la aplicación fallará si las tablas no existen o no coinciden con las entidades.

Pruebas y documentación API
--------------------------

- Swagger/OpenAPI estará disponible en `http://localhost:8080/swagger-ui.html` o la ruta propia de Springdoc.
- Ejecutar tests con:

```powershell
.\\mvnw.cmd test
```

Recomendaciones y siguientes pasos
---------------------------------

- No dejar secretos (JWT secret, DB password) en `application.yml` para producción; usar variables de entorno o un vault.
- Añadir README con pasos para desarrollo y enlazar este archivo.
- Generar documentación de endpoints (ej. añadir ejemplos de request/response en controllers o usar anotaciones OpenAPI detalladas).
- Configurar perfiles (`application-dev.yml`, `application-prod.yml`) y cambiar `ddl-auto` en desarrollo a `update` o `create-drop` si se desea iterar rápido.
- Añadir pruebas de integración que arranquen una BD en memoria o use Testcontainers para validar el comportamiento con PostgreSQL y Flyway.

Contacto y mantenimiento
-----------------------

Si quieres, puedo:
- Generar un `README.md` con estos pasos y comandos.
- Inspeccionar controladores y generar documentación de endpoints más detallada.
- Ejecutar los tests y validar que la app arranca localmente (necesitaré permiso para ejecutar comandos).
