package com.orion.orionstock_indumentaria_backend.venta.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class VentaRequestDTO {
    private LocalDate fecha;
    private double total;
    private List<DetalleVentaRequestDTO> listaDetalleVenta = new ArrayList<>();
}
