package nl.oscar.kwetter.dao.impl;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;

import javax.ejb.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserDaoCollection implements UserDao {

    private Map<Long, User> users = new HashMap<>();

    @Override
    public void create(User object) {
        users.put(object.getId(), object);
    }

    @Override
    public void edit(User object) {
        create(object);
    }

    @Override
    public User find(Long aLong) {
        return users.get(aLong);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void remove(User object) {
        users.remove(object.getId());
    }

    @Override
    public User findUser(String name) {
        return users.values()
                .stream()
                .filter(x -> x.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}