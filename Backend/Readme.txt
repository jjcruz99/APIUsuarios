# Gestión de Usuarios - Backend

Este proyecto es una **API REST** desarrollada con **Spring Boot** que permite realizar operaciones CRUD 
(crear, leer, actualizar y eliminar) sobre una base de datos MySQL para gestionar usuarios.

---

## **Características**

- **Endpoints REST**:
  - `GET /users`: Obtener todos los usuarios.
  - `POST /users`: Agregar un nuevo usuario.
  - `PUT /users/{id}`: Actualizar un usuario existente.
  - `DELETE /users/{id}`: Eliminar un usuario por su ID.
- **Validaciones**:
  - Los campos requeridos no pueden estar vacíos.
  - Validación para que el `email` sea único.
  - Validación para que la fecha de nacimiento no sea una fecha futura.
- **Base de datos**: Integración con **MySQL**.
- **CORS habilitado**: Configuración para permitir el acceso desde el frontend.

---

## **Requisitos**

### **Software Necesario**
1. **Java 17** o superior.
2. **Maven** para la gestión de dependencias.
3. **MySQL** como base de datos.
4. Un cliente API para pruebas, como **Postman** .

---

## **Configuración del Proyecto**

### **1. Configuración de la Base de Datos**
Asegúrate de tener una base de datos MySQL configurada con el siguiente esquema:

```sql
CREATE DATABASE gestion_usuarios;
```

Luego, actualiza las credenciales de conexión en el archivo `application.properties`:

```properties
spring.application.name=gestionUsuarios
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_usuarios?useSSL=false&serverTimeZone=UTC
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### **2. Ejecución del Proyecto**
1. Asegúrate de que la base de datos MySQL esté en ejecución.
2. Ejecuta el comando para iniciar la aplicación:
   ```bash
   mvn spring-boot:run
   ```
3. La API estará disponible en `http://localhost:8080`.

---

## **Estructura del Proyecto**

```
gestion-usuarios-backend/
├── src/main/java/com/gestionUsuarios/
│   ├── model/                 # Clases de modelo (entidades)
│   ├── repository/            # Repositorios JPA
│   ├── service/               # Lógica del negocio (servicios)
│   ├── controller/            # Controladores REST
│   ├── GestionUsuariosApplication.java # Clase principal
│   ├── WebConfig.java         # Configuración de CORS
│   └── resources/
│       ├── application.properties # Configuración de la aplicación
│       └── static/            # Archivos estáticos (si los hubiera)
└── pom.xml                    # Archivo de configuración de Maven
```

---

## **Endpoints**

### **1. Obtener Todos los Usuarios**
- **URL**: `GET /users`
- **Respuesta**: Lista de usuarios en formato JSON.
```json
[
  {
    "id_usuario": 1,
    "nombre": "Juan",
    "apellido": "Gomez",
    "email": "juan@email.com",
    "fechaNacimiento": "1990-05-15",
    "rol": "Administrador"
  }
]
```

---

### **2. Crear un Nuevo Usuario**
- **URL**: `POST /users`
- **Cuerpo de la Solicitud**:
```json
{
  "nombre": "Juan",
  "apellido": "Gomez",
  "email": "juan@email.com",
  "fechaNacimiento": "1990-05-15",
  "rol": "Usuario"
}
```
- **Respuesta**:
  - Éxito: `200 OK` - "Se agregó correctamente el usuario."
  - Error: `400 Bad Request` si los datos son inválidos.
  - Error: `409 Conflict` si el `email` ya está registrado.

---

### **3. Actualizar un Usuario**
- **URL**: `PUT /users/{id}`
- **Cuerpo de la Solicitud**:
```json
{
  "nombre": "Ana",
  "apellido": "Perez",
  "email": "ana@email.com",
  "fechaNacimiento": "1985-03-10",
  "rol": "Administrador"
}
```
- **Respuesta**:
  - Éxito: `200 OK` - "Se actualizó correctamente el usuario."
  - Error: `400 Bad Request` si los datos son inválidos.
  - Error: `404 Not Found` si el usuario no existe.

---

### **4. Eliminar un Usuario**
- **URL**: `DELETE /users/{id}`
- **Respuesta**:
  - Éxito: `200 OK` - "Se eliminó el usuario correctamente."
  - Error: `404 Not Found` si el usuario no existe.

---

## **Clases Principales**

### **1. Modelo: `Usuario`**
La clase `Usuario` representa la entidad de usuario y está mapeada a la tabla en MySQL.

```java
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_usuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String rol;
}
```

---

### **2. Repositorio: `IusuarioRepository`**
Extiende `JpaRepository` para realizar operaciones CRUD en la tabla de usuarios.

```java
@Repository
public interface IusuarioRepository extends JpaRepository<Usuario, Long> {
}
```

---

### **3. Servicio: `UsuarioService`**
Implementa la lógica del negocio, como validaciones y operaciones CRUD.

```java
@Service
public class UsuarioService implements IusuarioService {
    private final IusuarioRepository usuarioRepository;

    @Override
    public void guardarUsuario(Usuario nuevoUsuario) {
        usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public void actualizarUsuario(Long id, Usuario datosUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ese ID: " + id));
        usuarioExistente.setNombre(datosUsuario.getNombre());
        usuarioRepository.save(usuarioExistente);
    }
}
```

---

### **4. Controlador: `UsuarioController`**
Define los endpoints REST para interactuar con el servidor.

```java
@RestController
public class UsuarioController {
    @Autowired
    private IusuarioService usuarioService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }

    @PostMapping("/users")
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario nuevoUsuario) {
        usuarioService.guardarUsuario(nuevoUsuario);
        return ResponseEntity.ok("Usuario agregado correctamente.");
    }
}
```

---

## **Configuración de CORS**

El archivo `WebConfig` habilita solicitudes desde dominios específicos (como el frontend en desarrollo):

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

---

## **Autor**

Desarrollado por **John Jairo Cruz**.

- Año: 2025
- Contacto: jjcruz139@gmail.com