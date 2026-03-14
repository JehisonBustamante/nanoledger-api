# NanoLedger API 🏦💸

> **Un motor transaccional robusto basado en el principio contable de partida doble, construido con Spring Boot y PostgreSQL.**

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)
![JWT](https://img.shields.io/badge/Security-JWT-black.svg)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg)

## 🚀 ¿Qué es NanoLedger?

NanoLedger no es solo un CRUD de cuentas; es un motor financiero diseñado para garantizar que **el dinero nunca aparezca ni desaparezca mágicamente**. 

Utiliza el principio de **partida doble** (Double-Entry Bookkeeping) a través de transacciones ACID. Esto significa que cada transferencia de dinero genera automáticamente asientos contables: un crédito (entrada) en la cuenta de destino, y su respectivo débito (salida) exacto en la cuenta de origen. Si algo falla a la mitad, la transacción entera se revierte de manera segura gracias a las anotaciones `@Transactional` de Spring.

---

## 🛠️ Tech Stack & Arquitectura

El proyecto sigue una arquitectura sólida basada en capas (Controllers > Services > Repositories), asegurando la separación de responsabilidades y la inyección de dependencias.

- **Stack Backend**: Java 17, Spring Boot 4, Spring Data JPA, Hibernate, PostgreSQL.
- **Seguridad (Caja Fuerte)**: Protegido con **Spring Security** y tokens **JWT**. Únicamente los clientes autenticados pueden operar transferencias.
- **Calidad y Testing (El Sello)**: Pruebas unitarias impecables con **JUnit 5** y simulación de dependencias usando **Mockito** para garantizar la integridad contable ante cualquier escenario extremo (como intentos de sobregiros).
- **Integridad Continua**: Integrado con GitHub Actions para correr la suite de pruebas automáticamente en cada push en la nube.
- **Trazabilidad**: Integración de **SLF4J** para el monitoreo y logger en tiempo real.

---

## 🐳 Running it locally (Docker-Compose)

Poner el proyecto en marcha es trivial. El repositorio incluye un `docker-compose.yml` preconfigurado con las bases de datos requeridas.

### 1. Requisitos previos
-Tener [Docker Desktop](https://www.docker.com/products/docker-desktop) corriendo.
-Tener Java 17 y Maven listos (o usar simplemente `./mvnw`).

### 2. Levantar la Infraestructura
Abre tu terminal en la raíz del proyecto y ejecuta la magia:
```bash
docker-compose up -d
```
Esto levantará tu servidor PostgreSQL corriendo en el fondo en el puerto `5433` de la máquina anfitriona.

### 3. Exponer Variables de Entorno y Correr Spring
Compila y corre el servidor usando el Wrapper de Maven en Windows/Linux:

```bash
# Setea la constraseña y el secreto JWT como variables (Opcional, tiene fallbacks en el código)
export DB_PASSWORD=1234
export JWT_SECRET=49a2b5e7d8f9c1a3b2e4d6f8a0c5b7e9f1d3a5c7e9b2d4f6a8c0e2b4d6f8a0c2 

# Ejecuta
./mvnw spring-boot:run
```
*(Tip: Si usas IntelliJ o VSCode, el IDE inyecta/detecta la BD automáticamente si tienes el run configuration de Spring bien seteado).*

---

## 📖 Documentación Automática de la API (Swagger UI)
Una vez que veas en la consola que el servidor está corriendo en el puerto 8080 (gracias a los hermosos logs de SLF4J), explora y juega con la API visualmente.

🔗 **Swagger UI Local:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Allí podrás simular el logueo, inyectar el token JWT en el ícono de "Candado" 🔒 (Authorize) y probar las transacciones en vivo.

---

## 👨‍💻 Autor

Desarrollado con altos estándares de calidad por Jehison Bustamane. Construido para ser seguro, escalable y transaccionalmente perfecto.
