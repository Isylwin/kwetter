package nl.oscar.kwetter.service.kwetter;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.error.ServerError;

import java.util.Collection;

public interface KwetterService {

    Either<ServerError, Kwetter> addKwetter(long author, String text);

    Either<ServerError, Collection<Kwetter>> getKwettersForAuthor(long author);

    Either<ServerError, Kwetter> getKwetter(long id);

    Either<ServerError, Collection<Kwetter>> getKwettersWithMention(long mention);

    Either<ServerError, Collection<Kwetter>> getKwettersWithTopic(String topic);

    Either<ServerError, Void> removeKwetter(long id);

    Either<ServerError, Collection<Kwetter>> getAllKwetters();
}
