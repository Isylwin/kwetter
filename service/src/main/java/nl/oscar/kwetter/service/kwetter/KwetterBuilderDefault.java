package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.TimeProvider;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class KwetterBuilderDefault implements KwetterBuilder {

    @Inject
    private TopicParser topicParser;
    @Inject
    private MentionParser mentionParser;
    @Inject
    private TimeProvider timeProvider;

    @Override
    public Kwetter buildKwetter(User user, String text, LocalDateTime time) {
        return null;
    }
}
