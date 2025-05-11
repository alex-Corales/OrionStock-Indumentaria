package com.orion.orionstock_indumentaria_backend.usuario.service;

import com.orion.orionstock_indumentaria_backend.usuario.dto.request.CrearUsuarioRequestDTO;
import com.orion.orionstock_indumentaria_backend.usuario.dto.request.LoginUsuarioRequestDTO;

public interface IUsuarioService {
    void crearUsuario(CrearUsuarioRequestDTO usuarioRequestDTO);
    Long loginUsuario(LoginUsuarioRequestDTO usuarioRequestDTO);
}
