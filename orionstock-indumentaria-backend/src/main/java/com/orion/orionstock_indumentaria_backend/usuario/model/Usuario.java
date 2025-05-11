package com.orion.orionstock_indumentaria_backend.usuario.model;

import com.orion.orionstock_indumentaria_backend.local.model.Local;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    @OneToOne
    @JoinColumn(name = "local_id")
    private Local local = new Local();
}
