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
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

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

    private Set<KwetterListener> listeners = new HashSet<>();

    private <R> Either<ServerError, R> execute(Supplier<R> fn) {
        try {
            return Either.right(fn.get());
        } catch (Exception e) {
            return Either.left(new ServerError("Error: " + e.getCause().getCause().toString()));
        }
    }

    @Override
    public Either<ServerError, Kwetter> addKwetter(long author, String text) {
        Supplier<Kwetter> fn = () -> {
            LocalDateTime time = timeProvider.now();

            verifier.verifyArguments(author, text, time);

            Kwetter kwetter = builder.buildKwetter(author, text, time);

            dao.create(kwetter);
            persister.persist(kwetter);
            listeners.forEach(l -> l.onKwetter(kwetter));
            return kwetter;
        };

        return execute(fn);
    }

    @Override
    public Either<ServerError, Collection<Kwetter>> getKwettersForAuthor(long author) {
        Supplier<Collection<Kwetter>> fn = () -> dao.findWithAuthor(author);

        return execute(fn);
    }

    @Override
    public Either<ServerError, Kwetter> getKwetter(long id) {
        Supplier<Kwetter> fn = () -> dao.find(id);

        return execute(fn);
    }

    @Override
    public Either<ServerError, Collection<Kwetter>> getKwettersWithMention(long mention) {
        Supplier<Collection<Kwetter>> fn = () -> dao.findWithMention(mention);

        return execute(fn);
    }

    @Override
    public Either<ServerError, Collection<Kwetter>> getKwettersWithTopic(String topic) {
        Supplier<Collection<Kwetter>> fn = () -> dao.findWithTopic(topic);

        return execute(fn);
    }

    @Override
    public Either<ServerError, Void> removeKwetter(long id) {
        Supplier<Void> fn = () -> {
            Kwetter kwetter = dao.find(id);
            dao.remove(kwetter);

            return null;
        };

        return execute(fn);
    }

    @Override
    public Either<ServerError, Collection<Kwetter>> getAllKwetters() {
        Supplier<Collection<Kwetter>> fn = () ->
                dao.findAll();

        return execute(fn);
    }

    @Override
    public void addListener(KwetterListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(KwetterListener listener) {
        listeners.remove(listener);
    }
}
