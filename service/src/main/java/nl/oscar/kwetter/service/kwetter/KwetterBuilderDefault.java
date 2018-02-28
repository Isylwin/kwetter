package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Objects;

public class KwetterBuilderDefault implements KwetterBuilder {

    @Inject
    private TopicParser topicParser;
    @Inject
    private MentionParser mentionParser;

    @Override
    public Kwetter buildKwetter(long author, String text, LocalDateTime time) {
        verifyArguments(author, text, time);

        return Kwetter.builder()
                .author(author)
                .text(text)
                .timestamp(time)
                .mentions(mentionParser.parse(text))
                .topics(topicParser.parse(text))
                .build();
    }

    private void verifyArguments(long author, String text, LocalDateTime time) {
        checkAuthorNotInvalid(author);
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
}
