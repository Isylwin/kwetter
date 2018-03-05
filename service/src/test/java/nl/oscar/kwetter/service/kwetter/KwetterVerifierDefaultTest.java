package nl.oscar.kwetter.service.kwetter;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public class KwetterVerifierDefaultTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private UserDao dao;
    @InjectMocks
    private KwetterVerifierDefault verifier = new KwetterVerifierDefault();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildKwetter_withInvalidAuthor_throwsException() {
        expectedException.expect(IllegalArgumentException.class);

        long author = -1L;
        String text = "Hallo";
        LocalDateTime time = LocalDateTime.MIN;

        verifier.verifyArguments(author, text, time);
    }

    @Test
    public void buildKwetter_withInvalidText_throwsException() {
        expectedException.expect(IllegalArgumentException.class);

        long author = 1L;
        String text = "";
        LocalDateTime time = LocalDateTime.MIN;

        verifier.verifyArguments(author, text, time);
    }

    @Test
    public void buildKwetter_withNullText_throwsException() {
        expectedException.expect(NullPointerException.class);

        long author = 1L;
        String text = null;
        LocalDateTime time = LocalDateTime.MIN;

        when(dao.find(author)).thenReturn(User.builder().build());

        verifier.verifyArguments(author, text, time);
    }

    @Test
    public void buildKwetter_withNullTime_throwsException() {
        expectedException.expect(NullPointerException.class);

        long author = 1L;
        String text = "Henk";
        LocalDateTime time = null;

        when(dao.find(author)).thenReturn(User.builder().build());

        verifier.verifyArguments(author, text, time);
    }
}