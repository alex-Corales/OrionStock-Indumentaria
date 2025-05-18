package com.orion.orionstock_indumentaria_backend.exeption.variante;

public class VarianteNoExisteException extends RuntimeException {
    public VarianteNoExisteException(String message) {
        super(message);
    }
}
