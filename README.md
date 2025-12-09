# Sistema de Gestión de Biblioteca

Sistema web desarrollado con Spring Boot para la gestión de una biblioteca digital. Permite administrar categorías de libros, catálogo de libros y gestión de quejas/sugerencias de usuarios.

## 📋 Características

- **Gestión de Categorías**: Crear, listar y administrar categorías de libros
- **Gestión de Libros**: CRUD completo para el catálogo de libros con información detallada
- **Sistema de Quejas/Sugerencias**: Registro y seguimiento de quejas, sugerencias y consultas de usuarios
- **Interfaz Web**: Interfaz moderna desarrollada con Thymeleaf y Bootstrap
- **Base de Datos MySQL**: Persistencia de datos con JPA/Hibernate

## 🛠️ Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación
- **Spring Boot 3.5.6**: Framework de desarrollo
- **Spring Data JPA**: Persistencia de datos
- **MySQL**: Base de datos relacional
- **Thymeleaf**: Motor de plantillas para vistas
- **Lombok**: Reducción de código boilerplate
- **Maven**: Gestión de dependencias
- **Bootstrap**: Framework CSS para el diseño

## 📦 Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java JDK 21** o superior
- **Maven 3.6+**
- **MySQL 8.0+** o superior
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd o-EjercicioPractico1_FabianBustamante
```

### 2. Configurar la Base de Datos

1. Asegúrate de que MySQL esté ejecutándose en tu sistema
2. Ejecuta el script SQL para crear la base de datos y las tablas:

```bash
mysql -u root -p < "caso de estudio/biblioteca/biblioteca_schema.sql"
```

O ejecuta el script manualmente desde MySQL Workbench o cliente MySQL.

### 3. Configurar la Aplicación

Edita el archivo `caso de estudio/biblioteca/src/main/resources/application.properties` y ajusta las siguientes propiedades según tu configuración:

```properties
# Configuración del servidor
server.port=78

# Configuración de la base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña_aqui
```

### 4. Compilar el Proyecto

```bash
cd "caso de estudio/biblioteca"
mvn clean install
```

### 5. Ejecutar la Aplicación

**Opción 1: Desde Maven**
```bash
mvn spring-boot:run
```

**Opción 2: Desde el IDE**
- Ejecuta la clase `BibliotecaApplication.java` como aplicación Java

**Opción 3: Ejecutar el JAR**
```bash
mvn package
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
```

### 6. Acceder a la Aplicación

Una vez iniciada la aplicación, accede a:
- **URL**: http://localhost:78
- **Puerto**: 78 (configurable en `application.properties`)

## 📁 Estructura del Proyecto

```
caso de estudio/biblioteca/
├── src/
│   ├── main/
│   │   ├── java/com/biblioteca/biblioteca/
│   │   │   ├── BibliotecaApplication.java      # Clase principal
│   │   │   ├── controller/                      # Controladores REST/Web
│   │   │   │   ├── CategoriaController.java
│   │   │   │   ├── LibroController.java
│   │   │   │   ├── QuejaController.java
│   │   │   │   └── HomeController.java
│   │   │   ├── domain/                          # Entidades JPA
│   │   │   │   ├── Categoria.java
│   │   │   │   ├── Libro.java
│   │   │   │   └── Queja.java
│   │   │   ├── repository/                      # Repositorios JPA
│   │   │   │   ├── CategoriaRepository.java
│   │   │   │   ├── LibroRepository.java
│   │   │   │   └── QuejaRepository.java
│   │   │   └── service/                         # Lógica de negocio
│   │   │       ├── CategoriaService.java
│   │   │       ├── LibroService.java
│   │   │       └── QuejaService.java
│   │   └── resources/
│   │       ├── application.properties           # Configuración
│   │       ├── static/                          # Recursos estáticos
│   │       │   ├── css/style.css
│   │       │   └── js/script.js
│   │       └── templates/                       # Plantillas Thymeleaf
│   │           ├── home.html
│   │           ├── layout.html
│   │           ├── categorias/
│   │           ├── libros/
│   │           └── quejas/
│   └── test/                                    # Pruebas unitarias
├── biblioteca_schema.sql                        # Script de base de datos
├── pom.xml                                      # Configuración Maven
└── README.md                                    # Este archivo
```

## 🗄️ Modelo de Datos

### Entidades Principales

- **Categoria**: Categorías de libros (Ficción, Ciencia, Historia, etc.)
- **Libro**: Catálogo de libros con información detallada
- **Queja**: Sistema de quejas, sugerencias y consultas

### Relaciones

- Un **Libro** pertenece a una **Categoria** (ManyToOne)
- Las **Quejas** son independientes

## 🌐 Endpoints Principales

### Páginas Web

- `/` - Página de inicio
- `/libros` - Listado de libros
- `/libros/nuevo` - Formulario de nuevo libro
- `/libros/{id}` - Detalle de un libro
- `/categorias` - Listado de categorías
- `/categorias/nueva` - Formulario de nueva categoría
- `/quejas` - Listado de quejas/sugerencias
- `/quejas/nueva` - Formulario de nueva queja
- `/acerca` - Página acerca de
- `/contacto` - Página de contacto

## 🔧 Configuración Adicional

### Modo Desarrollo

El proyecto incluye Spring Boot DevTools para recarga automática durante el desarrollo. Los cambios en archivos estáticos y templates se reflejan automáticamente sin reiniciar la aplicación.

### Logging

Para ver las consultas SQL ejecutadas, la propiedad `spring.jpa.show-sql=true` está habilitada en `application.properties`.

## 📝 Datos de Ejemplo

El script `biblioteca_schema.sql` incluye datos de ejemplo:
- 8 categorías predefinidas
- 10 libros de ejemplo
- 5 quejas/sugerencias de ejemplo

## 🧪 Pruebas

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

## 👤 Autor

**Fabian Bustamante**

## 📄 Licencia

Este proyecto es un ejercicio práctico de desarrollo de software.

## 🤝 Contribuciones

Este es un proyecto educativo. Las contribuciones son bienvenidas para mejorar el código y la documentación.

## 📞 Soporte

Para consultas o problemas, por favor abre un issue en el repositorio del proyecto.

---

**Nota**: Asegúrate de tener MySQL ejecutándose antes de iniciar la aplicación. El puerto por defecto es 78, pero puedes cambiarlo en `application.properties` si es necesario.

