package com.juandavyc.product.infrastructure.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware {

    // update, insert, delete ...
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("System");
    }

}
