package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.service.TimeProvider;
import nl.oscar.kwetter.service.kwetter.parsing.MentionParser;
import nl.oscar.kwetter.service.kwetter.parsing.TopicParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class KwetterBuilderDefaultTest {

    @Mock
    TopicParser topicParser;

    @Mock
    MentionParser mentionParser;

    @Mock
    TimeProvider timeProvider;

    @InjectMocks
    KwetterBuilderDefault builder = new KwetterBuilderDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildKwetter_withValidParams_returnsKwetter() {
        long author = 1L;


    }
}