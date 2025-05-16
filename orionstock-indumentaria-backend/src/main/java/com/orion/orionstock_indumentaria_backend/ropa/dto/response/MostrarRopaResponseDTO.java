package com.orion.orionstock_indumentaria_backend.ropa.dto.response;

import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.variante.model.Estado;
import com.orion.orionstock_indumentaria_backend.variante.model.Talle;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter @Getter @NoArgsConstructor @ToString
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

    public MostrarRopaResponseDTO(Long idRopa, Long idVariante, String nombre, Categoria categoria,
                                  Talle talle, String color, Estado estado,
                                  double precioUnidadCompra, double precioUnidadVenta, int cantidad) {
        this.idRopa = idRopa;
        this.idVariante = idVariante;
        this.nombre = nombre;
        this.categoria = categoria;
        this.talle = talle;
        this.color = color;
        this.estado = estado;
        this.precioUnidadCompra = precioUnidadCompra;
        this.precioUnidadVenta = precioUnidadVenta;
        this.cantidad = cantidad;
    }

}
