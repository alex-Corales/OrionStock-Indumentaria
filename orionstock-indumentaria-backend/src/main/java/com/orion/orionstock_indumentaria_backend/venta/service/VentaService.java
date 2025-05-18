package com.orion.orionstock_indumentaria_backend.venta.service;

import com.orion.orionstock_indumentaria_backend.detalleVenta.model.DetalleVenta;
import com.orion.orionstock_indumentaria_backend.detalleVenta.repository.IDetalleVentaRepository;
import com.orion.orionstock_indumentaria_backend.exeption.local.LocalNoExisteException;
import com.orion.orionstock_indumentaria_backend.exeption.variante.VarianteNoExisteException;
import com.orion.orionstock_indumentaria_backend.exeption.variante.VarianteNoExisteStockException;
import com.orion.orionstock_indumentaria_backend.local.model.Local;
import com.orion.orionstock_indumentaria_backend.local.repository.ILocalRepository;
import com.orion.orionstock_indumentaria_backend.variante.model.Variante;
import com.orion.orionstock_indumentaria_backend.variante.repository.IVarianteRepository;
import com.orion.orionstock_indumentaria_backend.variante.service.IVarianteService;
import com.orion.orionstock_indumentaria_backend.venta.dto.request.DetalleVentaRequestDTO;
import com.orion.orionstock_indumentaria_backend.venta.dto.request.VentaRequestDTO;
import com.orion.orionstock_indumentaria_backend.venta.model.Venta;
import com.orion.orionstock_indumentaria_backend.venta.repository.IVentaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService implements IVentaService{

    private final IVentaRepository iVentaRepository;
    private final ILocalRepository iLocalRepository;
    private final IVarianteRepository iVarianteRepository;
    private final IDetalleVentaRepository iDetalleVentaRepository;
    private final ModelMapper modelMapper;

    @Override
    public void crearVenta(VentaRequestDTO ventaRequestDTO, Long idLocal) {
        Local local = iLocalRepository.findById(idLocal).orElseThrow(() -> new LocalNoExisteException("El local no existe en la base de datos"));
        List<Variante> listaVariante = new ArrayList<>();
        List<DetalleVenta> listaDetalleVentas = new ArrayList<>();

        for (DetalleVentaRequestDTO detalleVentaRequestDTO : ventaRequestDTO.getListaDetalleVenta()){
            Variante variante = iVarianteRepository.findById(detalleVentaRequestDTO.getIdVariante())
                    .orElseThrow(() -> new VarianteNoExisteException("La variante no existe"));

            if(detalleVentaRequestDTO.getCantidad() > variante.getCantidad())
                throw new VarianteNoExisteStockException("No hay stock sufiente para la variante con id: " + variante.getId());

            variante.setCantidad(variante.getCantidad() - detalleVentaRequestDTO.getCantidad());
            listaVariante.add(variante);

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(detalleVentaRequestDTO.getCantidad());
            detalleVenta.setPrecioUnitario(detalleVentaRequestDTO.getPrecioUnitario());
            detalleVenta.setVariante(variante);
            listaDetalleVentas.add(detalleVenta);
        }

        Venta venta = modelMapper.map(ventaRequestDTO, Venta.class);

        venta.setDetalleVenta(listaDetalleVentas);
        venta.setIdLocal(idLocal);

        venta = iVentaRepository.save(venta);

        iVarianteRepository.saveAll(listaVariante);

        local.getVentas().add(venta);
        iLocalRepository.save(local);

    }
}
