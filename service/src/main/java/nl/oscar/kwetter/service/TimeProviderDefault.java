package nl.oscar.kwetter.service;

import javax.ejb.Stateless;
import java.time.LocalDateTime;

@Stateless
public class TimeProviderDefault implements TimeProvider {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
