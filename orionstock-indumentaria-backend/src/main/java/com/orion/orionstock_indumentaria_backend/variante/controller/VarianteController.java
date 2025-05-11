package com.orion.orionstock_indumentaria_backend.variante.controller;

import com.orion.orionstock_indumentaria_backend.variante.dto.request.CrearVarianteRequestDTO;
import com.orion.orionstock_indumentaria_backend.variante.service.IVarianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variante")
public class VarianteController {

    private final IVarianteService iVarianteService;

    @PostMapping("/crear/{idRopa}")
    public ResponseEntity<?> crearVariante(@RequestBody @Valid List<CrearVarianteRequestDTO> varianteRequestDTO, @PathVariable Long idRopa){
        iVarianteService.crearVariante(varianteRequestDTO, idRopa);
        return ResponseEntity.ok("Se creo con exito");
    }

}
