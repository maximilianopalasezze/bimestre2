# EduCloud Inscripciones

API REST desarrollada con Spring Boot para administrar cursos virtuales e inscripciones de estudiantes.

## Funcionalidades

- Listar cursos disponibles.
- Agregar nuevos cursos.
- Inscribir estudiantes en uno o más cursos.
- Calcular total a pagar.
- Guardar cursos e inscripciones en Oracle Cloud.
- Manejar errores y validaciones.
- Despliegue automático con GitHub Actions, Docker Hub y AWS EC2.

## Endpoints

### Listar cursos

GET /api/cursos

### Buscar curso por ID

GET /api/cursos/{id}

### Crear curso

POST /api/cursos

Body:

```json
{
  "nombre": "Desarrollo Backend con Spring Boot",
  "instructor": "Carlos Pérez",
  "duracion": "40 horas",
  "costo": 120000
}
```

### Inscribir estudiante

POST /api/inscripciones

Body:

```json
{
  "nombreEstudiante": "Maximiliano Palasezze",
  "correoEstudiante": "maxi@duocuc.cl",
  "idsCursos": [1, 2]
}
```

## Variables de entorno necesarias

```bash
ORACLE_DB_URL=jdbc:oracle:thin:@HOST:PUERTO/SERVICE_NAME
ORACLE_DB_USER=USUARIO
ORACLE_DB_PASSWORD=PASSWORD
```

## Ejecutar localmente

```bash
mvn spring-boot:run
```

## Ejecutar con Docker

```bash
docker build -t educloud-inscripciones .
docker run -p 8080:8080 \
  -e ORACLE_DB_URL="jdbc:oracle:thin:@HOST:PUERTO/SERVICE_NAME" \
  -e ORACLE_DB_USER="USUARIO" \
  -e ORACLE_DB_PASSWORD="PASSWORD" \
  educloud-inscripciones
```

## Secrets de GitHub Actions

- DOCKER_USERNAME
- DOCKER_TOKEN
- EC2_HOST
- EC2_USER
- EC2_KEY
- ORACLE_DB_URL
- ORACLE_DB_USER
- ORACLE_DB_PASSWORD
