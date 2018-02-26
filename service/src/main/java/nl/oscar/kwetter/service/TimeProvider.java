package nl.oscar.kwetter.service;

import java.time.LocalDateTime;

public interface TimeProvider {

    LocalDateTime now();
}
