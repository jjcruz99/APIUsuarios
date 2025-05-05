package com.gestionUsuarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Usuario {

    //Estableceer id_usuario como PK autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_usuario ;

    //Atributos no pueden ser nulos
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String rol;

    public Usuario(){

    }

    //constructor con parametros
    public Usuario(Long id_usuario,String nombre,String apellido,String email,LocalDate fechaNacimiento,String rol){
        this.id_usuario = id_usuario;
        this.nombre =  nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
    }

    //Declaracion de getters y setters
    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
