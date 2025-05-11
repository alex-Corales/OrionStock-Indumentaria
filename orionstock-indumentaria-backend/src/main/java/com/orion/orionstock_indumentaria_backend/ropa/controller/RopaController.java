package com.orion.orionstock_indumentaria_backend.ropa.controller;

import com.orion.orionstock_indumentaria_backend.ropa.dto.request.CargarRopaRequestDTO;
import com.orion.orionstock_indumentaria_backend.ropa.dto.response.MostrarRopaResponseDTO;
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

}
