package nl.oscar.kwetter.service;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.domain.UserInformation;

import java.util.Collection;

public interface UserService {

    Either<ServerError, Collection<User>> getAllUsers();

    Either<ServerError, Collection<User>> getUsers(Collection<Long> ids);

    Either<ServerError, Collection<User>> getFollowersOfUser(long id);

    Either<ServerError, Collection<User>> getFollowingOfUser(long id);

    Either<ServerError, User> getUser(Long id);

    Either<ServerError, User> updateUserInfo(long id, UserInformation info);

    Either<ServerError, User> addUser(User user);

    Either<ServerError, User> followUser(long follower, long followee);
}
