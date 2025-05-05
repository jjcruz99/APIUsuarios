package com.gestionUsuarios.service;

import com.gestionUsuarios.model.Usuario;
import com.gestionUsuarios.repository.IusuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private IusuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        //Inicializar los mocks
       MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosUsuarios() {
        //  Simular usuarios
        Usuario usuario1 = new Usuario(1L, "Juan", "Gomez", "juan@email.com", LocalDate.of(1990, 5, 15), "Usuario");
        Usuario usuario2 = new Usuario(2L, "Ana", "Perez", "ana@email.com", LocalDate.of(1985, 3, 10), "Administrador");

        // Configurar el mock para retornar una lista de usuarios
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        // Llamar al método a probar
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();

        // Verificaciones
        assertEquals(2, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        assertEquals("Ana", usuarios.get(1).getNombre());

        // Verificar que se llamó al método findAll() una vez
       verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void guardarUsuario() {
        //Crear usua a guardar
        Usuario nuevoUsario = new Usuario(null,"Pedro","Infante","pedro@email.es",LocalDate.of(1940,5,20),"Usuario");

        //llamar metodo a probar
        usuarioService.guardarUsuario(nuevoUsario);

        //verificar
        verify(usuarioRepository,times(1)).save(nuevoUsario);
    }

    @Test
    void actualizarUsuario() {
        Long id = 1L;
        Usuario usuarioExistente = new Usuario(id, "Juan", "Gomez", "juan@email.com", LocalDate.of(1990, 5, 15), "Usuario");
        Usuario usuarioActualizado = new Usuario(id, "Carlos", "Lopez", "carlos@email.com", LocalDate.of(1995, 2, 20), "Administrador");

        //Configurar el mock para retornar un usuario existente
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));

        //llamar al metodo
        usuarioService.actualizarUsuario(id,usuarioActualizado);

        //verificaciones
        assertEquals("Carlos",usuarioExistente.getNombre());

        //verificar que el metodo save() fue llamado
        verify(usuarioRepository , times(1)).save(usuarioExistente);
    }

    @Test
    void borrarUsuario() {

        Long id = 2l;

        //configurar el mock para simular la existencia del id
        when(usuarioRepository.existsById(id)).thenReturn(true);

        //llamar metodo
        usuarioService.borrarUsuario(id);

        //verificar
        verify(usuarioRepository ,times(1)).deleteById(id);
    }

    @Test
    void validarExistenciaEmail(){
        Usuario usuario1 = new Usuario(1L, "Juan", "Gomez", "juan@email.com", LocalDate.of(1990, 5, 15), "Usuario");
        Usuario usuario2 = new Usuario(2L, "Carlos", "Lopez", "carlos@email.com", LocalDate.of(1995, 2, 20), "Administrador");
        Long id = 2L;
        String email = "carlos@email.com";

        //simular los usuarios en Bd
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1,usuario2));

        //llamar al metodo
        Boolean existeEmail = usuarioService.validarExistenciaEmail(id,email);

        //validacion
        assertEquals(true,existeEmail);

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void validarDatos(){

        Usuario usuario1 = new Usuario(1L, "Juan", "", "juan@email.com", LocalDate.of(1990, 5, 15), "Usuario");

        Boolean validacion = usuarioService.validarDatos(usuario1);

        assertEquals(true,validacion);
    }
}