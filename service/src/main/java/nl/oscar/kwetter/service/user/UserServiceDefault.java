package nl.oscar.kwetter.service.user;

import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.adt.Maybe;
import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.domain.UserInformation;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.util.EitherUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

@Stateless
public class UserServiceDefault implements UserService {

    @Inject
    private UserDao dao;

    private <R> Either<ServerError, R> executeDaoFunction(Supplier<Either<ServerError, R>> fn) {
        try {
            return fn.get();
        } catch (Exception e) {
            return Either.left(new ServerError("Critical Server error occurred:" + e.getMessage()));
        }
    }

    @Override
    public Either<ServerError, Collection<User>> getAllUsers() {
        try {
            Maybe<Collection<User>> userMaybe = Maybe.maybe(dao.findAll());

            return Either.fromMaybe(
                    userMaybe,
                    () -> new ServerError("Cannot find any users.")
            );
        } catch (Exception e) {
            //Logger.log
            return Either.left(new ServerError("Critical Server error encountered"));
        }
    }

    @Override
    public Either<ServerError, Collection<User>> getUsers(Collection<Long> ids) {
        try {
            Maybe<Collection<User>> usersMaybe = Maybe.maybe(dao.findUsersById(ids));

            return Either.fromMaybe(usersMaybe,
                    () -> new ServerError("Cannot find any users"));
        } catch (Exception e) {
            //Logger.log
            return Either.left(new ServerError("Critical Server error occurred"));
        }
    }

    @Override
    public Either<ServerError, Collection<User>> getFollowersOfUser(long id) {
        Either<ServerError, User> user = getUser(id);

        return EitherUtil.collapse(user, u -> getUsers(u.getFollowers()));
    }

    @Override
    public Either<ServerError, Collection<User>> getFollowingOfUser(long id) {
        Either<ServerError, User> user = getUser(id);

        return EitherUtil.collapse(user, u -> getUsers(u.getFollowing()));
    }

    @Override
    public Either<ServerError, User> getUser(Long id) {
        Supplier<Either<ServerError, User>> fn = () -> {
            Maybe<User> userMaybe = Maybe.maybe(dao.find(id));

            return Either.fromMaybe(
                    userMaybe,
                    () -> new ServerError("Cannot find user with id: " + id)
            );
        };

        return executeDaoFunction(fn);
    }

    @Override
    public Either<ServerError, User> updateUserInfo(long id, UserInformation info) {
        Supplier<Either<ServerError, User>> fn = () -> {
            Objects.requireNonNull(info);
            Either<ServerError, User> temp = getUser(id);
            temp.peek(u -> u.setInformation(info));
            return temp;
        };

        return executeDaoFunction(fn);
    }

    @Override
    public Either<ServerError, User> addUser(User user) {
        Supplier<Either<ServerError, User>> fn = () -> {
            Objects.requireNonNull(user);
            dao.create(user);
            return Either.right(user);
        };

        return executeDaoFunction(fn);
    }

    @Override
    public Either<ServerError, User> followUser(long followerId, long followeeId) {
        if (followeeId == followerId) {
            return Either.left(new ServerError("Can't follow yourself."));
        }

        Either<ServerError, User> followerOpt = getUser(followerId);

        if (EitherUtil.hasLeft(followerOpt)) {
            return followerOpt;
        }

        Either<ServerError, User> followeeOpt = getUser(followeeId);

        if (EitherUtil.hasLeft(followeeOpt)) {
            return followeeOpt;
        }

        User follower = followerOpt.orThrow(s -> new IllegalStateException("Should not be reachable"));
        User followee = followeeOpt.orThrow(s -> new IllegalStateException("Should not be reachable"));

        try {
            follower.follow(followeeId);
            dao.edit(follower);
        } catch (Exception e) {
            follower.unfollow(followeeId);
            return Either.left(new ServerError("Can't follow user " + followeeId));
        }

        try {
            followee.beFollowed(followerId);
            dao.edit(followee);
        } catch (Exception e) {
            follower.unfollow(followeeId);
            dao.edit(follower);
            followee.beUnfollowed(followerId);

            return Either.left(new ServerError("Can't be followed by user " + followerId));
        }

        return followerOpt;
    }

    @Override
    public void editUser(User user) {
        dao.edit(user);
    }

    @Override
    public void removeUser(long id) {
        User user = dao.find(id);
        dao.remove(user);
    }
}
