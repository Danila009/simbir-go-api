package ru.volga_it.simbir_go.common.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(final String message) {
        super(message);
    }
}
