package com.orion.orionstock_indumentaria_backend.usuario.repository;

import com.orion.orionstock_indumentaria_backend.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreoAndContraseña(String correo, String contraseña);
}
