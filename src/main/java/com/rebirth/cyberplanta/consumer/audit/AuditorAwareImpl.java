package com.rebirth.cyberplanta.consumer.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("mongoAuditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of("SYSTEM");
    }
}
