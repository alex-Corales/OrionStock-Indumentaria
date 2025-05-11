package com.orion.orionstock_indumentaria_backend.variante.repository;

import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVarianteRepository extends JpaRepository<Variante, Long> {
    List<Variante> findAllByIdRopa(Long idRopa);
}
