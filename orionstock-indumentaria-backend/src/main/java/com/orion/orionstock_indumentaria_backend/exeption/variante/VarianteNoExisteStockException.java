package com.orion.orionstock_indumentaria_backend.exeption.variante;

public class VarianteNoExisteStockException extends RuntimeException {
    public VarianteNoExisteStockException(String message) {
        super(message);
    }
}
