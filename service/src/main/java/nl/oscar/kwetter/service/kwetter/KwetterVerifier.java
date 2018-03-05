package nl.oscar.kwetter.service.kwetter;

import java.time.LocalDateTime;

public interface KwetterVerifier {
    void verifyArguments(long author, String text, LocalDateTime time);
}
