package com.juandavyc.inventory.infrastructure.feign.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.warn("Feign client error - Status: {}, Method: {}", response.status(), methodKey);

        return switch (response.status()) {
            case 404 -> new FeignProductNotFoundException("Product not found - Status: " + response.status());
            case 400 -> new FeignBadRequestException("Bad request - Status: " + response.status());
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }

    public static class FeignProductNotFoundException extends RuntimeException {
        public FeignProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class FeignBadRequestException extends RuntimeException {
        public FeignBadRequestException(String message) {
            super(message);
        }
    }
}
