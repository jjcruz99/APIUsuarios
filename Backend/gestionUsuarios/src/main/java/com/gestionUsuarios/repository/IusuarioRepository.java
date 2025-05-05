package com.gestionUsuarios.repository;

import com.gestionUsuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IusuarioRepository extends JpaRepository<Usuario,Long> {

}
