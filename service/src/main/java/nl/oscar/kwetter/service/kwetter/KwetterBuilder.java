package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.User;

import java.time.LocalDateTime;

public interface KwetterBuilder {

    Kwetter buildKwetter(User user, String text, LocalDateTime time);
}
