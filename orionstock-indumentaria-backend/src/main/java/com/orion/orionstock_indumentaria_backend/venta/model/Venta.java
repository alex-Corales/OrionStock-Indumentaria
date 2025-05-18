package com.orion.orionstock_indumentaria_backend.venta.model;

import com.orion.orionstock_indumentaria_backend.detalleVenta.model.DetalleVenta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate fecha;
    private double total;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venta_id")
    List<DetalleVenta> detalleVenta = new ArrayList<>();
    @Column(name = "local_id")
    private Long idLocal;
}
