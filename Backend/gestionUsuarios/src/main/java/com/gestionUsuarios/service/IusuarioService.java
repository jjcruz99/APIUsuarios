package com.gestionUsuarios.service;

import com.gestionUsuarios.model.Usuario;

import java.util.List;

public interface IusuarioService {

    //Metodo para obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios();

    //Metodo para guardar usuario.
    public void guardarUsuario(Usuario nuevoUsuario);

    //Metodo para actuallizarUsuario.
    public  void actualizarUsuario(Long id,Usuario datosUsuario);

    //Metodo para borrar usuario.
    public Boolean borrarUsuario(Long id);

    //Metodo para validar si existe email.
    public Boolean validarExistenciaEmail(Long id,String email);

    //Metodo validar datos de entrada
    public Boolean validarDatos(Usuario usuario);
}
