# Franchise API

[![SVG Banners](https://svg-banners.vercel.app/api?type=luminance&text1=>accenture&width=1500&height=200)]()

## Contenido
1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Requerimientos](#requerimientos)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Ejecutar localmente](#ejecutar-localmente)
6. [Producción](#producción)
7. [Endpoints](#endpoints)
8. [Autor](#autor)

## Descripción del Proyecto
Este proyecto consiste en el desarrollo de una API RESTful para la gestión de franquicias, sucursales y productos. Cada franquicia puede contener múltiples sucursales, y cada sucursal puede registrar diversos productos con su respectivo stock disponible.

## Requerimientos
### 🏢 Gestión de Franquicias
- Crear nuevas franquicias.
- Actualizar el nombre de una franquicia existente.

### 🏪 Gestión de Sucursales
- Agregar nuevas sucursales a una franquicia.
- Actualizar el nombre de una sucursal.

### 🛒 Gestión de Productos
- Agregar productos a una sucursal específica.
- Eliminar productos de una sucursal.
- Actualizar el nombre de un producto.

### 📊 Gestión de Stock
- Modificar el stock de un producto.
- Obtener el producto con mayor stock por sucursal dentro de una franquicia específica.

## Tecnologías Utilizadas
- Java 21
- Spring Boot 3.4.5
- Spring WebFlux
- MongoDB
- JUnit 5
- Mockito
- StepVerifier
- Docker
- AWS
- Terraform

## Estructura del Proyecto
```text
franchise-api/
├── infra                                   
└── src/main/java/com/cristian/franchise/
    ├── config/                             
    │   └── exception                       
    │   └── filter                          
    ├── dto/                                
    │   └── responses                       
    ├── exception                           
    ├── handler                             
    ├── mapper                              
    ├── model                               
    ├── repository                          
    ├── service/                            
    │   └── impl                            
    ├── utils                               
    └── validators                          
```

- **infra/**: Infraestructura como código (IaC), scripts y configuración con Terraform.

- **config/**: Archivos de configuración: rutas, Swagger, CORS, etc.

- **config/exception/**: Configuración para el manejo global excepciones.

- **config/filter/**: Filtros para logging, seguridad, y otras funciones transversales.

- **dto/**: Clases DTO (Data Transfer Objects) para entrada y salida de datos.

- **dto/responses**: Estructuras específicas para las respuestas de la API.

- **exception/**: Excepciones personalizadas.

- **handler/**: Controladores.

- **mapper/**: Mapeo entre entidades y DTOs.

- **model/**: Definición de modelos.

- **repository/**: Interfaces de acceso a datos.

- **service/**: Interfaces de servicios.

- **service/impl**: Implementaciones de los servicios.

- **utils/**: Funciones y clases utilitarias reutilizables.

- **validators/**: Validadores personalizados para reglas de negocio o entrada de datos.


## Ejecutar localmente

### Opción 1:

#### 1. Clonar repositorio
```bash
git clone https://github.com/crisdevcode/franchise-api.git
cd franchise-api
```

#### 2. Iniciar MongoDB
```bash
docker compose up -d
```

#### 3. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

#### 4. Acceder a la aplicación
[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

### Opción 2:

#### 1. Usar la imagen de Docker:
```bash
docker run -p 8080:8080 cristiandevcode/franchise-api:0.0.1
```

#### 2. Acceder a la aplicación
[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

## Producción
- **Base URL**: [`http://34.228.13.9/`](http://34.228.13.9/)
- **Docs**: [`http://34.228.13.9/swagger-ui/index.html`](http://34.228.13.9/swagger-ui/index.html)

## Endpoints

| Método | Ruta                                                                                     | Descripción                       |
| ------ | ---------------------------------------------------------------------------------------- | --------------------------------- |
| POST   | `/api/franchises`                                                                        | Crea una nueva franquicia         |
| PUT    | `/api/franchises/{franchiseId}`                                                          | Actualiza una franquicia          |
| POST   | `/api/franchises/{franchiseId}/branches`                                                 | Crea una nueva sucursal           |
| PUT    | `/api/franchises/{franchiseId}/branches/{branchId}`                                      | Actualiza una sucursal            |
| POST   | `/api/franchises/{franchiseId}/branches/{branchId}/products`                             | Crea un nuevo producto            |
| PUT    | `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name`            | Actualiza nombre de un producto   |
| DELETE | `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}`                 | Elimina un producto               |
| GET    | `/api/inventory/franchises/{franchiseId}/max-stock?franchiseName=frisby`                      | Obtiene productos con mayor stock |
| PUT    | `/api/inventory/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock` | Actualiza stock del producto      |

## Autor

Cristian Unigarro
