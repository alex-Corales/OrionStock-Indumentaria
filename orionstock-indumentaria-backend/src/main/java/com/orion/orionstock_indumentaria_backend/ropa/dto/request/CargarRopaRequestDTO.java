package com.orion.orionstock_indumentaria_backend.ropa.dto.request;

import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class CargarRopaRequestDTO {
    @NotBlank
    private String Nombre;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Categoria categoria;
}
