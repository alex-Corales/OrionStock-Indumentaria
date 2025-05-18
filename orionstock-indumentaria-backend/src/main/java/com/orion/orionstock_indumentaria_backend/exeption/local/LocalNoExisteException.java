package com.orion.orionstock_indumentaria_backend.exeption.local;

public class LocalNoExisteException extends RuntimeException {
    public LocalNoExisteException(String message) {
        super(message);
    }
}
