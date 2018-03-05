package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.kwetter.builder.KwetterBuilderDefault;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class KwetterBuilderDefaultTest {

    @Mock
    private TopicParser topicParser;

    @Mock
    private MentionParser mentionParser;

    @Mock
    private UserDao dao;

    @InjectMocks
    private KwetterBuilderDefault builder = new KwetterBuilderDefault();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildKwetter_withValidParamsNoTopicsNoMentions_returnsKwetter() {
        long author = 1L;
        String text = "Hallo dit is een kwetter.";
        LocalDateTime time = LocalDateTime.MIN;

        Kwetter result = builder.buildKwetter(author, text, time);

        assertEquals(result.getAuthor(), author);
        assertEquals(result.getText(), text);
        assertEquals(result.getTimestamp(), time);
    }

    @Test
    public void buildKwetter_withMentions_returnsKwetter() {
        long author = 1L;
        String text = "Hallo @Henk";
        LocalDateTime time = LocalDateTime.MIN;
        Collection<String> mentions = new HashSet<>();
        mentions.add("Henk");
        Collection<Long> users = new HashSet<>();
        users.add(2L);

        when(mentionParser.parse(text)).thenReturn(mentions);
        when(dao.findUsersByUsername(mentions)).thenReturn(new HashSet<>(Arrays.asList(User.builder().id(2L).credentials(Credentials.builder().username("Henk").build()).build())));

        Kwetter result = builder.buildKwetter(author, text, time);

        assertThat(result.getMentions(), containsInAnyOrder(users.toArray()));
    }

    @Test
    public void buildKwetter_withDifferentMentions_shouldDiffer() {
        long author = 1L;
        String text = "Hallo @Henk";
        LocalDateTime time = LocalDateTime.MIN;
        Collection<String> mentions = new HashSet<>();
        mentions.add("Henk");
        Collection<String> fake = new HashSet<>();
        fake.add("Piet");

        when(mentionParser.parse(text)).thenReturn(mentions);

        Kwetter result = builder.buildKwetter(author, text, time);

        assertThat(result.getMentions(), not(containsInAnyOrder(fake.toArray())));
    }

    @Test
    public void buildKwetter_withTopics_returnsKwetter() {
        long author = 1L;
        String text = "Hallo #Henk";
        LocalDateTime time = LocalDateTime.MIN;
        Collection<String> topics = new HashSet<>();
        topics.add("Henk");

        when(topicParser.parse(text)).thenReturn(topics);

        Kwetter result = builder.buildKwetter(author, text, time);

        String expected = Arrays.toString(topics.toArray());
        String actual = Arrays.toString(result.getTopics().toArray());

        assertThat(actual, is(expected));
    }

    @Test
    public void buildKwetter_withDifferentTopics_shouldDiffer() {
        long author = 1L;
        String text = "Hallo @Henk";
        LocalDateTime time = LocalDateTime.MIN;
        Collection<String> topics = new HashSet<>();
        topics.add("Henk");
        Collection<String> fake = new HashSet<>();
        fake.add("Piet");

        when(topicParser.parse(text)).thenReturn(topics);

        Kwetter result = builder.buildKwetter(author, text, time);

        String expected = Arrays.toString(fake.toArray());
        String actual = Arrays.toString(result.getTopics().toArray());

        assertThat(actual, is(not(expected)));
    }
}