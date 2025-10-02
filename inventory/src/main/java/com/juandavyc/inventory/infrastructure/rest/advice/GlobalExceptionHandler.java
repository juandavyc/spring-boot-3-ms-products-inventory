package com.juandavyc.inventory.infrastructure.rest.advice;

import com.juandavyc.inventory.application.exceptions.InventoryAlreadyExistsException;
import com.juandavyc.inventory.application.exceptions.InventoryNotFoundException;
import com.juandavyc.inventory.infrastructure.rest.dto.error.JsonApiError;
import com.juandavyc.inventory.infrastructure.rest.dto.error.JsonApiErrorResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.error.JsonApiErrorSource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<JsonApiErrorResponse> handleInventoryNotFound(
            InventoryNotFoundException ex
    ) {
        log.warn("Inventory not found: {}", ex.getMessage());
        JsonApiError error = JsonApiError.error(
                "404", "Inventory Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(JsonApiErrorResponse.of(error));
    }

    @ExceptionHandler(InventoryAlreadyExistsException.class)
    public ResponseEntity<JsonApiErrorResponse> handleInventoryAlreadyExists(
            InventoryAlreadyExistsException ex
    ) {
        log.warn("Inventory already exists: {}", ex.getMessage());
        JsonApiError error = JsonApiError.error(
                "409", "Inventory already exists",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(JsonApiErrorResponse.of(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.debug("Validation errors: {}", ex.getMessage());
        List<JsonApiError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> JsonApiError.errors(
                        fieldError.getDefaultMessage(),
                        Map.of(
                                "field", fieldError.getField(),
                                "rejectedValue", String.valueOf(fieldError.getRejectedValue())
                        ),
                        JsonApiErrorSource.pointer("/"+fieldError.getField().replaceAll("\\.", "/"))
                ))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonApiErrorResponse.of(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrorResponse> handleGenericException(Exception ex) {

        log.error("Unexpected error: {}", ex.getMessage(), ex);

        JsonApiError error = JsonApiError.error(
                "500", "Internal Server Error",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonApiErrorResponse.of(error));
    }

}
