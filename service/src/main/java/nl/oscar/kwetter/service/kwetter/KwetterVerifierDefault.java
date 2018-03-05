package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.dao.UserDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Objects;

@Stateless
public class KwetterVerifierDefault implements KwetterVerifier {

    @Inject
    private UserDao userDao;

    public void verifyArguments(long author, String text, LocalDateTime time) {
        checkAuthorNotInvalid(author);
        checkAuthorExists(author);
        checkTextNotNull(text);
        checkTextNotEmpty(text);
        checkTimeNotNull(time);
    }

    private void checkTimeNotNull(LocalDateTime time) {
        Objects.requireNonNull(time, "Time should not be null");
    }

    private void checkTextNotNull(String text) {
        Objects.requireNonNull(text, "Text should not be null.");
    }

    private void checkTextNotEmpty(String text) {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text should not be empty.");
        }
    }

    private void checkAuthorNotInvalid(long author) {
        if (author < 0L) {
            throw new IllegalArgumentException("Author must be valid.");
        }
    }

    private void checkAuthorExists(long author) {
        if (Objects.isNull(userDao.find(author))) {
            throw new IllegalArgumentException("Author does not exist.");
        }
    }
}
