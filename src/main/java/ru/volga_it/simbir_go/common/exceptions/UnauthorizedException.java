package ru.volga_it.simbir_go.common.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String message) {
        super(message);
    }
}
