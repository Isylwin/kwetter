package nl.oscar.kwetter.dao;

import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.User;

import java.util.Collection;

public interface UserDao extends Dao<Long, User> {

    User findUserByName(String name);

    Collection<User> findUsersByUsername(Collection<String> names);

    Collection<User> findUsersById(Collection<Long> ids);

    User findUserByCredentials(Credentials credentials);
}
