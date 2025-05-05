package com.gestionUsuarios.service;

import com.gestionUsuarios.model.Usuario;
import com.gestionUsuarios.repository.IusuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService implements  IusuarioService{

    //Inyeccion de dependencia para usar metedos CRUD

    private final IusuarioRepository usuarioRepository;

    public UsuarioService(IusuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Implementacion de metdodos de la interface IusuarioService

    @Override
    public List<Usuario> obtenerTodosUsuarios() {

        //Retorna una lista con todos los usuarios por medio del metodo findall()
        return usuarioRepository.findAll();
    }

    @Override
    public void guardarUsuario(Usuario nuevoUsuario) {

        //Guarda el usuario recibido
        usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public void actualizarUsuario(Long id,Usuario datosUsuario) {

        //Validar la existencia del usuario y almacenarlo
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ese ID : " + id));

        //Si existe se actualizan los datos del usuario

            //catualiza los datos del usuario
            usuarioExistente.setNombre(datosUsuario.getNombre());
            usuarioExistente.setApellido(datosUsuario.getApellido());
            usuarioExistente.setEmail(datosUsuario.getEmail());
            usuarioExistente.setFechaNacimiento(datosUsuario.getFechaNacimiento());
            usuarioExistente.setRol(datosUsuario.getRol());
            //Guarda los datos del usuario
            usuarioRepository.save(usuarioExistente);


    }

    @Override
    public void borrarUsuario(Long id) {
        //Valida si existe si id_usuario antes de borrarlo
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }
        else{
            System.out.println("No existe ningun usuario com ese Id");
        }
    }


    @Override
    public Boolean validarExistenciaEmail(Long id,String email){

        boolean validacionEmail=false;
        //Obtiene todos los usuarios
        List<Usuario> listaEmails = usuarioRepository.findAll();

        for(Usuario usuarioActual : listaEmails){

            //Validar si el email ya se encuentra en la base de datos.
            //Solo permite caso de actualizacion de usuario dejar el mismos email.
            if (usuarioActual.getEmail().equals(email) && Objects.equals(usuarioActual.getId_usuario(), id)) {
                validacionEmail = true;
                break;
            } else if (usuarioActual.getEmail().equals(email) && !Objects.equals(usuarioActual.getId_usuario(), id)) {
                break;
            }
        }
        return validacionEmail;
    }

    //Metodo validar datos de entrada
    public Boolean validarDatos(Usuario usuario){
        boolean validacion=false;
        List<String> listaDatosUsuario = new ArrayList<>();

        listaDatosUsuario.add(usuario.getNombre());
        listaDatosUsuario.add(usuario.getApellido());
        listaDatosUsuario.add(usuario.getEmail());
        listaDatosUsuario.add(usuario.getRol());

        for(String dato : listaDatosUsuario){
            if(dato == null || dato.isBlank()){
                validacion = true;
                break;
            }
        }

            if (usuario.getFechaNacimiento() == null) {
                validacion = false;
                System.out.println("La fecha de nacimiento no puede ser nula.");
            } else if (usuario.getFechaNacimiento().isAfter(LocalDate.now())) {
                validacion = false;
                System.out.println("La fecha de nacimiento no puede ser en el futuro.");
            }

        return validacion;
    }
}
