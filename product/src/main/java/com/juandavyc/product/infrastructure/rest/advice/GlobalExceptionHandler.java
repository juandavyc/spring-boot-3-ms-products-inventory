package com.juandavyc.product.infrastructure.rest.advice;

import com.juandavyc.product.application.exceptions.ProductAlreadyExistsException;
import com.juandavyc.product.application.exceptions.ProductNotFoundException;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiError;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiErrorResponse;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiErrorSource;
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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<JsonApiErrorResponse> handleProductNotFound(
            ProductNotFoundException ex
    ) {
        log.warn("Product not found: {}", ex.getMessage());
        JsonApiError error = JsonApiError.error(
                "404", "Product Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(JsonApiErrorResponse.of(error));
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<JsonApiErrorResponse> handleProductAlreadyExists(
            ProductAlreadyExistsException ex
    ) {
        log.warn("Product already exists: {}", ex.getMessage());
        JsonApiError error = JsonApiError.error(
                "409", "Product already exists",
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
                        JsonApiErrorSource.pointer("/data/attributes/" + fieldError.getField())
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<JsonApiErrorResponse> handleNullPointer(NullPointerException ex) {
        log.error("NullPointer in request processing: {}", ex.getMessage(), ex);

        JsonApiError error = JsonApiError.error("400", "Invalid request structure", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonApiErrorResponse.of(error));
    }

}
