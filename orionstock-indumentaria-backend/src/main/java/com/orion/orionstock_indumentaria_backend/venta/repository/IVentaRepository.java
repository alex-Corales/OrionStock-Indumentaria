package com.orion.orionstock_indumentaria_backend.venta.repository;

import com.orion.orionstock_indumentaria_backend.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
}
