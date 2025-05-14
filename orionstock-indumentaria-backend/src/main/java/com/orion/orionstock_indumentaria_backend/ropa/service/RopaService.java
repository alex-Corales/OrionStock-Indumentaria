package com.orion.orionstock_indumentaria_backend.ropa.service;

import com.orion.orionstock_indumentaria_backend.local.model.Local;
import com.orion.orionstock_indumentaria_backend.local.repository.ILocalRepository;
import com.orion.orionstock_indumentaria_backend.ropa.dto.request.CargarRopaRequestDTO;
import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaResponseDTO;
import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.ropa.model.Ropa;
import com.orion.orionstock_indumentaria_backend.ropa.repository.IRopaRepository;
import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import com.orion.orionstock_indumentaria_backend.variante.service.IVarianteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RopaService implements IRopaService{

    private final IRopaRepository iRopaRepository;
    private final ILocalRepository iLocalRepository;
    private final IVarianteService iVarianteService;
    private final ModelMapper modelMapper;

    @Override
    public Long cargarRopa(CargarRopaRequestDTO ropaRequestDTO, Long idLocal) {
        Local local = iLocalRepository.findById(idLocal).orElseThrow(() -> new RuntimeException("El local no existe en la base de datos"));

        Ropa ropa = iRopaRepository.save(modelMapper.map(ropaRequestDTO, Ropa.class));
        local.getRopas().add(ropa);
        iLocalRepository.save(local);
        return ropa.getId();
    }

    @Override
    public List<MostrarRopaResponseDTO> mostrarRopa(Long idLocal) {
        iLocalRepository.findById(idLocal).orElseThrow(() -> new RuntimeException("El local no existe en la base de datos"));

        List<Ropa> listaRopa = iRopaRepository.findAllByIdLocal(idLocal);

        for (Ropa ropa : listaRopa){
            List<Variante> listaVariante = iVarianteService.mostrarVariante(ropa.getId());
            ropa.setVariantes(listaVariante);
        }

        List<MostrarRopaResponseDTO> listaMostrarRopaResponseDTO = new ArrayList<>();

        for (Ropa ropa : listaRopa){
            for (Variante variante : ropa.getVariantes()){
                listaMostrarRopaResponseDTO.add(new MostrarRopaResponseDTO(ropa.getId(), variante.getId(), ropa.getNombre(), ropa.getCategoria(), variante.getTalle(), variante.getColor(), variante.getEstado(), variante.getPrecioUnidadCompra(), variante.getPrecioUnidadVenta(), variante.getCantidad()));
            }
        }

        return listaMostrarRopaResponseDTO;
    }

    @Override
    public List<MostrarRopaResponseDTO> filtrarRopa(Categoria categoria, String nombre, Long idLocal) {
        iLocalRepository.findById(idLocal).orElseThrow(() -> new RuntimeException("El local no se encuentra en la base de datos"));

        List<Ropa> ropaList = new ArrayList<>();

        if(categoria != null && nombre != null){
            ropaList = iRopaRepository.buscarPorCategoriaNombre(categoria, nombre, idLocal);
        }else if(categoria != null){
            ropaList = iRopaRepository.buscarPorCategoria(categoria, idLocal);
        }else{
            ropaList = iRopaRepository.buscarPorNombre(nombre, idLocal);
        }

        for (Ropa ropa : ropaList){
            List<Variante> variante = iVarianteService.mostrarVariante(ropa.getId());
            ropa.setVariantes(variante);
        }

        List<MostrarRopaResponseDTO> mostrarRopaResponseDTOS = new ArrayList<>();

        for (Ropa ropa : ropaList){
            for (Variante variante : ropa.getVariantes()){
                mostrarRopaResponseDTOS.add(new MostrarRopaResponseDTO(ropa.getId(), variante.getId(), ropa.getNombre(), ropa.getCategoria(), variante.getTalle(), variante.getColor(), variante.getEstado(), variante.getPrecioUnidadCompra(), variante.getPrecioUnidadVenta(), variante.getCantidad()));
            }
        }

        return mostrarRopaResponseDTOS;

    }
}
