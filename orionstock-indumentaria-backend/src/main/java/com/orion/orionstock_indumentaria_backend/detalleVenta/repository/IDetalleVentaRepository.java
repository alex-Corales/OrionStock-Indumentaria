package com.orion.orionstock_indumentaria_backend.detalleVenta.repository;

import com.orion.orionstock_indumentaria_backend.detalleVenta.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
