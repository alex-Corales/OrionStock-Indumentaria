package com.orion.orionstock_indumentaria_backend.ropa.model;

import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "ropas")
public class Ropa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany
    @JoinColumn(name = "ropa_id")
    private List<Variante> variantes = new ArrayList<>();
    @Column(name = "local_id")
    private Long idLocal;
}
