package com.gestionUsuarios.controller;

import com.gestionUsuarios.model.Usuario;
import com.gestionUsuarios.service.IusuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private IusuarioService usuarioService;

    //Retorna todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios(){
        try {
            return  ResponseEntity.ok(usuarioService.obtenerTodosUsuarios()) ;
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Recibe un usuario, valida los datos y el email
    // Si pasa las validaciones guarda en la base de datos
    @PostMapping("/users")
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario nuevoUsuario){

        try {
            if(usuarioService.validarDatos(nuevoUsuario)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Revise los datos, no pueden estar vacios");
            }
            else if(usuarioService.validarExistenciaEmail(nuevoUsuario.getId_usuario(),nuevoUsuario.getEmail())){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El email ya está registrado. Por favor, utiliza otro.");
            }
            else{
                usuarioService.guardarUsuario(nuevoUsuario);
                return ResponseEntity.ok("Se agrego correctamente el usuario.");
            }
        }
        catch (IllegalArgumentException e) {
            // Excepción específica
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en la solicitud: " + e.getMessage());
        }
       catch (Exception e) {
        // Excepción general
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado. Por favor, inténtalo nuevamente. " + e.getMessage());
        }

    }

    //Recibe el id y datos de usuario para actualizarlo
    @PutMapping("/users/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id,@RequestBody Usuario datosUsuario){

        try {
            if(usuarioService.validarDatos(datosUsuario)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Revise los datos, no pueden estar vacios");
            }
            else if(usuarioService.validarExistenciaEmail(datosUsuario.getId_usuario(),datosUsuario.getEmail())){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El email ya está registrado. Por favor, utiliza otro.");
            }
            else{
                usuarioService.actualizarUsuario(id,datosUsuario);
                return ResponseEntity.ok("Se actualizo correctamente el usuario.");
            }
        }
        catch (IllegalArgumentException e) {
            // Excepción específica
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en la solicitud: " + e.getMessage());
        }
        catch (Exception e) {
            // Excepción general
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado. Por favor, inténtalo nuevamente. " + e.getMessage());
        }

    }

    //Recibe id del usuario a eliminar y valida si existe.
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        try {
            //validacion si se borro el usuario si existe
           Boolean validacion =  usuarioService.borrarUsuario(id);
           if (validacion) {
               return ResponseEntity.ok("Se elimino el usuario correctamente");
           }
           else {
               return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body("El usuario no existe, revise el id");
           }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo borra el usuario. " + e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        try {
            // Intentamos buscar el usuario por ID
            Usuario usuarioObtenido = usuarioService.buscarPorId(id);

            // Si el usuario no existe, devolvemos 404
            if (usuarioObtenido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "El usuario con el ID " + id + " no fue encontrado."));
            }

            return ResponseEntity.status(HttpStatus.OK).body(usuarioObtenido);
        } catch (Exception e) {
            // En caso de error, devolvemos un mensaje genérico con el estado 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", "Ocurrió un error al procesar la solicitud.",
                            "error", e.getMessage()));
        }
    }

}
