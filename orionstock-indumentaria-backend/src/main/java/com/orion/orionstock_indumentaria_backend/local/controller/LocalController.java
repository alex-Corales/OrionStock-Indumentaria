package com.orion.orionstock_indumentaria_backend.local.controller;

import com.orion.orionstock_indumentaria_backend.local.dto.request.CrearLocalRequestDTO;
import com.orion.orionstock_indumentaria_backend.local.service.ILocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/local")
public class LocalController {

    private final ILocalService iLocalService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearLocal(@RequestBody CrearLocalRequestDTO localRequestDTO){
        iLocalService.crearLocal(localRequestDTO);
        return ResponseEntity.ok("El local se guardo en la base de datos con exito");
    }

}
