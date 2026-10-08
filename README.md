# agap2 - AEMET

Aplicación para consultar predicciones meteorológicas de AEMET por municipio.

## Requisitos

- Java JDK 25.
- Node.js 22.12 o superior.
- npm 12.
- Una clave de acceso de [AEMET OpenData](https://opendata.aemet.es/).

El backend incluye Maven Wrapper, por lo que no es necesario instalar Maven.

## Lanzamiento

Abre dos terminales en la raíz del proyecto. Ejecuta primero el backend y, cuando esté iniciado, prepara el frontend.

### 1. Configurar la clave de AEMET

El backend utiliza esta variable en `backend/src/main/resources/application.yml`:

```yaml
aemet:
  api-key: ${AEMET_API_KEY}
```

### 2. Ejecutar backend

En la primera terminal:

```powershell
cd backend
.\mvnw.cmd clean spring-boot:run -U
```

El backend queda disponible en `http://localhost:8080`.

### 3. Ejecutar frontend

En la segunda terminal:

```powershell
cd frontend
npm ci
npm start
```

El frontend queda disponible en `http://localhost:4200`.

Durante el desarrollo, las peticiones que empiezan por `/api` se redirigen automáticamente desde Angular (`4200`) al backend (`8080`).
