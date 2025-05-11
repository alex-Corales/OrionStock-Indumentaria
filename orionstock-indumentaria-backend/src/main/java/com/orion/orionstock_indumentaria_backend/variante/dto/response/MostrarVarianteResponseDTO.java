package com.orion.orionstock_indumentaria_backend.variante.dto.response;

import com.orion.orionstock_indumentaria_backend.variante.model.Estado;
import com.orion.orionstock_indumentaria_backend.variante.model.Talle;
import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class MostrarVarianteResponseDTO {
    private Talle talle;
    private String color;
    private Estado estado;
    private double precioUnidadCompra;
    private double precioUnidadVenta;
    private int cantidad;
}
