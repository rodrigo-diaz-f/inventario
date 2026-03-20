# Inventario, una API REST para el control de inventario

Construida con **Spring Boot 4**, **Base de datos H2 (en memoria)** y **Spring Data JPA**.

---

## Descargar el código fuente

```bash
git clone https://github.com/rodrigo-diaz-f/inventario.git
cd inventario
```
---

## Ejecutar la aplicación

**Linux / macOS**
```bash
./mvnw spring-boot:run
```

**Windows**
```cmd
mvnw.cmd spring-boot:run
```
---

## API Endpoints

Se recomienda utilizar **curl** o una herramienta como [Postman](https://www.postman.com/) para realizar las pruebas.

La API apunta a la URL `http://localhost:8080` por defecto.

---

### GET /api/v1/productos/{id}

Retorna el detalle y disponibilidad de un producto por su ID.
```bash
curl -X GET http://localhost:8080/api/v1/productos/1
```

---

### POST /api/v1/productos/{id}/disminuir-stock

Disminuye la cantidad de un producto por su ID.

> Hay un límite de 10 peticiones por minuto por un producto. Exceder este número devolverá un error que indica que se ha alcanzado dicho límite.

```bash
curl -X POST http://localhost:8080/api/v1/productos/1/disminuir-stock \
     -H "Content-Type: application/json" \
     -d '{"cantidad": 3}'
```
---

## Ejecutando las pruebas unitarias

**Linux / macOS**
```bash
./mvnw test
```

**Windows**
```cmd
mvnw.cmd test
```
