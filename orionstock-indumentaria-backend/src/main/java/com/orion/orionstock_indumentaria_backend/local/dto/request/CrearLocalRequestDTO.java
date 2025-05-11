package com.orion.orionstock_indumentaria_backend.local.dto.request;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class CrearLocalRequestDTO {
    private String nombre;
    private String direccion;
}
