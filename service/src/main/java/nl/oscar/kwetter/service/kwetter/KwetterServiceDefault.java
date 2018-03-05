package nl.oscar.kwetter.service.kwetter;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.dao.KwetterDao;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.TimeProvider;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.kwetter.builder.KwetterBuilder;
import nl.oscar.kwetter.service.kwetter.persistence.KwetterPersister;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

@Stateless
public class KwetterServiceDefault implements KwetterService {

    @Inject
    private TimeProvider timeProvider;

    @Inject
    private KwetterBuilder builder;

    @Inject
    private KwetterDao dao;

    @Inject
    private KwetterPersister persister;

    @Inject
    private KwetterVerifier verifier;

    @Override
    public Either<ServerError, Kwetter> addKwetter(long author, String text) {
        try {
            LocalDateTime time = timeProvider.now();

            verifier.verifyArguments(author, text, time);

            Kwetter kwetter = builder.buildKwetter(author, text, time);

            dao.create(kwetter);
            persister.persist(kwetter);

            return Either.right(kwetter);
        } catch (Exception e) {
            return Either.left(new ServerError("Error: " + e.getCause().getCause().toString()));
        }
    }

    @Override
    public Either<ServerError, Collection<Kwetter>> getKwettersForAuthor(long author) {
        try {
            return Either.right(dao.getKwettersForAuthor(author));
        } catch (Exception e) {
            return Either.left(new ServerError("Error: " + e.getCause().getCause().toString()));
        }
    }
}
