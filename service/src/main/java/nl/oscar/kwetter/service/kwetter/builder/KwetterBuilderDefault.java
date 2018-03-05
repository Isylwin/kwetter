package nl.oscar.kwetter.service.kwetter.builder;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Stateless
public class KwetterBuilderDefault implements KwetterBuilder {

    @Inject
    private TopicParser topicParser;
    @Inject
    private MentionParser mentionParser;
    @Inject
    private UserDao userDao;

    @Override
    public Kwetter buildKwetter(long author, String text, LocalDateTime time) {

        Collection<String> mentionsParse = mentionParser.parse(text);
        Collection<Long> mentions = userDao.findUsersByUsername(mentionsParse)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        Collection<String> topicParse = topicParser.parse(text);

        return Kwetter.builder()
                .author(author)
                .text(text)
                .timestamp(time)
                .mentions(mentions)
                .topics(topicParse)
                .build();
    }
}
