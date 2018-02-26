package nl.oscar.kwetter.service;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.domain.User;

import java.util.Collection;

public interface UserService {

    Either<ServerError, Collection<User>> getAllUsers();

    Either<ServerError, User> getUser(Long id);

    Either<ServerError, User> followUser(long follower, long followee);
}