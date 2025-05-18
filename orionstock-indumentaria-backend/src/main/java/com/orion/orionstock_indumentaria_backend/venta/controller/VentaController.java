package com.orion.orionstock_indumentaria_backend.venta.controller;

import com.orion.orionstock_indumentaria_backend.venta.dto.request.VentaRequestDTO;
import com.orion.orionstock_indumentaria_backend.venta.service.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/venta")
public class VentaController {

    private final IVentaService iVentaService;

    @PostMapping("/crear/{idLocal}")
    public ResponseEntity<?> crearVenta(@PathVariable Long idLocal, @RequestBody VentaRequestDTO ventaRequestDTO){
        iVentaService.crearVenta(ventaRequestDTO, idLocal);
        return ResponseEntity.ok("La venta se guardo con exito");
    }

}
