package nl.oscar.kwetter.api;

import com.jnape.palatable.lambda.adt.Either;

import javax.ws.rs.core.Response;

public class ResponseUtility {

    /**
     * Creates a {@code Response} from a {@code Either}.
     * The left side of the {@code Either} should contain the failure condition.
     * The right side of the {@code Either} should contain the success condition.
     *
     * @param either The {@code Either} that should be translated to a {@code Response}.
     * @param status The status that should be returned when a error occurred.
     * @param <L>    Workaround
     * @param <R>    Workaround
     * @return The {@code Response}.
     */
    public static <L, R> Response getResponseFromEither(Either<L, R> either, int status) {
        return either.match(
                l -> Response.status(status).entity(l).build(),
                r -> Response.ok(r).build());
    }

    /**
     * Creates a {@code Response} with a 500 status.
     *
     * @param either The {@code Either} that should be translated to a {@code Response}.
     * @param <L>    Workaround
     * @param <R>    Workaround
     * @return The {@code Response}.
     */
    public static <L, R> Response getResponseFromEither(Either<L, R> either) {
        return getResponseFromEither(either, 500);
    }
}
