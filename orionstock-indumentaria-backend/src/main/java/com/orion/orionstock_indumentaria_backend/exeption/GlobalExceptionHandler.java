package com.orion.orionstock_indumentaria_backend.exeption;

import com.orion.orionstock_indumentaria_backend.exeption.local.LocalNoExisteException;
import com.orion.orionstock_indumentaria_backend.exeption.variante.VarianteNoExisteException;
import com.orion.orionstock_indumentaria_backend.exeption.variante.VarianteNoExisteStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest webRequest){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LocalNoExisteException.class)
    public ResponseEntity<String> handleLocalNoExisteException(LocalNoExisteException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VarianteNoExisteException.class)
    public ResponseEntity<String> handleVarianteNoExisteException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VarianteNoExisteStockException.class)
    public ResponseEntity<String> handleVarianteNoExisteStockException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
