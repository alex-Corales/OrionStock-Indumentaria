package com.orion.orionstock_indumentaria_backend.ropa.service;

import com.orion.orionstock_indumentaria_backend.ropa.dto.request.CargarRopaRequestDTO;
import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaResponseDTO;
import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;

import java.util.List;

public interface IRopaService {
    Long cargarRopa(CargarRopaRequestDTO ropaRequestDTO, Long idLocal);
    List<MostrarRopaResponseDTO> mostrarRopa(Long idLocal);
    List<MostrarRopaResponseDTO> filtrarRopa(Categoria categoria, String nombre, Long idLocal);
}
