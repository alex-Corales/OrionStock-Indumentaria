package com.orion.orionstock_indumentaria_backend.local.service;

import com.orion.orionstock_indumentaria_backend.local.dto.request.CrearLocalRequestDTO;
import com.orion.orionstock_indumentaria_backend.local.model.Local;
import com.orion.orionstock_indumentaria_backend.local.repository.ILocalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalService implements ILocalService{

    private final ILocalRepository iLocalRepository;
    private final ModelMapper modelMapper;

    @Override
    public void crearLocal(CrearLocalRequestDTO localRequestDTO) {
        iLocalRepository.save(modelMapper.map(localRequestDTO, Local.class));
    }
}
