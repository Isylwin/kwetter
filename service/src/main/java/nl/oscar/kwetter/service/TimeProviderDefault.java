package nl.oscar.kwetter.service;

import java.time.LocalDateTime;

public class TimeProviderDefault implements TimeProvider {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
