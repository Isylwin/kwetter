package nl.oscar.kwetter.dao;

import nl.oscar.kwetter.domain.User;

public interface UserDao extends Dao<Long, User> {

    User findUser(String name);

}
