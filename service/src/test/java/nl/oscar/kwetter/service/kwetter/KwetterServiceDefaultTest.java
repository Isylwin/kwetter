package nl.oscar.kwetter.service.kwetter;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.dao.KwetterDao;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.TimeProvider;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.kwetter.builder.KwetterBuilder;
import nl.oscar.kwetter.service.kwetter.persistence.KwetterPersister;
import nl.oscar.kwetter.service.util.EitherUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KwetterServiceDefaultTest {

    @Mock
    private KwetterBuilder builder;

    @Mock
    private TimeProvider timeProvider;

    @Mock
    private KwetterDao dao;

    @Mock
    private KwetterPersister persister;

    @Mock
    private KwetterVerifier verifier;

    @InjectMocks
    private KwetterServiceDefault service = new KwetterServiceDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addKwetter_withValidKwetterNoMentionsNoTopics_returnsKwetter() {
        String text = "Dit is een kwetter.";
        long author = 1L;
        LocalDateTime time = LocalDateTime.MIN;
        Kwetter kwetter = Kwetter.builder()
                .author(1L)
                .text(text)
                .timestamp(time)
                .build();

        when(timeProvider.now()).thenReturn(time);
        when(builder.buildKwetter(author, text, time)).thenReturn(kwetter);

        Either<ServerError, Kwetter> result = service.addKwetter(author, text);

        assertTrue(EitherUtil.hasRight(result));
        verify(dao).create(kwetter);
    }

    @Test
    public void addKwetter_withValidKwetterWithMentionsNoTopics_returnsKwetter() {
        String text = "Dit is een kwetter @Henk.";
        long author = 1L;
        LocalDateTime time = LocalDateTime.MIN;
        long mention = 2L;
        Kwetter kwetter = Kwetter.builder()
                .author(author)
                .text(text)
                .timestamp(time)
                .mention(mention)
                .build();

        when(timeProvider.now()).thenReturn(time);
        when(builder.buildKwetter(author, text, time)).thenReturn(kwetter);

        Either<ServerError, Kwetter> result = service.addKwetter(author, text);
    }
}