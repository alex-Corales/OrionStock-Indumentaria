package com.orion.orionstock_indumentaria_backend.variante.dto.request;

import com.orion.orionstock_indumentaria_backend.variante.model.Estado;
import com.orion.orionstock_indumentaria_backend.variante.model.Talle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class CrearVarianteRequestDTO {
    @Enumerated(EnumType.STRING)
    @NotNull
    private Talle talle;
    @NotBlank
    private String color;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Estado estado;
    @PositiveOrZero
    private double precioUnidadCompra;
    @PositiveOrZero
    private double precioUnidadVenta;
    @PositiveOrZero
    private int cantidad;
}
