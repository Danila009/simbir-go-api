package ru.volga_it.simbir_go.features.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.volga_it.simbir_go.common.exceptions.BadRequestException;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.common.exceptions.ForbiddenException;
import ru.volga_it.simbir_go.features.advice.dto.ExceptionBody;
import ru.volga_it.simbir_go.features.advice.dto.ValidationExceptionBody;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(final ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage(), "resource_not_found_error");
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleForbidden(final ForbiddenException e) {
        return new ExceptionBody(e.getMessage(), "forbidden_error");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleDataIntegrityViolation(final DataIntegrityViolationException e) {
        return new ExceptionBody(e.getMessage(), "data_integrity_violation_error");
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBadRequestException(final BadRequestException e) {
        return new ExceptionBody(e.getMessage(), "bad_request_error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        ValidationExceptionBody body = new ValidationExceptionBody("validation_error");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        body.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return body;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleConstraintViolation(
            final ConstraintViolationException e
    ) {
        ValidationExceptionBody body = new ValidationExceptionBody("validation_error");
        body.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()
                )));
        return body;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(final Exception e) {
        return new ExceptionBody(e.getMessage(), "internal_error");
    }
}
