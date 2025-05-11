package com.orion.orionstock_indumentaria_backend.usuario.controller;

import com.orion.orionstock_indumentaria_backend.usuario.dto.request.CrearUsuarioRequestDTO;
import com.orion.orionstock_indumentaria_backend.usuario.dto.request.LoginUsuarioRequestDTO;
import com.orion.orionstock_indumentaria_backend.usuario.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final IUsuarioService iUsuarioService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioRequestDTO usuarioRequestDTO){
        iUsuarioService.crearUsuario(usuarioRequestDTO);
        return ResponseEntity.ok("El usuario fue creado con exito");
    }

    @PostMapping("/login")
    public ResponseEntity<Long> iniciarSesion(@RequestBody LoginUsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(iUsuarioService.loginUsuario(usuarioRequestDTO));
    }

}
