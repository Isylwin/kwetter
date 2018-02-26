package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class KwetterBuilderDefault implements KwetterBuilder {

    @Inject
    private TopicParser topicParser;
    @Inject
    private MentionParser mentionParser;

    @Override
    public Kwetter buildKwetter(long author, String text, LocalDateTime time) {
        Kwetter kwetter = Kwetter.builder()
                .author(author)
                .text(text)
                .timestamp(time)
                .build();

        return kwetter;
    }
}
