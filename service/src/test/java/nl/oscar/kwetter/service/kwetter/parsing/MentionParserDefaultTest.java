package nl.oscar.kwetter.service.kwetter.parsing;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MentionParserDefaultTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
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

        Collection<String> mentions = new HashSet<>();
        mentions.add("Henk");

        Collection<String> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(mentions.toArray()));
    }

    @Test
    public void parse_withMultipleMentions_returnsUsers() {
        long id = 2L;
        long id2 = 3L;
        String text = "Hallo @Henk@Piet";

        Collection<String> mentions = new HashSet<>();
        mentions.add("Henk");
        mentions.add("Piet");

        Collection<String> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(mentions.toArray()));
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

        Collection<String> result = parser.parse(text);

        assertTrue(result.isEmpty());
    }
}