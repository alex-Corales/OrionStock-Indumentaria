package com.orion.orionstock_indumentaria_backend.variante.service;

import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import com.orion.orionstock_indumentaria_backend.ropa.repository.IRopaRepository;
import com.orion.orionstock_indumentaria_backend.variante.dto.request.CrearVarianteRequestDTO;
import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import com.orion.orionstock_indumentaria_backend.variante.repository.IVarianteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VarianteService implements IVarianteService{

    private final IVarianteRepository iVarianteRepository;
    private final IRopaRepository iRopaRepository;
    private final ModelMapper modelMapper;

    @Override
    public void crearVariante(List<CrearVarianteRequestDTO> varianteRequestDTO, Long idRopa) {
        Ropa ropa = iRopaRepository.findById(idRopa).orElseThrow(() -> new RuntimeException("El producto no existe en la base de datos"));

        List<Variante> listaVariante = new ArrayList<>();

        for (CrearVarianteRequestDTO varianteDto : varianteRequestDTO){
            Variante variante = iVarianteRepository.save(modelMapper.map(varianteDto, Variante.class));
            ropa.getVariantes().add(variante);
        }

        iRopaRepository.save(ropa);
    }

    @Override
    public List<Variante> mostrarVariante(Long idRopa) {
        iRopaRepository.findById(idRopa).orElseThrow(() -> new RuntimeException("El producto no existe en la base de datos"));
        return iVarianteRepository.findAllByIdRopa(idRopa);
    }
}
