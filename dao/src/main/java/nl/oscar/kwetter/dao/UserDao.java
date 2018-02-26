package nl.oscar.kwetter.dao;

import nl.oscar.kwetter.domain.User;

import java.util.Collection;

public interface UserDao extends Dao<Long, User> {

    User findUser(String name);

    Collection<User> findUsersById(Collection<Long> ids);

}
