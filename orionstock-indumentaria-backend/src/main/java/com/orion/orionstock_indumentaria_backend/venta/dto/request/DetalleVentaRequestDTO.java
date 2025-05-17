package com.orion.orionstock_indumentaria_backend.venta.dto.request;

import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class DetalleVentaRequestDTO {
    private Long idVariante;
    private int cantidad;
    private double precioUnitario;
}
