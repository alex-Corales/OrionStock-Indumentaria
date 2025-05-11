package com.orion.orionstock_indumentaria_backend.local.model;

import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "locales")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    @OneToMany
    @JoinColumn(name = "local_id")
    private List<Ropa> ropas = new ArrayList<>();
}
