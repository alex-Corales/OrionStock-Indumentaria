package com.orion.orionstock_indumentaria_backend.ropa.controller;

import com.orion.orionstock_indumentaria_backend.ropa.dto.request.CargarRopaRequestDTO;
import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaProjectionResponseDTO;
import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaResponseDTO;
import com.orion.orionstock_indumentaria_backend.ropa.model.Categoria;
import com.orion.orionstock_indumentaria_backend.ropa.service.IRopaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ropa")
public class RopaController {

    private final IRopaService iRopaService;

    @PostMapping("/crear/{idLocal}")
    public ResponseEntity<Long> crearRopa(@RequestBody @Valid CargarRopaRequestDTO ropaRequestDTO, @PathVariable Long idLocal){
        return ResponseEntity.ok(iRopaService.cargarRopa(ropaRequestDTO, idLocal));
    }

    @GetMapping("/traer/{idLocal}")
    public ResponseEntity<List<MostrarRopaResponseDTO>> mostrarRopa(@PathVariable Long idLocal){
        return ResponseEntity.ok(iRopaService.mostrarRopa(idLocal));
    }

    @GetMapping("/traer/{idLocal}/filtro")
    public ResponseEntity<List<MostrarRopaResponseDTO>> mostrarRopaFiltro(@RequestParam(required = false) Categoria categoria, @RequestParam(required = false) String nombre, @PathVariable Long idLocal){
        return ResponseEntity.ok(iRopaService.filtrarRopa(categoria, nombre, idLocal));
    }

    @GetMapping("/traer-paginacion/{idLocal}")
    public ResponseEntity<List<MostrarRopaProjectionResponseDTO>> mostrarRopaPaginacion(@RequestParam(required = true) int pagina, @RequestParam(required = true) int limit, @PathVariable Long idLocal){
        return ResponseEntity.ok(iRopaService.mostrarRopaPorPaginacion(pagina, limit, idLocal));
    }

}
