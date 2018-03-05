package nl.oscar.kwetter.service.kwetter.parsing;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class MentionParserDefaultTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private UserDao userDao;
    @InjectMocks
    private MentionParserDefault parser = new MentionParserDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parse_withSingleMention_returnsUsers() {
        long id = 2L;
        String text = "Hallo @Henk";

        Collection<User> mentions = new HashSet<>();
        mentions.add(User.builder().id(id).credentials(Credentials.builder().username("Henk").build()).build());

        when(userDao.findUsersByUsername(Matchers.any())).thenReturn(mentions);

        Collection<Long> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(mentions.stream().map(User::getId).toArray()));
    }

    @Test
    public void parse_withMultipleMentions_returnsUsers() {
        long id = 2L;
        long id2 = 3L;
        String text = "Hallo @Henk@Piet";

        Collection<User> mentions = new HashSet<>();
        mentions.add(User.builder().id(id).credentials(Credentials.builder().username("Henk").build()).build());
        mentions.add(User.builder().id(id2).credentials(Credentials.builder().username("Piet").build()).build());

        when(userDao.findUsersByUsername(Matchers.any())).thenReturn(mentions);

        Collection<Long> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(mentions.stream().map(User::getId).toArray()));
    }

    @Test
    public void parse_withNullText_throwsException() {
        expectedException.expect(NullPointerException.class);

        String text = null;

        parser.parse(text);
    }

    @Test
    public void parse_withNoMentions_returnsEmptyList() {
        String text = "Hallo";

        Collection<Long> result = parser.parse(text);

        assertTrue(result.isEmpty());
    }
}