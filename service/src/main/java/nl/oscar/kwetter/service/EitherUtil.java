package nl.oscar.kwetter.service;

import com.jnape.palatable.lambda.adt.Either;

public class EitherUtil {

    public static <L, R> boolean hasRight(Either<L, R> either) {
        return either.match(l -> false, r -> true);
    }

    public static <L, R> boolean hasLeft(Either<L, R> either) {
        return !hasRight(either);
    }
}
