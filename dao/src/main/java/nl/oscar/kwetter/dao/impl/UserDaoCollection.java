package nl.oscar.kwetter.dao.impl;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.dao.qualifier.InMemCollection;
import nl.oscar.kwetter.domain.User;

import javax.ejb.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@InMemCollection
public class UserDaoCollection implements UserDao {

    private Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public void create(User object) {
        object.setId(id++);
        users.put(object.getId(), object);
    }

    @Override
    public void edit(User object) {
        users.put(object.getId(), object);
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
    public Collection<User> findUsersById(Collection<Long> ids) {
        return users.values()
                .stream()
                .filter(x -> ids.contains(x.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void remove(User object) {
        users.remove(object.getId());
    }

    @Override
    public User findUserByName(String name) {
        return users.values()
                .stream()
                .filter(x -> x.getInformation().getName().equals(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<User> findUsersByUsername(Collection<String> names) {
        return users.values()
                .stream()
                .filter(x -> names.contains(x.getCredentials().getUsername()))
                .collect(Collectors.toSet());
    }
}
