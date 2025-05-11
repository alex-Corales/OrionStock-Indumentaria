package com.orion.orionstock_indumentaria_backend.variante.service;

import com.orion.orionstock_indumentaria_backend.variante.dto.request.CrearVarianteRequestDTO;
import com.orion.orionstock_indumentaria_backend.variante.model.Variante;

import java.util.List;

public interface IVarianteService {
    void crearVariante(List<CrearVarianteRequestDTO> varianteRequestDTO, Long idRopa);
    List<Variante> mostrarVariante(Long idRopa);
}
