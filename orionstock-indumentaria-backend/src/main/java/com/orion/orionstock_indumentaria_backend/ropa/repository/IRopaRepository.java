package com.orion.orionstock_indumentaria_backend.ropa.repository;

import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRopaRepository extends JpaRepository<Ropa, Long> {
    List<Ropa> findAllByIdLocal(Long idLocal);

    @Query("SELECT r FROM Ropa r WHERE r.categoria = :categoria AND r.nombre LIKE %:nombre% AND r.idLocal = :idLocal")
    List<Ropa> buscarPorCategoriaNombre(Categoria categoria, String nombre, Long idLocal);

    @Query("SELECT r FROM Ropa r WHERE r.categoria = :categoria AND r.idLocal = :idLocal")
    List<Ropa> buscarPorCategoria(Categoria categoria, Long idLocal);

    @Query("SELECT r FROM Ropa r WHERE r.nombre LIKE %:nombre% AND r.idLocal = :idLocal")
    List<Ropa> buscarPorNombre(String nombre, Long idLocal);
}
