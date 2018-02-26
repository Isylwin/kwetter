package nl.oscar.kwetter.service.impl;

import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.adt.Maybe;
import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.EitherUtil;
import nl.oscar.kwetter.service.ServerError;
import nl.oscar.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class UserServiceDefault implements UserService {

    @Inject
    private UserDao dao;

    /*private <R> Either<ServerError, R> executeDaoFunction() {
        return
    }*/

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
    public Either<ServerError, User> getUser(Long id) {
        try {
            Maybe<User> userMaybe = Maybe.maybe(dao.find(id));

            return Either.fromMaybe(
                    userMaybe,
                    () -> new ServerError("Cannot find user with id: " + id)
            );
        } catch (Exception e) {
            //Logger.log
            return Either.left(new ServerError("Critical Server error encountered"));
        }
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
}
