package nl.oscar.kwetter.service;

import com.jnape.palatable.lambda.adt.Either;

import java.util.function.Function;

public class EitherUtil {

    public static <L, R> boolean hasRight(Either<L, R> either) {
        return either.match(l -> false, r -> true);
    }

    public static <L, R> boolean hasLeft(Either<L, R> either) {
        return !hasRight(either);
    }

    public static <L, R, R2> Either<L, R2> collapse(Either<L, R> either, Function<R, Either<L, R2>> mapFn) {
        Either<L, Either<L, R2>> temp = either.biMapR(mapFn);
        return temp.recover(Either::left);
    }
}
