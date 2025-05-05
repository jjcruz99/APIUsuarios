# Gestión de Usuarios

Este proyecto es una aplicación web simple de gestión de usuarios que permite realizar operaciones CRUD 
(crear, leer, actualizar y eliminar) mediante una interfaz interactiva. Está diseñado para interactuar 
con un servidor en `http://localhost:8080` (o cualquier otro endpoint configurado).

---

## **Características**

- **Crear usuarios**: Agregar nuevos usuarios completando un formulario.
- **Listar usuarios**: Mostrar una tabla dinámica con los usuarios registrados en el sistema.
- **Actualizar usuarios**: Modificar la información de un usuario existente especificando su ID.
- **Eliminar usuarios**: Eliminar un usuario del sistema ingresando su ID.
- **Validaciones**: 
  - Comprobación de campos obligatorios.
  - Validación de formato de correo electrónico.
  - Validación de que la fecha de nacimiento no sea futura.
- **Botón de ayuda**: Lleva al usuario a la sección de instrucciones para usar la aplicación.

---

## **Requisitos**

### **Frontend**
- Navegador web moderno.
- Conexión a un servidor backend accesible en `http://localhost:8080`.

### **Backend**
- Un servidor REST API que soporte los siguientes endpoints:
  - `GET /users`: Obtener todos los usuarios.
  - `POST /users`: Agregar un nuevo usuario.
  - `PUT /users/{id}`: Actualizar un usuario existente.
  - `DELETE /users/{id}`: Eliminar un usuario por su ID.

---

## **Instalación**

1. Descarga y descomprime el archivo `.zip` del proyecto.
   
2. Abre el archivo `index.html` en tu navegador web.

3. Asegúrate de que el backend en `http://localhost:8080` esté en funcionamiento.

---

## **Estructura del Proyecto**

```
gestion-usuarios/
├── index.html       # HTML principal de la aplicación.
├── style.css        # Estilos de la aplicación.
├── script.js        # Lógica JavaScript para las operaciones CRUD.
└── Pictures/
    
```

---

## **Funciones Clave (JavaScript)**

### **1. Listar Usuarios**
Carga dinámicamente los usuarios desde el servidor y los muestra en una tabla.

```javascript
async function cargarUsuarios() {
    const response = await fetch('http://localhost:8080/users');
    const usuarios = await response.json();
    // Renderización de los usuarios en la tabla.
}
```

### **2. Crear Usuario**
Valida el formulario y envía los datos al servidor mediante un método `POST`.

```javascript
async function agregarUsuario() {
    const nuevoUsuario = validarFormulario();
    const response = await fetch('http://localhost:8080/users', {
        method: 'POST',
        body: JSON.stringify(nuevoUsuario)
    });
}
```

### **3. Actualizar Usuario**
Actualiza un usuario existente especificando su ID.

```javascript
async function actualizarUsuario() {
    const id_usuario = document.getElementById('id').value;
    const nuevoUsuario = validarFormulario();
    const response = await fetch(`http://localhost:8080/users/${id_usuario}`, {
        method: 'PUT',
        body: JSON.stringify(nuevoUsuario)
    });
}
```

### **4. Eliminar Usuario**
Elimina un usuario del sistema mediante su ID.

```javascript
async function eliminarUsuario() {
    const id_usuario = document.getElementById('id').value;
    const response = await fetch(`http://localhost:8080/users/${id_usuario}`, {
        method: 'DELETE'
    });
}
```

### **5. Validaciones**
- **Correo electrónico válido:**
  ```javascript
  if (!email.includes('@') || !email.includes('.')) {
      alert('El email no es válido');
      return;
  }
  ```

- **Fecha de nacimiento no futura:**
  ```javascript
  if (fechaNacimientoDate > fechaActual) {
      alert('La fecha de nacimiento no puede ser mayor a la fecha actual');
      return;
  }
  ```

---

## **Cómo Usar**

1. **Agregar Usuario:**
   - Completa todos los campos excepto el `ID` y haz clic en "Agregar".
   - El sistema validará los datos antes de enviarlos al servidor.

2. **Actualizar Usuario:**
   - Completa todos los campos, incluyendo el `ID`, y haz clic en "Actualizar".
   - El sistema actualiza el usuario existente con los datos proporcionados.

3. **Eliminar Usuario:**
   - Ingresa el `ID` del usuario y haz clic en "Borrar".

4. **Listar Usuarios:**
   - Haz clic en "Listar" para cargar la lista de usuarios registrados.

5. **Ayuda:**
   - Haz clic en el botón "Ayuda" para desplazarte a las instrucciones.

---

## **Estilo**

Todos los estilos fueron aplicados en CSS puro.

El diseño utiliza la fuente **Roboto** de Google Fonts para un estilo limpio y moderno. 

```


## **Autor**

Desarrollado por **John Jairo Cruz**.

- Año: 2025
- Contacto: jjcruz139@gmail.com