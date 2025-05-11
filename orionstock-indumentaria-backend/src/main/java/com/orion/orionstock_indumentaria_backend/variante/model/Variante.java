package com.orion.orionstock_indumentaria_backend.variante.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "variantes")
public class Variante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Talle talle;
    private String color;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    private double precioUnidadCompra;
    private double precioUnidadVenta;
    private int cantidad;
    @Column(name = "ropa_id")
    private Long idRopa;
}
