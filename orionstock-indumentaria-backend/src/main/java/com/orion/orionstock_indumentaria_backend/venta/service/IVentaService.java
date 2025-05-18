package com.orion.orionstock_indumentaria_backend.venta.service;

import com.orion.orionstock_indumentaria_backend.venta.dto.request.VentaRequestDTO;

public interface IVentaService {
    void crearVenta(VentaRequestDTO ventaRequestDTO, Long idLocal);
}
