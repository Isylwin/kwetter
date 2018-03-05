package nl.oscar.kwetter.service.kwetter.builder;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Stateless
public class KwetterBuilderDefault implements KwetterBuilder {

    @Inject
    private TopicParser topicParser;
    @Inject
    private MentionParser mentionParser;

    @Override
    public Kwetter buildKwetter(long author, String text, LocalDateTime time) {
        return Kwetter.builder()
                .author(author)
                .text(text)
                .timestamp(time)
                .mentions(mentionParser.parse(text))
                .topics(topicParser.parse(text))
                .build();
    }
}
