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

## Funcionalidad semana 2: almacenamiento de resúmenes en AWS S3

El sistema permite generar un archivo físico del resumen de inscripción y luego administrarlo en un bucket de AWS S3.

Bucket usado para la actividad:

```bash
educloud-inscripciones-2026
```

Región:

```bash
us-east-1
```

### Endpoints de resumen

#### Generar archivo físico del resumen

```http
POST /api/inscripciones/{idInscripcion}/resumen/generar
```

Genera un archivo `.txt` en la carpeta local `resumenes`.

#### Subir resumen a AWS S3

```http
POST /api/inscripciones/{idInscripcion}/resumen/subir
```

El archivo queda guardado en el bucket con esta estructura:

```bash
{idInscripcion}/resumen-inscripcion-{idInscripcion}.txt
```

Ejemplo:

```bash
1/resumen-inscripcion-1.txt
```

#### Modificar resumen en AWS S3

```http
PUT /api/inscripciones/{idInscripcion}/resumen
```

Body JSON:

```json
{
  "contenido": "Nuevo contenido del resumen"
}
```

#### Descargar resumen desde AWS S3

```http
GET /api/inscripciones/{idInscripcion}/resumen/descargar
```

#### Borrar resumen desde AWS S3

```http
DELETE /api/inscripciones/{idInscripcion}/resumen
```

### Variables de entorno AWS necesarias

```bash
AWS_ACCESS_KEY_ID=TU_ACCESS_KEY
AWS_SECRET_ACCESS_KEY=TU_SECRET_KEY
AWS_REGION=us-east-1
AWS_S3_BUCKET=educloud-inscripciones-2026
```

### Secrets agregados a GitHub Actions

Además de los secrets anteriores, para el despliegue se deben configurar:

```bash
DOCKER_USERNAME
DOCKER_TOKEN
EC2_HOST
EC2_USER
EC2_KEY
ORACLE_DB_URL
ORACLE_DB_USER
ORACLE_DB_PASSWORD
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
AWS_REGION
AWS_S3_BUCKET
```
