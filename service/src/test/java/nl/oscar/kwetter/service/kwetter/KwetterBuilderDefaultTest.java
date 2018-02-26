package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class KwetterBuilderDefaultTest {

    @Mock
    TopicParser topicParser;

    @Mock
    MentionParser mentionParser;

    @InjectMocks
    private KwetterBuilderDefault builder = new KwetterBuilderDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildKwetter_withValidParams_returnsKwetter() {
        long author = 1L;
        String text = "Hallo dit is een kwetter.";
        LocalDateTime time = LocalDateTime.MIN;

        Kwetter result = builder.buildKwetter(author, text, time);

        assertEquals(result.getAuthor(), author);
        assertEquals(result.getText(), text);
        assertEquals(result.getTimestamp(), time);
    }
}