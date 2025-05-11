package com.orion.orionstock_indumentaria_backend.local.repository;

import com.orion.orionstock_indumentaria_backend.local.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalRepository extends JpaRepository<Local, Long> {
}
