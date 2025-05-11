package com.orion.orionstock_indumentaria_backend.usuario.service;

import com.orion.orionstock_indumentaria_backend.usuario.dto.request.CrearUsuarioRequestDTO;
import com.orion.orionstock_indumentaria_backend.usuario.dto.request.LoginUsuarioRequestDTO;
import com.orion.orionstock_indumentaria_backend.usuario.model.Usuario;
import com.orion.orionstock_indumentaria_backend.usuario.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService{

    private final IUsuarioRepository iUsuarioRepository;
    private final ModelMapper modelMapper;

    @Override
    public void crearUsuario(CrearUsuarioRequestDTO usuarioRequestDTO) {
        iUsuarioRepository.save(modelMapper.map(usuarioRequestDTO, Usuario.class));
    }

    @Override
    public Long loginUsuario(LoginUsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = iUsuarioRepository.findByCorreoAndContraseña(usuarioRequestDTO.getCorreo(), usuarioRequestDTO.getContraseña());
        if(usuario != null){
            return usuario.getLocal().getId();
        }
        return 0L;
    }

}
