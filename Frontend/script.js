    // Llamada al endpoint para obtener la lista de usuarios
    async function cargarUsuarios() {
        
        // Realizar la llamada al endpoint para obtener la lista de usuarios
        try {
            const response = await fetch('http://localhost:8080/users'); 
            if (!response.ok) {
                throw new Error('Error al obtener los usuarios');
            }

            const usuarios = await response.json(); // Convierte la respuesta a JSON
            
            // Referencia al cuerpo de la tabla
            const tabla = document.getElementById('tabla-usuarios').querySelector('tbody');
            tabla.innerHTML = ''; // Limpiar la tabla antes de agregar nuevos datos

            // Iterar sobre los usuarios y crear filas dinámicamente
            usuarios.forEach(usuario => {
                const fila = document.createElement('tr');
                let listafecha = usuario.fechaNacimiento.split('-');
                let fechaFormateada = listafecha[2] + '/' + listafecha[1] + '/' + listafecha[0];
                
                // Crear una fila con los datos del usuario
                fila.innerHTML = `
                    <td>${usuario.id_usuario}</td>
                    <td>${usuario.nombre}</td>
                    <td>${usuario.apellido}</td>
                    <td>${usuario.email}</td>
                    <td>${fechaFormateada}</td>
                    <td>${usuario.rol}</td>
                `;

                // Agregar la fila a la tabla
                tabla.appendChild(fila);
            });
        } catch (error) {
            console.error('Error:', error);
            alert('Hubo un error al cargar los usuarios');
        }
    }

    // Cargar los usuarios al cargar la página
    document.addEventListener('DOMContentLoaded', cargarUsuarios);


    // Función para limpiar el formulario
    function limpiarFormulario() {
        document.getElementById('id').value = '';
        document.getElementById('nombre').value = '';               
        document.getElementById('apellido').value = '';
        document.getElementById('email').value = '';
        document.getElementById('fecha-nacimiento').value = ''; 
        document.getElementById('rol').value = '';
    }


    // Funcion para validar el formulario
    function validarFormulario() {

        // Obtener los valores de los campos del formulario
        const nombre = document.getElementById('nombre').value.trim(); 
        const apellido = document.getElementById('apellido').value.trim(); 
        const email = document.getElementById('email').value.trim();
        const fechaNacimiento = document.getElementById('fecha-nacimiento').value;
        const rol = document.getElementById('rol').value;
        
        
        // Validar que todos los campos estén llenos            
        if (!nombre || !apellido || !email || !fechaNacimiento || !rol) {
            alert('Por favor, completa todos los campos');
            return;
        }

        //validar el formato del email
        // Verifica si el email contiene un '@' y un '.' después de '@'
        // Si no se encuentra, muestra un mensaje de error y retorna
        let validacionEmail = false;
        for(let i = 0; i < email.length; i++){
            if(email[i] === '@'){
        
                for(let j = i + 1; j < email.length; j++){
                    
                    if(email[j] === '.'){
                        validacionEmail = true;
                        break;
                    }
                }
                if(validacionEmail){
                    break;
                }
            }

            if(i == email.length - 1){
                alert('El email no es válido');
                return;
            }
        }

        //validar el formato de la fecha de nacimiento
        const fechaActual = new Date();
        const fechaNacimientoDate = new Date(fechaNacimiento);
        if(fechaNacimientoDate > fechaActual){
            alert('La fecha de nacimiento no puede ser mayor a la fecha actual');
            return;
        }

        // Crear un objeto nuevoUsuario con los datos del formulario
        const nuevoUsuario = {
            nombre : nombre,
            apellido : apellido,
            email : email,
            fechaNacimiento : fechaNacimiento,  
            rol : rol
        };

        // Retorna el objeto nuevoUsuario para su uso posterior
        return nuevoUsuario; 
    }


    // Función para agregar un nuevo usuario
    async function agregarUsuario() {

        // Llama a la función de validación y obtiene el objeto nuevoUsuario
        const nuevoUsuario = validarFormulario(); 
        if (!nuevoUsuario) {
            return; // Si la validación falla, retorna y no continúa    
        }
         
        // Realizar la llamada al endpoint para agregar el usuario
        try {
            const response = await fetch('http://localhost:8080/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuevoUsuario)
            });

            if (response.status === 409) {
                alert('El email ya está registrado. Por favor, utiliza otro email.');
                return; // Retorna si el email ya está registrado
            }
            else if (!response.ok) {
                throw new Error('Error al agregar el usuario');
            }

            alert('Usuario agregado exitosamente');
            cargarUsuarios(); // Recargar la lista de usuarios
            limpiarFormulario();
        } catch (error) {
            console.error('Error:', error);
            alert('Hubo un error al agregar el usuario');
        }
    }

    //Actualizar usuario
    async function actualizarUsuario() {
           
        // Obtener los valores de los campos del formulario
        const id_usuario = document.getElementById('id').value;
        console.log(id_usuario); // Muestra el id_usuario en la consola
    
        // Validar que todos los campos estén llenos  

        if (!id_usuario) {
            alert('Por favor, digite el Id del usuario a actualizar');
            return;
        }  
        
        const nuevoUsuario = validarFormulario(); 
        if (!nuevoUsuario) {
            return; // Si la validación falla, retorna y no continúa            
        }
           
        //realizar la actualización del usuario
            try {
                const response = await fetch(`http://localhost:8080/users/${id_usuario}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(nuevoUsuario)
                });
    
                if (!response.ok ) {
                    throw new Error('Error al actualizar el usuario');
                }
                
    
                alert('Usuario actualizado exitosamente');
                cargarUsuarios(); // Recargar la lista de usuarios
                limpiarFormulario(); // Limpiar el formulario después de actualizar
            } catch (error) {
                console.error('Error:', error);
                alert('Hubo un error al actualizar el usuario');
            }
    }


    //Eliminar usuario
    async function eliminarUsuario() {

        // Obtener el id del usuario a eliminar
        const id_usuario = document.getElementById('id').value;
        console.log(id_usuario); 
        
        // Validar que el id_usuario esté lleno
        if (!id_usuario) {
            alert('Por favor, ingresa el ID del usuario a eliminar');
            return; // retorna si no hay id_usuario
        }
        try {
            const response = await fetch(`http://localhost:8080/users/${id_usuario}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error('Error al eliminar el usuario');
            }

            alert('Usuario eliminado exitosamente');
            cargarUsuarios(); // Recargar la lista de usuarios
            limpiarFormulario();
        } catch (error) {
            console.error('Error:', error);
            alert('Hubo un error al eliminar el usuario');
        }

    }

    //evento para listar usuarios al hacer clic en el botón
    document.getElementById('boton-listar').addEventListener('click', cargarUsuarios);

    // Función para llevar al usuario a la sección de instrucciones
    function irAInstrucciones() {
    document.getElementById("contenedor-instrucciones").scrollIntoView({ behavior: "smooth" });
    }



   
