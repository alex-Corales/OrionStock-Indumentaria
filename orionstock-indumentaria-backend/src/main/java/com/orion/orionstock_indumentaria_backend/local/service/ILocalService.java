package com.orion.orionstock_indumentaria_backend.local.service;

import com.orion.orionstock_indumentaria_backend.local.dto.request.CrearLocalRequestDTO;

public interface ILocalService {
    void crearLocal(CrearLocalRequestDTO localRequestDTO);
}
