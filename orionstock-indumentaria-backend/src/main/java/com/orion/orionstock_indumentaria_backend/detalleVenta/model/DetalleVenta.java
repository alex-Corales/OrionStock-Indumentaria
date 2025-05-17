package com.orion.orionstock_indumentaria_backend.detalleVenta.model;

import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import jakarta.persistence.*;
import lombok.*;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "detalles_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private double precioUnitario;
    @OneToOne
    @JoinColumn(name = "variante_id")
    private Variante variante = new Variante();
}
