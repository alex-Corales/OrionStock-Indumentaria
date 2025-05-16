package com.orion.orionstock_indumentaria_backend.ropa.repository;

import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaProjectionResponseDTO;
import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT ropas.id AS idRopa, variantes.id AS idVariante, ropas.nombre, ropas.categoria, variantes.talle, variantes.color, " +
            "variantes.estado, variantes.precio_unidad_compra, variantes.precio_unidad_venta, variantes.cantidad\n" +
            "FROM ropas\n" +
            "INNER JOIN variantes ON variantes.ropa_id = ropas.id\n" +
            "WHERE ropas.local_id = :idLocal\n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<MostrarRopaProjectionResponseDTO> traerRopaPaginacion(@Param("limit") int limit, @Param("offset") int offset, @Param("idLocal") Long idLocal);


}
