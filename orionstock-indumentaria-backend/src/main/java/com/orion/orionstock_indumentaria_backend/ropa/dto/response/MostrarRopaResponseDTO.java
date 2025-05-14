package com.orion.orionstock_indumentaria_backend.ropa.dto.response;

import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.variante.model.Estado;
import com.orion.orionstock_indumentaria_backend.variante.model.Talle;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class MostrarRopaResponseDTO {
    private Long idRopa;
    private Long idVariante;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Enumerated(EnumType.STRING)
    private Talle talle;
    private String color;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private double precioUnidadCompra;
    private double precioUnidadVenta;
    private int cantidad;
}
