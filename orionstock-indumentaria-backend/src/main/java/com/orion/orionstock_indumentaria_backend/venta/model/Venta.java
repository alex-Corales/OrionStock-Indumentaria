package com.orion.orionstock_indumentaria_backend.venta.model;

import com.orion.orionstock_indumentaria_backend.detalleVenta.model.DetalleVenta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    private double total;
    @OneToMany
    @JoinColumn(name = "venta_id")
    List<DetalleVenta> detalleVenta = new ArrayList<>();
}
