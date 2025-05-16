package com.orion.orionstock_indumentaria_backend.ropa.dto.response;

public interface MostrarRopaProjectionResponseDTO {
    Long getIdRopa();
    Long getIdVariante();
    String getNombre();
    String getCategoria();
    String getTalle();
    String getColor();
    String getEstado();
    double getPrecioUnidadCompra();
    double getPrecioUnidadVenta();
    int getCantidad();
}
