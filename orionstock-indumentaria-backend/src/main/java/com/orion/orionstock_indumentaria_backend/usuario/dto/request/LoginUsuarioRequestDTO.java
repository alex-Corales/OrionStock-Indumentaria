package com.orion.orionstock_indumentaria_backend.usuario.dto.request;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class LoginUsuarioRequestDTO {
    private String correo;
    private String contraseña;
}
