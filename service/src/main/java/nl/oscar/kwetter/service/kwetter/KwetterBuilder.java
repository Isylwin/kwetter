package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;

import java.time.LocalDateTime;

public interface KwetterBuilder {

    Kwetter buildKwetter(long author, String text, LocalDateTime time);
}
