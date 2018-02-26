package nl.oscar.kwetter.service.kwetter;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.error.ServerError;

public interface KwetterService {

    Either<ServerError, Kwetter> addKwetter(User author, String text);
}
