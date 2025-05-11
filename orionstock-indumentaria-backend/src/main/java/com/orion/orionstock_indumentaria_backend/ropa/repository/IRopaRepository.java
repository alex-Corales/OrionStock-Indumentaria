package com.orion.orionstock_indumentaria_backend.ropa.repository;

import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRopaRepository extends JpaRepository<Ropa, Long> {
    List<Ropa> findAllByIdLocal(Long idLocal);
}
