# 🧠 Magic Mind

> **Plataforma Web Inteligente para el Fortalecimiento y Diagnóstico de Habilidades Lectoescritoras**

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-brightgreen?logo=springboot)
![Angular](https://img.shields.io/badge/Angular-Nx_Monorepo-red?logo=angular)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![Google Gemini](https://img.shields.io/badge/Google_Gemini-AI_Powered-8E75B2?logo=googlegemini)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)

---

## 📖 Descripción del Proyecto

**Magic Mind** es una solución tecnológica integral diseñada para transformar y apoyar el proceso de aprendizaje lectoescritor en niños. Mediante actividades interactivas, gamificación y análisis pedagógico potenciado por **Inteligencia Artificial (Google Gemini)**, la plataforma permite realizar diagnósticos en tiempo real, seguimiento curricular y recomendaciones pedagógicas personalizadas.

---

## 🏛️ Arquitectura del Sistema

El proyecto está diseñado bajo estándares modernos de desacoplamiento y escalabilidad:

```
TesisTatiana/
├── lectoapp-backend/              # API RESTful con Spring Boot 3 & Java 17
│   ├── src/main/java/             # Arquitectura por capas (Controller, Service, Repository, DTO, Mapper)
│   ├── src/main/resources/db/     # Migraciones versionadas con Flyway (PostgreSQL)
│   └── Dockerfile                 # Imagen ligera multi-stage (Temurin 17 JRE Alpine)
│
└── lectoapp-frontend-angular/     # Monorepo Nx con Arquitectura de Microfrontends
    ├── apps/
    │   ├── shell/                 # Contenedor anfitrión (Host), autenticación y orquestación
    │   ├── admin/                 # Microfrontend de Administración y control de usuarios
    │   ├── teacher/               # Microfrontend Docente: Métricas y Reportes con IA
    │   └── student/               # Microfrontend Estudiante: Actividades lúdicas interactivas
    └── libs/shared/               # Librerías compartidas (API, Modelos, UI, Auth)
```

---

## ✨ Características Principales

- 🧒 **Experiencia Estudiante:** Actividades gamificadas como completar oraciones, ordenar secuencias narrativas y retroalimentación interactiva.
- 👩‍🏫 **Panel Docente con IA:** Análisis integral y multidimensional del progreso del estudiante (precisión, velocidad, comprensión, diagnóstico y plan de acción pedagógico) generado automáticamente con **Google Gemini**.
- 🖨️ **Reportes Profesionales:** Módulo de exportación e impresión formal de informes pedagógicos y clínicos para padres de familia y comités escolares.
- 👑 **Administración Centralizada:** Gestión segura de docentes, estudiantes y asignación de credenciales con JWT y roles dinámicos.
- 🧩 **Native Federation:** Carga dinámica e independiente de módulos frontend optimizando tiempos de carga y memoria.

---

## 🚀 Requisitos Previos

- **Java JDK 17+**
- **Node.js 20+** y **npm**
- **PostgreSQL 16+**
- **Maven 3.9+** (o el wrapper `./mvnw` incluido)

---

## ⚙️ Configuración y Ejecución Local

### 1. Backend (`lectoapp-backend`)

1. Copia el archivo de variables de entorno:
   ```bash
   cp .env.example .env
   ```
2. Configura tus credenciales en el archivo `.env` (PostgreSQL, JWT Secret, Gemini API Key).
3. Compila y ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
   *La API estará disponible en `http://localhost:8080`.*

### 2. Frontend (`lectoapp-frontend-angular`)

1. Instala las dependencias:
   ```bash
   npm install
   ```
2. Inicia el servidor de desarrollo principal (Shell):
   ```bash
   npx nx serve shell
   ```
   *La aplicación estará accesible en `http://localhost:4200`.*

---

## 🐳 Despliegue en la Nube

- **Backend:** Preparado para despliegue en contenedores cloud (Railway / Render / AWS) mediante su `Dockerfile` multi-stage optimizado con límites de memoria JVM.
- **Frontend:** Preparado para despliegue estático de alto rendimiento en **Vercel** usando el script de ensamble `npm run build:prod`.

---

## 👩‍💻 Autora & Créditos

- **Proyecto:** Magic Mind
- **Autora:** Tatiana
- **Tecnologías:** Spring Boot, Angular, Native Federation, PostgreSQL, Gemini AI
