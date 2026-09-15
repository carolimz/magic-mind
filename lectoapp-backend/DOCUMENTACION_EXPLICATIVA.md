# Documentación explicativa — Lectoapp (versión usuario)

Propósito
--------

Este documento explica, en lenguaje sencillo, qué hace Lectoapp Backend, a quién va dirigido y cómo usarlo desde la perspectiva de un usuario o administrador no técnico.

¿Qué es Lectoapp?
------------------

Lectoapp es el backend que alimenta una aplicación educativa para la gestión de usuarios y estudiantes. Proporciona una API que permite registrar, autenticar y administrar cuentas, y manejar información de estudiantes (perfiles, progreso, intentos, insignias, etc.).

Audiencia
--------

- Profesores y administradores que gestionan cuentas y estudiantes.
- Personal técnico no experto que necesita entender las capacidades básicas del sistema.

Funciones principales (resumen para usuarios)
--------------------------------------------

- Autenticación: inicio de sesión y gestión de sesiones mediante credenciales.
- Gestión de usuarios: crear, editar y listar usuarios (incluido un usuario administrador por defecto).
- Gestión de estudiantes: crear y consultar datos de estudiantes y su progreso.
- Seguridad básica: el sistema usa tokens (JWT) para proteger las operaciones que requieren autenticación.

Flujos típicos de uso
---------------------

1. Inicio (administrador):
   - El administrador usa las credenciales por defecto definidas en la configuración para iniciar sesión (esto es sólo para desarrollo; en producción debe cambiarse).
2. Crear usuarios/estudiantes:
   - Desde el frontend o usando llamadas a la API, se crean usuarios y se asignan roles.
3. Operaciones protegidas:
   - Acciones como editar datos o acceder a información sensible requieren autenticación con token.

Acceso y credenciales por defecto (desarrollo)
--------------------------------------------

- En `application.yml` hay un usuario administrador por defecto (sólo en desarrollo):

- Correo: `admin@lectoapp.com`
- Contraseña: `Admin123*`

Importante: Cambiar estas credenciales antes de poner el sistema en producción.

Cómo interactuar con el sistema (alto nivel)
------------------------------------------

- La aplicación expone endpoints REST que el frontend consume. Para usuarios no técnicos, lo habitual es usar la interfaz web móvil o de escritorio que llame a estos endpoints.
- Si necesitas validar manualmente un flujo (solo para pruebas), puedes usar herramientas como Postman o curl para autenticarte y llamar a las rutas protegidas.

Ejemplo simplificado (autenticación)
-----------------------------------

1. Enviar credenciales a `POST /api/auth/login` (ruta ilustrativa): recibirás un token.
2. Incluir el token en el encabezado `Authorization: Bearer <token>` para las llamadas siguientes.

Buenas prácticas para usuarios y administradores
----------------------------------------------

- No compartir las credenciales del administrador.
- Cambiar la contraseña del admin tras la primera conexión.
- Hacer copias de seguridad periódicas de la base de datos si se gestiona información importante.

Dónde pedir ayuda
------------------

- Si usas la aplicación y hay errores visibles (500, páginas que no cargan, datos faltantes), reporta: descripción del error, acción realizada y hora.
- Para problemas de acceso: contactar al administrador y solicitar reinicio de contraseña.

Siguientes pasos recomendados para un usuario responsable
--------------------------------------------------------

1. Cambiar la contraseña del admin por una segura.
2. Configurar un entorno de producción con credenciales seguras y certificados.
3. Revisar que el frontend use HTTPS para proteger las credenciales de los usuarios.

¿Quieres que cree una versión en inglés de este documento, o que genere una guía rápida para administradores con pasos concretos (cambiar contraseña, crear usuarios)?
