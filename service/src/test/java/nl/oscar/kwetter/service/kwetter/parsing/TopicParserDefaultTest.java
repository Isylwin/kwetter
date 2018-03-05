package nl.oscar.kwetter.service.kwetter.parsing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class TopicParserDefaultTest {

    @InjectMocks
    private TopicParserDefault parser = new TopicParserDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parse_withSingleTopic_returnsTopics() {
        String id = "moe";
        String text = "Ik ben #moe";

        Collection<String> expected = new HashSet<>();
        expected.add(id);

        Collection<String> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void parse_withDoubleTopic_returnsTopics() {
        String id = "moe";
        String id2 = "kutleven";
        String text = "Ik ben #moe#kutleven";

        Collection<String> expected = new HashSet<>();
        expected.add(id);
        expected.add(id2);

        Collection<String> result = parser.parse(text);

        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void parse_withTwoTopics_convertsToLowerCase() {
        String id = "moe";
        String text = "Ik ben #MoE#moe#MOe#MOE#mOE#mOe";

        Collection<String> result = parser.parse(text);

        assertThat(result.iterator().next(), is(id));
        assertThat(result.size(), is(1));
    }
}